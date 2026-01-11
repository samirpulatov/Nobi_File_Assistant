package org.Nobi.services;

import com.aspose.pdf.Document;
import com.aspose.pdf.ExcelSaveOptions;
import com.aspose.pdf.SaveFormat;
import com.aspose.pdf.TextAbsorber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Service
public class PdfService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PdfService.class);


    // converting pdf to word
    File convertPdf_TO_WORD(java.io.File file) {
        try {

            // create an instance of Document object
            Document document = new Document(file.getAbsolutePath());


            String file_name_without_extension = get_rid_of_extension(file);
            String path_to_save = "output/"+file_name_without_extension+".doc";

            // saving file in doc format
            document.save(path_to_save, SaveFormat.Doc);
            document.close();


            return new File(path_to_save);

        } catch (Exception e) {
            LOGGER.error("Error while converting PDF to WORD", e);
            return null;
        }
    }

    File convertPdf_TO_TEXT(File file) {
        try {
            Document document = new Document(file.getAbsolutePath());
            String file_name_without_extension = get_rid_of_extension(file);
            String path_to_save = "output/"+file_name_without_extension+".txt";

            TextAbsorber textAbsorber = new TextAbsorber();
            textAbsorber.visit(document);

            // Save the extracted text in the life
            BufferedWriter writer = new BufferedWriter(new FileWriter(path_to_save));
            writer.write(textAbsorber.getText());
            writer.close();

            document.close();
            return new File(path_to_save);

        } catch (Exception e) {
            LOGGER.error("Error while converting PDF to TEXT", e);
            return null;
        }
    }


    private String get_rid_of_extension(java.io.File file) {

        // remove extension from  file
        String file_name = file.getName();
        int indexOfExtension = file.getName().lastIndexOf(".");

        // in case of an error return just file name
        return (indexOfExtension == -1) ? file_name : file.getName().substring(0,indexOfExtension);
    }


}
