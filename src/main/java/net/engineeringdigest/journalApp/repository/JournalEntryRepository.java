package net.engineeringdigest.journalApp.repository;

import org.springframework.stereotype.Repository;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry,Long> {
}
