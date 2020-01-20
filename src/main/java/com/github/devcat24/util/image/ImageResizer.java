package com.github.devcat24.util.image;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {
    public static void resize(String srcFile, String targetFile, int targetWidth) throws IOException {
        /*int targetWidth = 500;

        String srcFile = "/home/tomcat/Downloads/sample_img.jpg";
        String targetFile = "/home/tomcat/Downloads/sample_img_" + targetWidth + ".jpg";*/

        File input = new File(srcFile);
        // Read image & Get image Info
        BufferedImage srcImage = ImageIO.read(input);
        int targetHeight = Math.round(targetWidth * ((float) srcImage.getHeight()/ (float)srcImage.getWidth()));

        Image tmpImage = srcImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_3BYTE_BGR);     // -> for jpg
        // BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);   // -> for png (no compression)

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmpImage, 0, 0, null);
        g2d.dispose();

        ImageWriter jpgWriter = (ImageWriter) ImageIO.getImageWritersByFormatName("jpg").next();
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        // save jpeg image with specific quality. "1f" corresponds to 100% , "0.7f" corresponds to 70%
        jpgWriteParam.setCompressionQuality(0.7f);

        File output = new File(targetFile);
        jpgWriter.setOutput(ImageIO.createImageOutputStream(output));
        IIOImage outputImage = new IIOImage(resized, null, null);
        jpgWriter.write(null, outputImage, jpgWriteParam);
        jpgWriter.dispose();
    }
}
