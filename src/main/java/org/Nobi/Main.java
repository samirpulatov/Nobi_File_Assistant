package org.Nobi;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@SpringBootApplication
public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting Application");

//        try {
//            File obj = new File("downloads/robot-7727525_1280.jpg");
//
//            if(obj.createNewFile()) {
//                System.out.println("File created: "+obj.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//
//            int index = obj.getName().lastIndexOf(".");
//            String newFileName = obj.getName().substring(0,index);
//
//
//            //trying to convert jpg file to png
//            BufferedImage image = ImageIO.read(obj);
//
//            File output = new File("output/"+newFileName+".png");
//
//            ImageIO.write(image,"png",output);
//
//            File output2 = new File("output/"+newFileName+".webp");
//            ImageIO.write(image,"webp",output2);
//
//            //Create PDF writer
//            var writer = new PdfWriter("output/"+newFileName+".pdf");
//            PdfDocument pdfDocument = new PdfDocument(writer);
//
//            //itext library
//            Document document = new Document(pdfDocument);
//
//            //Load image
//            ImageData imageData = ImageDataFactory.create("downloads/robot-7727525_1280.jpg");
//            Image image1 = new Image(imageData);
//
//            //add image to pdf
//            document.add(image1);
//
//            //close document
//            document.close();
//
//        }
//
//        catch (IOException e) {
//            System.out.println("An error has occurred");
//            e.printStackTrace();
//        }


        SpringApplication.run(Main.class, args);
    }
}
