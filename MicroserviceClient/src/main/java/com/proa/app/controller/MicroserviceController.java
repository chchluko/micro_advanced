package com.proa.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proa.app.entities.Client;
import com.proa.app.exceptions.ClientNotFoundException;
import com.proa.app.services.IService;

@RestController
@RequestMapping("/client")
public class MicroserviceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MicroserviceController.class);
	@Autowired
	private IService service;

	// insert
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody Client c) {
		try {
			if (service.insert(c)) {
				return new ResponseEntity<>("Client inserted", HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			LOGGER.error("ERROR INSERT {}", ex.getMessage());
		}

		return new ResponseEntity<>("Error inserting client", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// select all
	@GetMapping
	public ResponseEntity<List<Client>> selectAll() {
		return new ResponseEntity<>(service.selectAll(), HttpStatus.OK);

	}

	// update
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Client c) {
		try {
			if (service.update(c)) {
				return new ResponseEntity<>("Client updated", HttpStatus.OK);
			}
		} catch (ClientNotFoundException ex) {
			LOGGER.info(ex.getMessage());
			return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("ERROR UPDATE {}", ex.getMessage());
		}

		return new ResponseEntity<>("Error updating client", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/id")
	public ResponseEntity<Client> selectById(@RequestParam Long id) {
		try {

			return new ResponseEntity<>(service.findById(id), HttpStatus.OK);

		} catch (ClientNotFoundException ex) {
			LOGGER.info(ex.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("ERROR SELECT BY ID {}", ex.getMessage());
		}

		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	
	// delete
	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam Long id) {
		try {
			if (service.delete(id)) {
				return new ResponseEntity<>("Client deleted", HttpStatus.OK);
			}
		} catch (ClientNotFoundException ex) {
			LOGGER.info(ex.getMessage());
			return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("ERROR DELETE {}", ex.getMessage());
		}

		return new ResponseEntity<>("Error deleting client", HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
}
