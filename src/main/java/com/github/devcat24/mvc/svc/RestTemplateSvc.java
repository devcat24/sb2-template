package com.github.devcat24.mvc.svc;

import com.github.devcat24.mvc.dto.mm.RestEmp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
//import org.springframework.http.client.support.BasicAuthorizationInterceptor;
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

    // -> for Spring 4.x -> 'BasicAuthorizationInterceptor' is deprecated
    /*
    @SuppressWarnings("deprecation")
    private BasicAuthorizationInterceptor getBasicAuthorizationInterceptor(){
        return new BasicAuthorizationInterceptor(username, password);
    }
    */


    @SuppressWarnings("unused")
    public String fetchAsStringFromRestAPI() {
        String rtn ;
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        try {
            RestTemplate restTemplate = new RestTemplate();


            // -> for Spring 4.x -> 'BasicAuthorizationInterceptor' is deprecated
            // restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
            // ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);


            //noinspection RedundantStringToString,ConstantConditions
            rtn = res.getBody().toString();
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return rtn;
    }
    public RestEmp[] fetchAsObjFromRestAPI() {
        RestEmp [] rtnList;
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        try {
            RestTemplate restTemplate = new RestTemplate();

            // -> for Spring 4.x -> 'BasicAuthorizationInterceptor' is deprecated
            // restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
            // ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            // rtnList = restTemplate.getForObject(url, RestEmp[].class);

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            HttpEntity<RestEmp[]> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<RestEmp[]> res = restTemplate.exchange(url, HttpMethod.GET, requestEntity, RestEmp[].class);
            rtnList = res.getBody();
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return rtnList;
    }


    @SuppressWarnings({"ArraysAsListWithZeroOrOneArgument", "unused"})
    public void downloadRemoteFile(String fileLocation, String newFileName) throws IOException {
        String apiFileDownloadBaseUrl = "https://localhost:8200/template/api/file";
        String fileWorkingDir = "/data/downloads/files";

        //String url = "https://localhost:8200/template/api/file/1001";
        String url = apiFileDownloadBaseUrl + fileLocation;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        // -> for Spring 4.x -> 'BasicAuthorizationInterceptor' is deprecated
        // restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> res = restTemplate.exchange(url, HttpMethod.GET, requestEntity, byte[].class, "1");

        if(res.getStatusCode() == HttpStatus.OK){
            byte[] fileStream = res.getBody();
            String newFilePathString = fileWorkingDir + File.separator + newFileName;
            File file = new File(newFilePathString);  // overwrites if file exists
            //noinspection ConstantConditions
            FileUtils.writeByteArrayToFile(file, fileStream); // automatically invokes IOUtils.closeQuietly()
        }
        // --> clearing directory
        // File df = new File("/data/downloads/files/");
        // FileUtils.deleteDirectory(df);
    }




}
