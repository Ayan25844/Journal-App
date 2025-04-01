package net.engineeringdigest.journalApp.service;

import java.util.*;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import net.engineeringdigest.journalApp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public void saveEntry(JournalEntry journalEntry, String name) {
        User user = userService.findByUsername(name);
        journalEntry.setUser(user);
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(Long id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteById(Long id) {
        journalEntryRepository.deleteById(id);
    }

}
