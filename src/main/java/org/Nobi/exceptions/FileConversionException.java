package org.Nobi.exceptions;

public class FileConversionException extends NobiBotException{

    public FileConversionException(Long chat_id, String file_name, String action) {
        super("File "+file_name+" could not be converted to "+action + " for a user with chat_id = "+chat_id);
    }
}
