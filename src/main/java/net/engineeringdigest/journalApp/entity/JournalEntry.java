package net.engineeringdigest.journalApp.entity;

import lombok.*;
import java.time.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import net.engineeringdigest.journalApp.enums.Sentiment;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "journal_entries")
public class JournalEntry {
    @Id
    @Schema(description = "Journal's id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Schema(description = "Journal's title")
    private String title;
    @Schema(description = "Journal's content")
    private String content;
    @Schema(description = "Journal's date of purchase")
    private LocalDateTime date;
    @Schema(description = "Journal's sentiment")
    private Sentiment sentiment;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
}
