package com.github.devcat24;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@Slf4j
@SpringBootApplication
public class DevTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevTemplateApplication.class, args);
	}


	@EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent(){
        System.out.println("==================================");
        System.out.println(" Now Spring Boot is ready on      ");
        System.out.println("  http://localhost:8200/template/ ");
        System.out.println("==================================");


        //openWelcomePage("http://localhost:8200/template/");
    }

    /*
    private void openWelcomePage(String url){
	    if(Desktop.isDesktopSupported()){
	        Desktop desktop = Desktop.getDesktop();
	        try {
	            desktop.browse(new URI(url));
            } catch(IOException | URISyntaxException e) {
	            e.printStackTrace();
            }
        }   else {
	        Runtime runtime = Runtime.getRuntime();
	        try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch(IOException e){
	            e.printStackTrace();
            }
        }
    }*/

}
