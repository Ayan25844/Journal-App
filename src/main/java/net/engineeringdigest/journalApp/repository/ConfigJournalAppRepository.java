package net.engineeringdigest.journalApp.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import net.engineeringdigest.journalApp.entity.ConfigJournalApp;

@Repository
public interface ConfigJournalAppRepository extends JpaRepository<ConfigJournalApp, Long> {
}
