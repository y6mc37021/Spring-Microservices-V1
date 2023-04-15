package com.company.microservice.currencyexchangeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.microservice.currencyexchangeservice.bean.CurrencyExchange;

public interface CurrencyExchangeServiceRepository extends JpaRepository<CurrencyExchange, Long>{

	public CurrencyExchange findByFromAndTo(String from, String to);
}
