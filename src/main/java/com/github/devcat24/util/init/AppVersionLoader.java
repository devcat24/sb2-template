package com.github.devcat24.util.init;

import java.util.jar.Manifest;

public class AppVersionLoader {
    public String getApplicationTitleNVersion(){
        String rtn = "Invalid version info";
        try {
            Manifest manifest = new Manifest(this.getClass().getResourceAsStream("/META-INF/MANIFEST.MF"));
            String appTitle = manifest.getMainAttributes().getValue("Implementation-Title");
            String version = manifest.getMainAttributes().getValue("Implementation-Version");
            if(appTitle != null && version != null){
                rtn = appTitle + ": " + version;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return rtn;
    }
}
