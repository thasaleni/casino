package com.rankinteractive.casino.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rankinteractive.casino.model.Player;
import com.rankinteractive.casino.model.Wager;
import com.rankinteractive.casino.model.dto.WagerDto;
import com.rankinteractive.casino.model.dto.WagerListRequestDto;
import com.rankinteractive.casino.repository.PlayerRepository;
import com.rankinteractive.casino.service.CasinoService;

@Controller
@RestController
@RequestMapping("/casino")
public class CasinoContoller {

	private final CasinoService casinoService;
	private final PlayerRepository playerRepository;

	@Autowired
	public CasinoContoller(CasinoService casinoService, PlayerRepository playerRepository) {
		this.casinoService = casinoService;
		this.playerRepository = playerRepository;
	}

	@GetMapping(value = "/players/{id}/balance")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<? extends Object> getBalance(@PathVariable() Long id) {
		Optional<Player> player = playerRepository.findById(id);
		if (player == null || player.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(String.format("{Balance: %s}", player.get().getBalance()));
	}

	@PostMapping(value = "/wagers/{transactionId}/play")
	public ResponseEntity<? extends Object> putWager(@PathVariable Long transactionId, @RequestBody WagerDto wagerDto) {
		Wager wager = casinoService.findWager(transactionId);
		if (wager == null) {
			Optional<Player> player = playerRepository.findById(wagerDto.getPlayerId());
			if (player != null && player.isPresent()) {
				if ((player.get().getBalance() - wagerDto.getAmount()) < 0) {
					return new ResponseEntity(HttpStatus.I_AM_A_TEAPOT.getReasonPhrase(), HttpStatus.I_AM_A_TEAPOT);
				}
			} else {
				return ResponseEntity.badRequest().build();
			}

		}
		WagerDto result = casinoService.addWager(wagerDto);
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/wagers/{transactionId}/deposit")
	public ResponseEntity<? extends Object> addWinnings(@PathVariable Long transactionId,
			@RequestBody WagerDto wagerDto) {
		Wager wager = casinoService.findWager(transactionId);
		if (wager == null) {
			return ResponseEntity.ok(casinoService.addWinnings(wagerDto));
		} else {
			return ResponseEntity.ok(wagerDto);
		}
	}

	@PostMapping(value = "/wagers")
	public ResponseEntity<? extends Object> listLastTenWagers(@RequestBody WagerListRequestDto request) {
		if (request.getPassword() == null || !request.getPassword().equals("swordfish")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		List<WagerDto> wagers = casinoService.findLastTenWagersForUser(request.getUsername());
		return ResponseEntity.ok(wagers);
	}
}
