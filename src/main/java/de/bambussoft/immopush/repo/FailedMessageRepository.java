package de.bambussoft.immopush.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedMessageRepository extends JpaRepository<FailedMessage, Long> {
}
