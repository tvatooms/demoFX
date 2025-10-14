package com.tva.FXapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import org.springframework.web.client.RestTemplate;

public class AsyncRestClient {
	private final WebClient webClient;

	public AsyncRestClient(String uri) {
		this.webClient = WebClient.builder().baseUrl(uri).build();
	}

	public Mono<String> isConnected() {
		return webClient.get().uri("/").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class);	
	}
};
