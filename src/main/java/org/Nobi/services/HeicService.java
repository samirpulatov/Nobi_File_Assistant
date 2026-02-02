package org.Nobi.services;
import openize.heic.decoder.HeicImage;

import org.Nobi.exceptions.ImageConversionException;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class HeicService {

    private final static Logger LOGGER = LoggerFactory.getLogger(HeicImage.class);

    public File convertHEIC_TO_PNG(File file) {
        String file_name_with_png_extension = file.getName().replaceAll("(?i)\\.heic$",".png");
        return convert(file, file_name_with_png_extension);
    }

    public File convertHEIC_TO_JPG(File file) {
        String file_name_with_png_extension = file.getName().replaceAll("(?i)\\.heic$",".jpg");
        return convert(file, file_name_with_png_extension);
    }

    public File convertHEIC_TO_PDF(File file) {
        String file_name_with_pdf_extension = file.getName().replaceAll("(?i)\\.heic$",".pdf");
        File output_file = new File("output/"+"compressed__"+file_name_with_pdf_extension);
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\Samir\\Installations\\ImageMagick-7.1.2-Q16-HDRI\\magick.exe",
                    file.getAbsolutePath(),
                    "-density", "150",        // ↓ DPI
                    "-resize", "1240x1240>",  // ↓ (A4 ~150dpi)
                    "-quality", "80",         // ↓ JPEG quality
                    "-strip",                 // remove metadata
                    "-colorspace", "sRGB",

                    output_file.getAbsolutePath()
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LOGGER.error("ImageMagick compression failed, exit code: {}", exitCode);
                throw new ImageConversionException("Failed to convert HEIC to PDF");
            }

            return output_file;

        } catch (Exception magickEx) {
            LOGGER.error("Failed to convert HEIC to PNG with ImageMagick", magickEx);
            throw new ImageConversionException("Failed to convert HEIC to PDF", magickEx);
        }

    }


    @Nullable
    private File convert(File file, String file_name_with_new_extension) {
        File output_file = new File("output/"+file_name_with_new_extension);
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "C:\\Users\\Samir\\Installations\\ImageMagick-7.1.2-Q16-HDRI\\magick.exe",
                    "convert",
                    file.getAbsolutePath(),
                    output_file.getAbsolutePath()
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LOGGER.error("ImageMagick conversion failed, exit code: {}", exitCode);
                throw new ImageConversionException("Failed to convert HEIC");
            }

            return output_file;

        } catch (Exception magickEx) {
            LOGGER.error("Failed to convert HEIC to PNG with ImageMagick", magickEx);
            throw new ImageConversionException("Failed to convert HEIC to PNG", magickEx);
        }
    }


}
