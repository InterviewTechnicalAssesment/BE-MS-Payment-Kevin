package com.showcase.interview.mspayment.repository;

import org.springframework.data.repository.CrudRepository;

import com.showcase.interview.mspayment.model.Bank;

public interface BankRepository extends CrudRepository<Bank, Long> {

}
