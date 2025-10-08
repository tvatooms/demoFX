package com.tva.RestEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(PartitionRepository repository) {

		return args -> {
			log.info("Partition 1 " + repository.save(new Partition("mozart")));
			log.info("Partiton 2 " + repository.save(new Partition("beethoven")));
		};
	}
}
