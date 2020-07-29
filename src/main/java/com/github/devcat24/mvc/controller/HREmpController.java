package com.github.devcat24.mvc.controller;

import com.github.devcat24.exception.ResourceNotFoundException;
import com.github.devcat24.mvc.db.entity.hr.Emp01;
import com.github.devcat24.mvc.svc.HREmpSvc;
//import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.commons.lang3.StringUtils;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.web.client.RestTemplate;


// 1. Sample data creation
//   $ curl -i -X POST -H "Content-Type:application/json" -d '{"empno": 12331,"ename": "KIM","job": "Admin","mgr": 12331}' http://localhost:8080/api/v1/emp
// 2. Query user
//   $ curl -X GET http://localhost:8080/api/v1/emp/12331


@Tag(name="Employee Management System", description="Operations pertaining to employee in Employee Management System")
// '@ApiResponses' can be defined both class level & method level
@ApiResponses(value={
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "401", description = "You are not authorized to view the resource"),
        @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found")
})

@RestController
@RequestMapping("/api/v1")
public class HREmpController {
    private HREmpSvc hrEmpSvc;
    @Autowired
    void setHrEmpSvc(HREmpSvc hrEmpSvc) {
        this.hrEmpSvc = hrEmpSvc;
    }

    @Operation(summary="View all employees", description = "View a list of available employees")
    // '@ApiResponses' can be defined both class level & method level
    @ApiResponses(value={
            @ApiResponse(responseCode = "400", description = "Please try again with valid data")
        }
    )
    @GetMapping("/emp")
    public List<Emp01> getAllEmp() {
        System.out.println("-> HREmpController.getAllEmp() invoked" );
        return hrEmpSvc.getAllEmp();
    }

    @Operation(summary="Get an employee by Id", description = "Get an employee by Id")
    @GetMapping("/emp/{id}")
    public ResponseEntity<Emp01> getEmpById(
            @Parameter(name = "Emploee Id", description = "Employee id from which employee object will retrieve", required = true) @PathVariable(value = "id") Integer empno) throws ResourceNotFoundException {
        Emp01 emp01 = hrEmpSvc.getEmpById(empno).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this empno :: " + empno));
        return ResponseEntity.ok().body(emp01);
    }

    @Operation(summary="Create employee", description = "Add an employee")
    @PostMapping("/emp")
    public Emp01 createEmp(@ParameterObject @Valid @RequestBody Emp01 emp01) {
        return hrEmpSvc.save(emp01);
    }



    @Operation(summary="Update employee", description = "Update an employee")
    @PutMapping("/emp/{id}")
    public ResponseEntity<Emp01> updateEmp( @Parameter(name = "Employee Id", required = true) @PathVariable(value = "id") Integer empno,
                                            @ParameterObject @Valid @RequestBody Emp01 emp01) throws ResourceNotFoundException {
        Emp01 updatedEmp = hrEmpSvc.getEmpById(empno).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this empno :: " + empno));

        updatedEmp.setEname(emp01.getEname());
        updatedEmp.setJob(emp01.getJob());
        updatedEmp.setMgr(emp01.getMgr());

        hrEmpSvc.save(updatedEmp);
        return ResponseEntity.ok(updatedEmp);
    }

    @Operation(summary="Delete employee")
    @DeleteMapping("/emp/{id}")
    public Map<String, Boolean> deleteEmp(@Parameter(name = "Employee Id", required = true) @PathVariable(value = "id") Integer empno) throws ResourceNotFoundException {
        hrEmpSvc.delete(empno);
        Map<String, Boolean> res = new HashMap<>();
        res.put("deleted", Boolean.TRUE);
        return res;
    }




    // ### Spring Security Google OAuth2 Configuration
    // ### OAuth user information can be extracted from Spring Controller's method parameter
    /*
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    void setAuthorizedClientService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/emp")
    public List<Emp01> getAllEmp(OAuth2AuthenticationToken authToken) {
        Map<String, Object> userAttributes = getOAuthUserMap(authToken);
        if(userAttributes != null){userAttributes.forEach((k, v) -> System.out.println(k + ": " + v));}

        return hrEmpSvc.getAllEmp();
    }
    @ApiOperation(value = "Get an employee by Id")
    @GetMapping("/emp/{id}")
    public ResponseEntity<Emp01> getEmpById(
            @ApiParam(value = "Employee id from which employee object will retrieve", required = true) @PathVariable(value = "id") Integer empno,
            OAuth2AuthenticationToken authToken ) throws ResourceNotFoundException {
        Map<String, Object> userAttributes = getOAuthUserMap(authToken);
        if(userAttributes != null){userAttributes.forEach((k, v) -> System.out.println(k + ": " + v));}

        Emp01 emp01 = hrEmpSvc.getEmpById(empno).orElseThrow(() -> new ResourceNotFoundException("Employee not found for this empno :: " + empno));
        return ResponseEntity.ok().body(emp01);
    }

    private Map<String, Object> getOAuthUserMap(OAuth2AuthenticationToken authToken){
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());

            HttpEntity<String> entity = new HttpEntity<>("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            //noinspection unchecked
            return (Map<String, Object>)response.getBody();
        }
        return null;
    }
    */


}
