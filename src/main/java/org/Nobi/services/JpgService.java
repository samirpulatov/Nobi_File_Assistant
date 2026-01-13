package org.Nobi.services;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

// service for handling jpg operations
@Service
public class JpgService {

    private final static Logger LOGGER = LoggerFactory.getLogger(JpgService.class);

    java.io.File convertJPG_TO_PNG(java.io.File file) {

        //use ImageIO.read to read the image into a BufferedImage object
        try {
            BufferedImage image = ImageIO.read(file);

            String file_name_with_png_extension = file.getName().replaceAll("(?i)\\.jpg$",".png");

            //Create a new output file
            java.io.File output = new java.io.File("output/"+file_name_with_png_extension);

            //Write the image to the output file in PNG format
            ImageIO.write(image,"png",output);

            return output;
        } catch(IOException e) {
            LOGGER.error("Error converting JPG_TO_PNG ", e);
        }

        //return null in case of an error while converting
        return null;
    }

    java.io.File convertJPG_TO_PDF(java.io.File file) {
        //using iText 7 library to work with pdf files
        try {
            String file_name_with_pdf_extension = file.getName().replaceAll("(?i)\\.jpg$",".pdf");

            java.io.File output = new java.io.File("output/"+file_name_with_pdf_extension);


            //Create a PDFWriter
            PdfWriter writer = new PdfWriter(output);
            PdfDocument pdfDocument = new PdfDocument(writer);

            Document document = new Document(pdfDocument);

            //Load Image first
            ImageData imageData = ImageDataFactory.create(file.getAbsolutePath());
            Image image = new Image(imageData);


            //add image to pdf
            document.add(image);

            //close the document
            document.close();

            //return pdf file
            return output;
        } catch (Exception e) {
            LOGGER.error("Error converting JPG_TO_PDF",e);
            return null;
        }
    }


    //almost same code like with convertJPG_TO_PNG
    java.io.File convertJPG_TO_WEBP(java.io.File file) {

        try {
            BufferedImage image = ImageIO.read(file);

            String file_name_with_webp_extension = file.getName().replaceAll("(?i)\\.jpg$",".webp");

            java.io.File output = new java.io.File("output/"+file_name_with_webp_extension);

            ImageIO.write(image,"webp",output);

            return output;
        }
        catch(IOException e) {
            LOGGER.error("Error converting JPG_TO_WEBP ", e);
            return null;
        }
    }



}
