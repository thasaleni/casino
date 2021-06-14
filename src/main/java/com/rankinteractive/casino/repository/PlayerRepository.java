package com.rankinteractive.casino.repository;

import org.springframework.data.repository.CrudRepository;

import com.rankinteractive.casino.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
