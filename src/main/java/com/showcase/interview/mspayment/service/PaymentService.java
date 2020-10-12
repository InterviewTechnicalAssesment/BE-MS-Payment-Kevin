package com.showcase.interview.mspayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.showcase.interview.mspayment.exception.CommitFailedException;
import com.showcase.interview.mspayment.exception.DataNotFoundException;
import com.showcase.interview.mspayment.exception.UndefinedException;
import com.showcase.interview.mspayment.model.Payment;
import com.showcase.interview.mspayment.repository.PaymentRepository;
import com.showcase.interview.mspayment.utils.SuccessTemplateMessage;
import com.showcase.interview.mspayment.utils.Util;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private Util util;

	public Iterable<Payment> getAll() {
		return paymentRepository.findAll();
	}

	public Payment createNew(Payment newData) throws CommitFailedException, UndefinedException {
		try {
			newData.setCreated_at(util.getTimeNow());
			newData.setUpdated_at(util.getTimeNow());
			return paymentRepository.save(newData);
		} catch (Exception e) {
			if (e.getMessage().contains("Error while committing")) {
				throw new CommitFailedException();
			} else {
				throw new UndefinedException(e.toString());
			}
		}
	}

	public Payment getById(long id) throws DataNotFoundException {
		return paymentRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	public Payment updateById(Payment updatedData, Long id) {

		return paymentRepository.findById(id).map(data -> {
			updatedData.setId(id);
			updatedData.setCreated_at(data.getCreated_at());
			data = updatedData;

			data.setUpdated_at(util.getTimeNow());
			return paymentRepository.save(data);
		}).orElseGet(() -> {
			updatedData.setCreated_at(util.getTimeNow());
			updatedData.setUpdated_at(util.getTimeNow());
			return paymentRepository.save(updatedData);
		});
	}

	public ResponseEntity<Object> deleteById(long id) throws DataNotFoundException {

		try {
			if (paymentRepository.findById(id) != null) {
				paymentRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}

}