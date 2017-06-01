package com.github.devcat24.util.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

// Refer to -> https://www.tutorialspoint.com/pdfbox/index.htm
public class PDFBoxUtil {

    public String readFromPDF(String pdfFile) throws IOException {
        String pdfText = null;

        PDDocument doc = null;
        try {
            File file = new File(pdfFile);
            doc = PDDocument.load(file);
            int totPage = doc.getNumberOfPages();

            //doc.removePage(2);
            //doc.save("/opt/dev/pdf_test/test.pdf");

            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfText = pdfStripper.getText(doc);
        }  finally {
            if(doc != null) { doc.close(); }
        }

        return pdfText;
    }
}
