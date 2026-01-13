package org.Nobi.exceptions;

public class FileDownloadException extends NobiBotException{
    public FileDownloadException(String file_name) {
        super("Failed to download file that user has sent" + file_name);
    }
}
