package de.bambussoft.immopush.repo;

import de.bambussoft.immopush.fetch.filter.FilterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterEntryRepository extends JpaRepository<FilterEntry, Long> {
    List<FilterEntry> findByChatId(String chatId);

    List<FilterEntry> findByChatIdAndSearchNameAndAttribute(String chatId, String searchName, FilterAttribute attribute);
}
