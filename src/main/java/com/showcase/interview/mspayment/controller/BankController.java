package com.showcase.interview.mspayment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.showcase.interview.mspayment.exception.CommitFailedException;
import com.showcase.interview.mspayment.exception.DataNotFoundException;
import com.showcase.interview.mspayment.exception.UndefinedException;
import com.showcase.interview.mspayment.model.Bank;
import com.showcase.interview.mspayment.service.BankService;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/ms-payment/bank", produces = { "application/json" })
public class BankController {
	
	@Autowired
	private BankService bankService;

	@PostMapping("/create")
	public @ResponseBody Bank createNew(@RequestBody Bank newPayment) {
		try {
			return bankService.createNew(newPayment);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@GetMapping("/{id}/detail")
	public @ResponseBody Bank findById(@PathVariable Long id) {
		try {
			return bankService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody Bank updateData(@RequestBody Bank newPayment, @PathVariable Long id) {
		return bankService.updateById(newPayment, id);
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<Bank> getAll() {
		return bankService.getAll();

	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return bankService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

}