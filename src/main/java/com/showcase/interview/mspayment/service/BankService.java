package com.showcase.interview.mspayment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.showcase.interview.mspayment.exception.CommitFailedException;
import com.showcase.interview.mspayment.exception.DataNotFoundException;
import com.showcase.interview.mspayment.exception.UndefinedException;
import com.showcase.interview.mspayment.model.Bank;
import com.showcase.interview.mspayment.repository.BankRepository;
import com.showcase.interview.mspayment.utils.SuccessTemplateMessage;
import com.showcase.interview.mspayment.utils.Util;

@Service
public class BankService {
	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private Util util;

	public Iterable<Bank> getAll() {
		return bankRepository.findAll();
	}

	public Bank createNew(Bank newData) throws CommitFailedException, UndefinedException {
		try {
			newData.setCreated_at(util.getTimeNow());
			newData.setUpdated_at(util.getTimeNow());
			return bankRepository.save(newData);
		} catch (Exception e) {
			if (e.getMessage().contains("Error while committing")) {
				throw new CommitFailedException();
			} else {
				throw new UndefinedException(e.toString());
			}
		}
	}

	public Bank getById(long id) throws DataNotFoundException {
		return bankRepository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	public Bank updateById(Bank updatedData, Long id) {

		return bankRepository.findById(id).map(data -> {
			updatedData.setId(id);
			updatedData.setCreated_at(data.getCreated_at());
			data = updatedData;

			data.setUpdated_at(util.getTimeNow());
			return bankRepository.save(data);
		}).orElseGet(() -> {
			updatedData.setCreated_at(util.getTimeNow());
			updatedData.setUpdated_at(util.getTimeNow());
			return bankRepository.save(updatedData);
		});
	}

	public ResponseEntity<Object> deleteById(long id) throws DataNotFoundException {

		try {
			if (bankRepository.findById(id) != null) {
				bankRepository.deleteById(id);
			}
		} catch (Exception e) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Object>(new SuccessTemplateMessage(), HttpStatus.OK);
	}
}
