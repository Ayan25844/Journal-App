package net.engineeringdigest.journalApp.dto;

import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalDTO {
    private String title;
    private String content;
    private Sentiment sentiment;
}
