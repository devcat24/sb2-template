package com.github.devcat24.util.version;

import java.util.jar.Manifest;

public class AppVersion {

    public static void main(String args[]){

        String appTitle = "";
        String version = "";
        try {
            Manifest manifest = new Manifest(new AppVersion().getClass().getResourceAsStream("/META-INF/MANIFEST.MF"));
            appTitle = manifest.getMainAttributes().getValue("Implementation-Title");
            version = manifest.getMainAttributes().getValue("Implementation-Version");
            System.out.println("====================================");
            System.out.println(" Spring Application version         ");
            System.out.println("     "+ appTitle + ": " + version    );
            System.out.println("====================================");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
