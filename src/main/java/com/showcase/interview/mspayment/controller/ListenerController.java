package com.showcase.interview.mspayment.controller;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.interview.mspayment.model.Payment;

@Component
public class ListenerController {
	@Autowired
	protected ObjectMapper mapper;

	@Autowired
	private PaymentController paymentController;

	@RabbitListener(queues = "ms-payment-queue")
	public void receiveMessage(Payment newPayment) throws IOException {
		if (newPayment == null) {
			throw new AmqpRejectAndDontRequeueException("Received message is null");
		}
		paymentController.createNew(newPayment);
	}

}

