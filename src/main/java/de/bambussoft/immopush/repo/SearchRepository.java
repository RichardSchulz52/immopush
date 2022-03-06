package de.bambussoft.immopush.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<SearchRequest, Long> {

    List<SearchRequest> findAllByChatId(String chatId);

    SearchRequest findByChatIdAndSearchName(String chatId, String searchName);
}
