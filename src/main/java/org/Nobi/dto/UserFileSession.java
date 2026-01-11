package org.Nobi.dto;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Document;

@Getter
public class UserFileSession {
    private final String fileId;
    private final String fileName;
    private final String mimeType;
    private String selectedAction; //set selectedAction then perform that action

    public UserFileSession(Document document) {
        this.fileId = document.getFileId();
        this.fileName = document.getFileName();
        this.mimeType = document.getMimeType();
    }

    public void setSelectedAction(String action) {
        this.selectedAction = action;
    }
}
