package com.rankinteractive.casino.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rankinteractive.casino.model.Wager;

public interface WagerRepository extends CrudRepository<Wager, Long>{

	List<Wager> findByUsernameOrderByCreatedDateDesc(String username);

}
