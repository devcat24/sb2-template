// https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
package com.github.devcat24.mvc.controller;

import com.github.devcat24.exception.CustomIntlSvrError;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ResolvableType;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;


@Controller
public class WebMVCController {

    @CrossOrigin(origins="http://localhost:9000") // This method can be invoked from "http://localhost:9000" as the exception of SOP(Single-Origin Policy)
    @RequestMapping("/cors_call")
    public String userKey(@RequestParam(required=false, defaultValue="10000A") String userName) {
        return userName.substring(0, 1) + "00001";
    }

    @RequestMapping("/jsp_page")
    public String jspPage(){
        return "jsp-01";
    }
    @RequestMapping(value={"/ftl_page"})
    public String ftlPage(Model model) {
        model.addAttribute("title", "Hello");
        model.addAttribute("body", "World");
        return "ftl-01" ;
    }
    @RequestMapping("/err5xx")
    public String err5xx() throws CustomIntlSvrError {
        throw new CustomIntlSvrError("Manual RuntimeException is thrown");
    }


    /*   // ### Spring Security Google OAuth2 Configuration

    private static String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    void setClientRegistrationRepository(ClientRegistrationRepository clientRegistrationRepository){
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/oauth_login")
    public String getLoginPage(Model model){
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration ->
                                        oauth2AuthenticationUrls.put(registration.getClientName(),
                                            authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "oauth_login";
    }


    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    void setAuthorizedClientService(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
    @GetMapping("/login_success")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());

            HttpEntity<String> entity = new HttpEntity<String>("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();
            model.addAttribute("name", userAttributes.get("name"));
        }

        return "login_success";
    }

    */
}
