package com.github.devcat24.util.archive;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("ArchiveUtils")
public class ArchiveUtils {

    // @Value("${archive.working.dir}")
    String fileWorkingDir;


    public File getArchivedFileByName(String fileName) throws Exception {
        if(StringUtils.isNotBlank(fileName)){
            File file = new File(fileWorkingDir + File.separator + fileName);
            if(file.exists()){
                return file;
            }
        }
        return null;
    }



    //public void createZip(List<File> srcFiles, String outputFilePath) throws Exception{
    public void createZip(List<String> srcFileStringList, String outputFileName) throws Exception {
        List<File> srcFiles = new ArrayList<>();
        for(String fileString : srcFileStringList){
            srcFiles.add(new File(fileWorkingDir + File.separator + fileString));
        }

        if (srcFiles.size() > 0) {
            File targetFile;
            FileInputStream fis = null;
            ZipArchiveOutputStream zos = null;

            try {
                targetFile = new File(fileWorkingDir + File.separator + outputFileName);
                FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                zos = new ZipArchiveOutputStream(fileOutputStream);
                zos.setEncoding(Charset.defaultCharset().name());

                int length;
                ZipArchiveEntry ze;
                byte[] buf = new byte[8 * 1024];

                for (File srcFile : srcFiles) {
                    ze = new ZipArchiveEntry(srcFile.getName());
                    zos.putArchiveEntry(ze);
                    fis = new FileInputStream(srcFile);
                    while ((length = fis.read(buf, 0, buf.length)) >= 0) {
                        zos.write(buf, 0, length);
                    }
                }
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (zos != null) {
                    zos.closeArchiveEntry();
                    zos.close();
                }
            }
            cleanUpSrcFiles(srcFileStringList);
        } else {
            throw new IOException("Invalid compression source files");
        }

    }

    @SuppressWarnings("WeakerAccess")
    public void cleanUpSrcFiles(List<String> filePathStrings){
        for(String filePath : filePathStrings){
            File file = new File(fileWorkingDir + File.separator + filePath);
            FileUtils.deleteQuietly(file);
        }
    }

    public boolean cleanUpSrcFile(String filePathString) {
        File file = new File(fileWorkingDir + File.separator + filePathString);
        return file.exists() && FileUtils.deleteQuietly(file);
    }

}
