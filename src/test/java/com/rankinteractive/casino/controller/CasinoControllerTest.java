package com.rankinteractive.casino.controller;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.rankinteractive.casino.model.Player;
import com.rankinteractive.casino.model.Wager;
import com.rankinteractive.casino.model.dto.WagerDto;
import com.rankinteractive.casino.model.dto.WagerListRequestDto;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CasinoControllerTest extends AbstractControllerTest {

	@Test
	public void shouldReturnBalance() throws Exception {
		Player player = getPlayer();

		when(playerRepository.findById(1234L)).thenReturn(Optional.of(player));

		mockMvc.perform(get("/casino/players/1234/balance").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.Balance", is(50.0)));

	}

	@Test
	public void shouldDeductWagerAmountFromBalances() throws Exception {

		WagerDto wager = getWager();
		Player player = getPlayer();
		when(wagerRepository.findById(123456L)).thenReturn(Optional.empty());
		when(playerRepository.findById(1234L)).thenReturn(Optional.of(player));
		when(playerRepository.save(Mockito.any(Player.class))).thenAnswer(new Answer<Player>() {
			@Override
			public Player answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Player) args[0];
			}
		});
		when(wagerRepository.save(Mockito.any(Wager.class))).thenAnswer(new Answer<Wager>() {
			@Override
			public Wager answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Wager) args[0];
			}
		});
		Gson gson = new Gson();
		mockMvc.perform(post("/casino/wagers/123456/play").content(gson.toJson(wager))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.transactionId", is(123456))).andExpect(jsonPath("$.playerId", is(1234)))
				.andExpect(jsonPath("$.promoCode", is("")))
				.andExpect(jsonPath("$.playerRemainingBalance", is(30.0)))
				.andExpect(jsonPath("$.amount", is(20.0)));

		mockMvc.perform(post("/casino/wagers/123456/play").content(gson.toJson(wager))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.transactionId", is(123456))).andExpect(jsonPath("$.playerId", is(1234)))
				.andExpect(jsonPath("$.promoCode", is("")))
				.andExpect(jsonPath("$.playerRemainingBalance", is(10.0)))
				.andExpect(jsonPath("$.amount", is(20.0)));

		mockMvc.perform(post("/casino/wagers/123456/play").content(gson.toJson(wager))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.I_AM_A_TEAPOT.value()));

	}
	@Test
	public void shouldReturnBadRequestIfPlayerDoesntExist() throws Exception {

		WagerDto wager = getWager();
		when(wagerRepository.findById(123456L)).thenReturn(Optional.empty());
		when(playerRepository.findById(1234L)).thenReturn(Optional.empty());
		Gson gson = new Gson();
		mockMvc.perform(post("/casino/wagers/123456/play").content(gson.toJson(wager))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	public void shouldReturnFoundWagers() throws Exception {
		WagerListRequestDto request = new WagerListRequestDto();
		request.setPassword("swordfish");
		request.setUsername("hacker");
		WagerDto wager = getWager();
		Player player = getPlayer();
		when(wagerRepository.findById(123456L)).thenReturn(Optional.empty());
		when(playerRepository.findById(1234L)).thenReturn(Optional.of(player));
		when(playerRepository.save(Mockito.any(Player.class))).thenAnswer(new Answer<Player>() {
			@Override
			public Player answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Player) args[0];
			}
		});
		when(wagerRepository.save(Mockito.any(Wager.class))).thenAnswer(new Answer<Wager>() {
			@Override
			public Wager answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (Wager) args[0];
			}
		});
		


		Gson gson = new Gson();
		mockMvc.perform(post("/casino/wagers/123456/play").content(gson.toJson(wager))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.transactionId", is(123456))).andExpect(jsonPath("$.playerId", is(1234)))
				.andExpect(jsonPath("$.promoCode", is("")))
				.andExpect(jsonPath("$.playerRemainingBalance", is(30.0)))
				.andExpect(jsonPath("$.amount", is(20.0)));

		mockMvc.perform(post("/casino/wagers/123456/play").content(gson.toJson(wager))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.transactionId", is(123456))).andExpect(jsonPath("$.playerId", is(1234)))
				.andExpect(jsonPath("$.promoCode", is("")))
				.andExpect(jsonPath("$.playerRemainingBalance", is(10.0)))
				.andExpect(jsonPath("$.amount", is(20.0)));
		
		mockMvc.perform(post("/casino/wagers").content(gson.toJson(request))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	
		
		WagerListRequestDto wrongPassword = new WagerListRequestDto();
		mockMvc.perform(post("/casino/wagers").content(gson.toJson(wrongPassword))
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
	}
//
//	@Test
//	public void shouldAddComment() throws Exception {
//
//		// given
//		String commentBody = "{\"content\":\"Test content\", \"author\":\"John Doe\"}";
//		NewCommentDto newComment = createComment("Test content", "John Doe");
//
//		// when
//		when(commentService.addComment(newComment)).thenReturn(1L);
//
//		// then
//		mockMvc.perform(post("/posts/1/comments")
//				.content(commentBody)
//				.contentType(APPLICATION_JSON_UTF8)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isCreated());
//	}
//
//	private NewCommentDto createComment(String content, String author) {
//		NewCommentDto newComment = new NewCommentDto();
//		newComment.setContent(content);
//		newComment.setAuthor(author);
//		return newComment;
//	}
	private Player getPlayer() {
		Player player = new Player();
		player.setId(1234L);
		player.setBalance(50.0);
		player.setName("John Doe");
		return player;
	}

	private WagerDto getWager() {
		WagerDto wager = new WagerDto();
		wager.setUsername("hacker");
		wager.setAmount(20.0);
		wager.setPlayerId(1234L);
		wager.setPromoCode("");
		wager.setTransactionId(123456L);
		return wager;
	}

}