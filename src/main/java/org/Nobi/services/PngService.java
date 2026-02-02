package org.Nobi.services;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import org.Nobi.documents.PngHandler;
import org.Nobi.exceptions.ImageConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


@Service
public class PngService {
    private final static Logger LOGGER = LoggerFactory.getLogger(PngHandler.class);


    public File convertJPG_TO_PDF(File file) {
        try{
            String file_name_with_pdf_extension = file.getName().replaceAll("(?i)\\.png$",".pdf");
            File output_file = new File("output/"+file_name_with_pdf_extension);

            //Create a PDFWriter
            PdfWriter writer = new PdfWriter(output_file);
            PdfDocument pdfDocument = new PdfDocument(writer);

            Document document = new Document(pdfDocument);

            //Load Image first
            ImageData imageData = ImageDataFactory.create(file.getAbsolutePath());
            com.itextpdf.layout.element.Image image = new Image(imageData);


            //add image to pdf
            document.add(image);

            //close the document
            document.close();

            //return pdf file
            return output_file;
        } catch(Exception e){
            LOGGER.error("Error while converting JPG_TO_PDF", e);
            throw new ImageConversionException("Failed to convert JPG to PDF", e);
        }
    }

    public File convertPNG_TO_JPG(File file) {

        try{
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                throw new ImageConversionException("Unsupported PNG file: " + file.getName());
            }

            String file_name_with_new_extension = file.getName().replaceAll("(?i)\\.png$",".jpg");
            File output_file = new File("output/"+file_name_with_new_extension);
            ImageIO.write(image,"jpg",output_file);
            return output_file;
        } catch(Exception e){
            LOGGER.error("Error while converting PNG to JPG",e);
            throw new ImageConversionException("Failed to convert PNG to JPG", e);
        }

    }

    //almost same code like with convertJPG_TO_PNG
    java.io.File convertPNG_TO_WEBP(java.io.File file) {

        try {
            BufferedImage image = ImageIO.read(file);

            if (image == null) {
                throw new ImageConversionException("Unsupported PNG file: " + file.getName());
            }

            String file_name_with_webp_extension = file.getName().replaceAll("(?i)\\.png$",".webp");

            java.io.File output = new java.io.File("output/"+file_name_with_webp_extension);

            ImageIO.write(image,"webp",output);

            return output;
        }
        catch(IOException e) {
            LOGGER.error("Error converting PNG to webp ", e);
            throw new ImageConversionException("Failed to convert PNG to webp", e);
        }
    }

}
