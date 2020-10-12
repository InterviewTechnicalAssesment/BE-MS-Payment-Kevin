package com.showcase.interview.mspayment.repository;

import org.springframework.data.repository.CrudRepository;

import com.showcase.interview.mspayment.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
