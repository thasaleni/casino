package com.rankinteractive.casino.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.rankinteractive.casino.repository.PlayerRepository;
import com.rankinteractive.casino.repository.WagerRepository;
import com.rankinteractive.casino.service.CasinoService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public abstract class AbstractControllerTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	protected PlayerRepository playerRepository;
	
	@MockBean
	protected WagerRepository wagerRepository;

	@SpyBean
	protected CasinoService casinoService;

	

	@BeforeEach
	public void setUp() {
//		casinoService = new CasinoService(wagerRepository, playerRepository);
		Mockito.reset(playerRepository, wagerRepository);
	}

}
