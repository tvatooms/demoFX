package com.tva.FXapplication;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

public class AsyncRestClient {
	private final WebClient webClient;

	public AsyncRestClient(String uri) {
		this.webClient = WebClient.builder().baseUrl(uri).build();
	}

	public Mono<String> isConnected() {
		return webClient.get().uri("/").accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(String.class);
	}

	public Mono<HttpStatusCode> sendFile(FileSystemResource image, String fileId) {
		MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
		bodyBuilder.part("file", image);
		bodyBuilder.part("name", fileId);
		Mono<HttpStatusCode> httpStatusMono = webClient.post().uri("/Partitions/add")
				.contentType(MediaType.MULTIPART_FORM_DATA).body(BodyInserters.fromMultipartData(bodyBuilder.build()))
				.exchangeToMono(response -> {
					if (response.statusCode().equals(HttpStatus.OK)) {
						return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
					} else {
						throw new RuntimeException("Error uploading file:" + response.statusCode());
					}
				});
		return httpStatusMono;
	}

	public Mono<HttpStatusCode> testPost(String val1, String val2) {
		MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
		bodyBuilder.part("key1", val1);
		bodyBuilder.part("key2", val2);
		Mono<HttpStatusCode> httpStatusMono = webClient.post().uri("/Partitions/test")
				.contentType(MediaType.MULTIPART_FORM_DATA).body(BodyInserters.fromMultipartData(bodyBuilder.build()))
				.exchangeToMono(response -> {
					if (response.statusCode().equals(HttpStatus.OK)) {
						return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
					} else {
						throw new RuntimeException("Error uploading file:" + response.statusCode());
					}
				});
		return httpStatusMono;
	}
};
