package org.Nobi.exceptions;

public class FileSendException extends NobiBotException {
    public FileSendException(Long chatId, Throwable cause) {
        super("Failed to send file to chatId=" + chatId, cause);
    }
}
