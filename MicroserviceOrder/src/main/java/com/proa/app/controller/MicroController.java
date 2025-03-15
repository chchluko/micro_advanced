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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proa.app.entities.Order;
import com.proa.app.exceptions.OrderNotFoundException;
import com.proa.app.services.IServece;

@RestController
@RequestMapping("/order")
public class MicroController {

	@Autowired
	private IServece service;
	private static final Logger LOGGER = LoggerFactory.getLogger(MicroController.class);

	@PostMapping
	public ResponseEntity<String> insert(@RequestParam long idClient, @RequestParam double total) {

		try {
			if (service.insert(idClient, total)) {
				return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
			}
		} catch (Exception ex) {
			LOGGER.error("Error creating order: {}", ex.getMessage());
		}

		return new ResponseEntity<>("ERROR INSERT", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping
	public ResponseEntity<List<Order>> selectAll() {
		return new ResponseEntity<>(service.selecttAll(), HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> delete(@RequestParam long id) {
		try {
			if (service.delete(id)) {
				return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
			}
		} catch (OrderNotFoundException ex) {
			LOGGER.info(ex.getMessage());
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("Error DELETE {}", ex.getMessage());
		}

		return new ResponseEntity<>("Internal Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
