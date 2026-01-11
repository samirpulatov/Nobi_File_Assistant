package org.Nobi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Long chat_id;
    private String user_name;
    private String first_name;
    private String last_name;
}
