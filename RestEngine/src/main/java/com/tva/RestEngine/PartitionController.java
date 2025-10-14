package com.tva.RestEngine;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class PartitionController {

	private final PartitionRepository repository;

	PartitionController(PartitionRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping("/")
	String getVersion()
	{
		return ("Partition service v0.5");
	}
	// tag::get-aggregate-root[]
	@GetMapping("/partitions")
	CollectionModel<EntityModel<Partition>> all() {

		List<EntityModel<Partition>> Partitions = repository.findAll().stream()
				.map(Partition -> EntityModel.of(Partition,
						linkTo(methodOn(PartitionController.class).one(Partition.getId())).withSelfRel(),
						linkTo(methodOn(PartitionController.class).all()).withRel("Partitions")))
				.collect(Collectors.toList());

		return CollectionModel.of(Partitions, linkTo(methodOn(PartitionController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/Partitions")
	Partition newPartition(@RequestBody Partition newPartition) {
		return repository.save(newPartition);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/Partitions/{id}")
	EntityModel<Partition> one(@PathVariable Long id) {

		Partition Partition = repository.findById(id) //
				.orElseThrow(() -> new PartitionNotFoundException(id));

		return EntityModel.of(Partition, //
				linkTo(methodOn(PartitionController.class).one(id)).withSelfRel(),
				linkTo(methodOn(PartitionController.class).all()).withRel("Partitions"));
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
}
