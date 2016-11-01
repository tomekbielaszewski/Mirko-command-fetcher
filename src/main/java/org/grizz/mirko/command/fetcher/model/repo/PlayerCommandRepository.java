package org.grizz.mirko.command.fetcher.model.repo;

import org.grizz.mirko.command.fetcher.model.PlayerCommand;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerCommandRepository extends MongoRepository<PlayerCommand, String> {
}
