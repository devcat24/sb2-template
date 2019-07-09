package com.github.devcat24.mvc.rest;

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

    /* Deprecated
    private BasicAuthorizationInterceptor getBasicAuthorizationInterceptor(){
        return new BasicAuthorizationInterceptor(username, password);
    }
    */
    private HttpHeaders getBasicAuthHeader(HttpHeaders headers){
        //HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        return headers;
    }

    @SuppressWarnings("unused")
    public String fetchAsStringFromRestAPI() throws Exception {
        String rtn;
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        try {
            RestTemplate restTemplate = new RestTemplate();
            // -> Deprecated in Spring 5.1
            // restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
            // ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

            // Basic Auth using HttpEntity
            HttpEntity<String> requestHeaderEntity = new HttpEntity<>(getBasicAuthHeader(new HttpHeaders()));
            ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, requestHeaderEntity, String.class);

            //noinspection RedundantStringToString,ConstantConditions
            rtn = res.getBody().toString();
        } catch (Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return rtn;
    }
    @SuppressWarnings({"unused", "RedundantThrows"})
    public RestEmp[] fetchAsObjFromRestAPI() throws Exception {
        RestEmp [] rtnList;
        String url = "http://localhost:8200/template/rest/jsonHolder/emps";
        try {
            RestTemplate restTemplate = new RestTemplate();
            // -> Deprecated in Spring 5.1
            // restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());
            // ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, null, String.class);


            // Basic Auth using HttpEntity -> getForObject(...) doesn't support adding HttpEntity to parameter
            // HttpEntity<String> requestHeaderEntity = new HttpEntity<>(getBasicAuthHeader());
            // rtnList = restTemplate.getForObject(url, requestHeaderEntity, RestEmp[].class); -> use 'restTemplate.exchange(...)' method
            rtnList = restTemplate.getForObject(url, RestEmp[].class);



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
        // -> Deprecated in Spring 5.1
        // restTemplate.getInterceptors().add(getBasicAuthorizationInterceptor());

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        getBasicAuthHeader(headers);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> res = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, "1");

        if(res.getStatusCode() == HttpStatus.OK){
            byte[] fileStream = res.getBody();
            String newFilePathString = fileWorkingDir + File.separator + newFileName;
            File file = new File(newFilePathString);  // overwrites if file exists
            if (fileStream != null) {
                FileUtils.writeByteArrayToFile(file, fileStream); // automatically invokes IOUtils.closeQuietly()
            }
        }
        // --> clearing directory
        // File df = new File("/data/downloads/files/");
        // FileUtils.deleteDirectory(df);
    }




}
