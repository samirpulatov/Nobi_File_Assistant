package org.Nobi.config;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class StorageConfigForFiles {

    private final static Logger LOGGER = LoggerFactory.getLogger(StorageConfigForFiles.class);

    @Value("${storage.download-dir}")
    private String downloadDir;

    @Value("${storage.output-dir}")
    private String outputDir;

    @PostConstruct //run when spring gets started
    public void init() {
        createDir(downloadDir);
        createDir(outputDir);
        LOGGER.info("Created downloads and output directory");
    }

    public void createDir(String path) {
        File dir = new File(path);
        if(!dir.exists()) {
            boolean created = dir.mkdirs();
            if(!created) {
                LOGGER.error("Could not create directory: {}", path);
            }
        }
    }

}
