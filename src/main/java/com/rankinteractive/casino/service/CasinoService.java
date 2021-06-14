package com.rankinteractive.casino.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rankinteractive.casino.model.Player;
import com.rankinteractive.casino.model.Wager;
import com.rankinteractive.casino.model.dto.WagerDto;
import com.rankinteractive.casino.repository.PlayerRepository;
import com.rankinteractive.casino.repository.WagerRepository;
@Service
public class CasinoService {
    private WagerRepository wagerRepository;
    private PlayerRepository playerRepository;
	
    @Autowired
    public CasinoService(WagerRepository wagerRepository, PlayerRepository playerRepository){
    	this.wagerRepository = wagerRepository;
    	this.playerRepository = playerRepository;
    }
    public Wager findWager(Long transactionId) {
    	Optional<Wager> wager = wagerRepository.findById(transactionId);
		return wager != null && wager.isPresent() ? wager.get() : null;
	}

	public WagerDto addWager(WagerDto wagerDto) {
		Wager wager = getEntity(wagerDto);
		wager.getPlayer().setBalance(wager.getPlayer().getBalance() - wagerDto.getAmount());
		return getDto(wagerRepository.save(wager), false);
	}

	public WagerDto addWinnings(WagerDto wagerDto) {
		Wager wager = getEntity(wagerDto);
		wager.getPlayer().setBalance(wager.getPlayer().getBalance() + wagerDto.getAmount());
		WagerDto dto = getDto(wagerRepository.save(wager), true);
		return dto;
	}

	private Wager getEntity(WagerDto dto) {
		Wager wager = new Wager();
		Player player = playerRepository.findById(dto.getPlayerId()).get();
		wager.setUsername(dto.getUsername());
		wager.setAmount(dto.getAmount());
		wager.setPlayer(player);
		wager.setPromoCode(dto.getPromoCode());
		wager.setTransactionId(dto.getTransactionId());
		
		return wager;
	}
	
	private WagerDto getDto(Wager wager, boolean isWin) {
		WagerDto wagerDto = new WagerDto();
		Player player = wager.getPlayer();
		wagerDto.setCreatedDate(wager.getCreatedDate());
		wagerDto.setUsername(wager.getUsername());
		wagerDto.setAmount(wager.getAmount());
		wagerDto.setPlayerRemainingBalance(player.getBalance());
		wagerDto.setPlayerId(player.getId());
		wagerDto.setPromoCode(wager.getPromoCode());
		wagerDto.setTransactionId(wager.getTransactionId());
		wager.setWin(isWin ? true : false);
		return wagerDto;
	}
	public List<WagerDto> findLastTenWagersForUser(String username) {
		List<Wager> results = wagerRepository.findByUsernameOrderByCreatedDateDesc(username);
		return results.stream().map(res -> getDto(res, false)).collect(Collectors.toList());
	}
}
