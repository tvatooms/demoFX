package com.tva.FXapplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartitionClientController {

	private AsyncRestClient restClient;

	public PartitionClientController() {
		System.out.println("test");
	}

	public PartitionClientController(String uri) {
		restClient = new AsyncRestClient(uri);
	}

	// @GetMapping("/")
	public void ping() {
		System.out.println("---->" + restClient.isConnected().block());
		System.out.println(("------------->" + restClient.testPost("val1", "val2").block()));
	}

	public void sendFile(String filePath) {

		try {
			FileSystemResource resource = new FileSystemResource(filePath);
			if (!resource.exists()) {
				System.out.println("file error");
				return;
			}
			// MediaType mediaType =
			// MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file);

			restClient.sendFile(resource, "toto").block();
		}
		catch (Exception e) {
			System.out.println("Error:" + e.getMessage());
		}

	}

}