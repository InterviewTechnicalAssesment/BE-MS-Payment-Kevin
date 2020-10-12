package com.showcase.interview.mspayment.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "payment")
public class Payment extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String trxId;

	@Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)
	private String currency;

	@Column(nullable = false)
	private String status;

	@Column(nullable = false)
	private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bank_id")
    private Bank bank;
    
    @Transient
    private String bankName;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getbankName() {
		return bankName = this.bank.getName();
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}
	
	
}
