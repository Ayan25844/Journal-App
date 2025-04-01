package net.engineeringdigest.journalApp.controller;

import java.util.*;
import org.springframework.http.*;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import net.engineeringdigest.journalApp.entity.*;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.dto.JournalDTO;
import org.springframework.security.core.Authentication;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("journal")
@Tag(name = "Journal APIs", description = "Create, Read, Update and Delete Journal")
public class JournalEntryController {

    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    @Operation(summary = "Create a journal entry for a user")
    public ResponseEntity<?> createEntry(@RequestBody JournalDTO journalDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        JournalEntry myEntry = new JournalEntry();
        myEntry.setTitle(journalDTO.getTitle());
        myEntry.setContent(journalDTO.getContent());
        myEntry.setSentiment(journalDTO.getSentiment());
        journalEntryService.saveEntry(myEntry, username);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all journal entries of a user")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    @Operation(summary = "Update a journal entry by it's id")
    public ResponseEntity<?> updateJournalEntryById(@RequestParam(name = "id") Long myId,
            @RequestBody JournalDTO journalDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            JournalEntry newEntry = journalEntryService.findById(myId).get();
            String title = journalDTO.getTitle();
            String content = journalDTO.getContent();
            Sentiment sentiment = journalDTO.getSentiment();
            newEntry.setSentiment(sentiment != null ? sentiment : newEntry.getSentiment());
            newEntry.setTitle(title != null && !title.equals("") ? title : newEntry.getTitle());
            newEntry.setContent(content != null && !content.equals("") ? content : newEntry.getContent());
            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    @Operation(summary = "Delete a journal entry by it's id")
    public ResponseEntity<?> deleteJournalEntryById(@RequestParam(name = "id") Long myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
        if (removed) {
            journalEntryService.deleteById(myId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}