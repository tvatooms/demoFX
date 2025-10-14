package com.tva.FXapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@RestController
public class PartitionClientController {

	private RestClient restClient;

	public PartitionClientController() {
		System.out.println("test");
	}

	public PartitionClientController(String uri) {
		this.restClient = RestClient.builder().baseUrl(uri).build();

	}

	// @GetMapping("/")
	public void ping() {
		System.out.println("---->"+restClient.get().uri("/Partitions").retrieve().toString());

	}

}