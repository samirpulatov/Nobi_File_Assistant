package org.Nobi.exceptions;

public class UserSessionNotFoundException extends NobiBotException{

    public UserSessionNotFoundException(Long chat_id) {
        super("User session not found for chat_id = " + chat_id);
    }
}
