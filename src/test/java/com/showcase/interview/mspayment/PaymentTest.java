package com.showcase.interview.mspayment;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.showcase.interview.mspayment.model.Payment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaymentTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String baseUrl = "/api/v1/ms-payment/payment/";
	
	@Test
	public void formCustomerCRUDSeq() throws Exception {
		Payment basePayment = new Payment();
		basePayment.setAmount(BigDecimal.valueOf(50000));
		basePayment.setCurrency("IDR");
		basePayment.setOrderId(new Long(1));
		basePayment.setStatus("WAITING_FOR_PAYMENT");
		basePayment.setTrxId("TESTTESTTESTTEST");
		
//		Create Payment
		Payment insertedPayment = createPaymentShouldSuccess(basePayment);
		
//		Read Payment
		queryShouldReturnSpecificPayment(insertedPayment, basePayment.getStatus());
		
//		Update Payment
		insertedPayment.setStatus("PAID");
		updatePaymentProductNameShouldSuccess(insertedPayment, insertedPayment.getId());
		
		queryShouldReturnSpecificPayment(insertedPayment, insertedPayment.getStatus());
		
//		Delete Payment
		deletePaymentShouldSuccess(insertedPayment);
		
		
	}
	
	public Payment createPaymentShouldSuccess(Payment newPayment) throws Exception {

		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// build the request
		HttpEntity<Payment> entity = new HttpEntity<>(newPayment, headers);

		// send POST request
		Payment newInvResult = this.restTemplate.postForObject("http://localhost:" + port + baseUrl + "create",
				entity, Payment.class);
		assertThat(newInvResult).isEqualToIgnoringGivenFields(newPayment, "id", "created_at", "updated_at");
		return newInvResult;

	}
	
	public void queryShouldReturnSpecificPayment(Payment form, String status) throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + baseUrl + form.getId() + "/detail",
				String.class)).contains(status);
	}
	
	public void updatePaymentProductNameShouldSuccess(Payment updatedPayment, Long insertedId) throws Exception {
		String url = "http://localhost:" + port + baseUrl + insertedId + "/update-data";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(updatedPayment);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
		assertThat(response.getBody()).contains(updatedPayment.getStatus());
	}
	
	public void deletePaymentShouldSuccess(Payment inventory) throws Exception {

		String url = "http://localhost:" + port + baseUrl + inventory.getId() + "/delete-data";
		HttpEntity<?> requestEntity = null;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);

		assertThat(response.getBody()).contains("Success");

	}
	
	@Test
	public void queryShouldReturnListPayment() throws Exception {
		assertThat(
				this.restTemplate.getForObject("http://localhost:" + port + baseUrl +"all", String.class))
						.contains("TESTTRXCODE");
	}
	
}