package com.tva.RestEngine;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.lang.InterruptedException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import javafx.scene.image.Image;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class PartitionApiController {

	private final PartitionRepository repository;
	// private ProcessingController process;

	PartitionApiController(PartitionRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/")
	String getVersion() {
		return ("Partition service v0.5");
	}

	// tag::get-aggregate-root[]
	@GetMapping("/Partitions")
	CollectionModel<EntityModel<Partition>> all() {

		List<EntityModel<Partition>> Partitions = repository.findAll().stream()
				.map(Partition -> EntityModel.of(Partition,
						linkTo(methodOn(PartitionApiController.class).one(Partition.getId())).withSelfRel(),
						linkTo(methodOn(PartitionApiController.class).all()).withRel("Partitions")))
				.collect(Collectors.toList());

		return CollectionModel.of(Partitions, linkTo(methodOn(PartitionApiController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping(value = "/Partitions/add", produces = MediaType.IMAGE_PNG_VALUE)
	ResponseEntity<?> newPartition(@RequestParam("file") MultipartFile imageData, @RequestParam("name") String name)
			throws IOException {

			
		File fileDest = new File("./"+name + ".png");
		imageData.transferTo(fileDest.toPath());
		//System.out.println("NEW PARTITION " + name + "   " + fileName);
		
		System.out.println("PATH: " + fileDest.toURI().toURL().toString());
		try {
			process(fileDest);
		} catch (InterruptedException e) {

		}
		System.out.println("END");
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@PostMapping(value = "/Partitions/test")
	ResponseEntity<?> test(@RequestParam("key1") String val1, @RequestParam("key2") String val2) {
		System.out.println("TEST " + val1 + "  " + val2);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	// Single item

	// tag::get-single-item[]
	@GetMapping("/Partitions/{id}")
	EntityModel<Partition> one(@PathVariable Long id) {

		Partition Partition = repository.findById(id) //
				.orElseThrow(() -> new PartitionNotFoundException(id));

		return EntityModel.of(Partition, //
				linkTo(methodOn(PartitionApiController.class).one(id)).withSelfRel(),
				linkTo(methodOn(PartitionApiController.class).all()).withRel("Partitions"));
	}
	// end::get-single-item[]

	@PutMapping("/Partitions/{id}")
	Partition replacePartition(@RequestBody Partition newPartition, @PathVariable Long id) {

		return repository.findById(id) //
				.map(Partition -> {
					Partition.setName(newPartition.getName());
					return repository.save(Partition);
				}) //
				.orElseGet(() -> {
					return repository.save(newPartition);
				});
	}

	@DeleteMapping("/Partitions/{id}")
	void deletePartition(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@Async
	public CompletableFuture<Partition> process(File fileSource) throws InterruptedException {
		System.out.println("BEGIN");
		for (int i = 0; i < 5; ++i) {
			Thread.sleep(1000);
			System.out.println(("*"));
		}
		//String fileName = fileSource.getOriginalFilename();
		Partition partition = new Partition("async");
		
		System.out.println("END");
		return CompletableFuture.completedFuture(partition);
	}
}
