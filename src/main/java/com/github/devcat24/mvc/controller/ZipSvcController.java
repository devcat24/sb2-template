package com.github.devcat24.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Controller("ZipSvcController")
public class ZipSvcController {

    @RequestMapping(value="/getMergedEmpFile", produces = "application/zip")
    public void getMergedEmpFile(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.addHeader("Content-Disposition", "attachment; filename=\"merged.zip\"");

        List<File> files = new ArrayList<>();

        // type #1 : using absolute path
        //     -> this could be better way while using 'self contained jar'
        //     -> parameterize path variable to 'application.properties'
        // files.add(new File("/home/tomcat/Readme.md"));
        // files.add(new File("/home/tomcat/Readme2.md"));

        // type #2 : using project path
        String uploadDir = req.getSession().getServletContext().getRealPath(("/upload"));
        files.add(new File(uploadDir + "/Readme.md"));
        files.add(new File(uploadDir + "/Readme2.md"));

        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;

        try {
            zipOutputStream = new ZipOutputStream(res.getOutputStream());
            for(File file : files){
                try{
                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                    fileInputStream = new FileInputStream(file);
                    IOUtils.copy(fileInputStream, zipOutputStream);
                }   catch(IOException iie){
                    throw iie;
                }   finally {
                    fileInputStream.close();
                    zipOutputStream.closeEntry();
                }
            }
        } catch(IOException ie){
            throw ie;
        } finally {
            zipOutputStream.close();
        }
    }
}
