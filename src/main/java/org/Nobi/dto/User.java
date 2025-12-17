package org.Nobi.dto;

import lombok.*;
import org.Nobi.enums.UserRole;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
public class User {
    private  Long chat_id;
    private  String userName;
    private  String firstName;
    private  String lastName;
    private  UserRole userRole;

    public User(Long chatId,String userName,String firstName, String lastName, UserRole userRole) {
        this.chat_id = chatId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
    }
}
