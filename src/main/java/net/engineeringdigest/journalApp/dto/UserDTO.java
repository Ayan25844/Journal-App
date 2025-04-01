package net.engineeringdigest.journalApp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String username;
    private String password;
    private boolean sentimentAnalysis;
}
