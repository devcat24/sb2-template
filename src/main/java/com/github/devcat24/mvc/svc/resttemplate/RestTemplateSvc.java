package com.github.devcat24.mvc.svc.resttemplate;

import com.github.devcat24.mvc.svc.db.dto.mm.RestEmp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service("RestTemplateSvc")
public class RestTemplateSvc {

    @Value("${rest.api.username}")
    String username;

    @Value("${rest.api.password}")
    String password;

    private BasicAuthorizationInterceptor getBasicAuthorizationInterceptor(){
        return new BasicAuthorizationInterceptor(username, password);
    }

    public String fetchAsStringFromRestAPI() throws Exception {
        String rtn = null;
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            //noinspection RedundantStringToString
            rtn = res.getBody().toString();
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return rtn;
    }
    public RestEmp[] fetchAsObjFromRestAPI() throws Exception {
        RestEmp [] rtnList;
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
            //ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            rtnList = restTemplate.getForObject(url, RestEmp[].class);
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return rtnList;
    }


    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public void downloadRemoteFile(String fileLocation, String newFileName) throws IOException {
        String apiFileDownloadBaseUrl = "https://localhost:8200/template/api/file";
        String fileWorkingDir = "/data/downloads/files";

        //String url = "https://localhost:8200/template/api/file/1001";
        String url = apiFileDownloadBaseUrl + fileLocation;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> res = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, "1");

        if(res.getStatusCode() == HttpStatus.OK){
            byte[] fileStream = res.getBody();
            String newFilePathString = fileWorkingDir + File.separator + newFileName;
            File file = new File(newFilePathString);  // overwrites if file exists
            FileUtils.writeByteArrayToFile(file, fileStream); // automatically invokes IOUtils.closeQuietly()
        }
        // --> clearing directory
        // File df = new File("/data/downloads/files/");
        // FileUtils.deleteDirectory(df);
    }




}
