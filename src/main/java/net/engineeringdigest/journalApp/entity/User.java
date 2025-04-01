package net.engineeringdigest.journalApp.entity;

import lombok.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @Schema(description = "User's id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "User's email")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$", message = "Invalid email format")
    private String email;
    @NotEmpty
    @Column(unique = true)
    @Schema(description = "User's name")
    private String username;
    @NotEmpty
    @Schema(description = "User's password")
    private String password;
    @Schema(description = "User's opt for journal recommendations")
    private boolean sentimentAnalysis;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<String> roles;
    @JsonManagedReference
    @Schema(description = "User's journal entries")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final List<JournalEntry> journalEntries = new ArrayList<>();
}
