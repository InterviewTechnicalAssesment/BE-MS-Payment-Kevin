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
import com.showcase.interview.mspayment.model.Payment;
import com.showcase.interview.mspayment.service.PaymentService;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/ms-payment/payment", produces = { "application/json" })
public class PaymentController {
	@Autowired
	private PaymentService paymentService;

	@PostMapping("/create")
	public @ResponseBody Payment createNew(@RequestBody Payment newPayment) {
		try {
			return paymentService.createNew(newPayment);
		} catch (CommitFailedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		} catch (UndefinedException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@GetMapping("/{id}/detail")
	public @ResponseBody Payment findById(@PathVariable Long id) {
		try {
			return paymentService.getById(id);
		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}
	}

	@PutMapping("/{id}/update-data")
	public @ResponseBody Payment updateData(@RequestBody Payment newPayment, @PathVariable Long id) {
		return paymentService.updateById(newPayment, id);
	}

	@GetMapping("/all")
	public @ResponseBody Iterable<Payment> getAll() {
		return paymentService.getAll();

	}

	@DeleteMapping("/{id}/delete-data")
	public @ResponseBody ResponseEntity<Object> deleteById(@PathVariable Long id) {
		try {
			return paymentService.deleteById(id);

		} catch (DataNotFoundException e) {
			throw new ResponseStatusException(e.getStatus(), e.getMessage());
		}

	}

}