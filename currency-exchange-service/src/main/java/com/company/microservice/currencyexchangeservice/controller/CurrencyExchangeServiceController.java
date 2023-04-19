package com.company.microservice.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.company.microservice.currencyexchangeservice.bean.CurrencyExchange;
import com.company.microservice.currencyexchangeservice.repository.CurrencyExchangeServiceRepository;

@RestController
public class CurrencyExchangeServiceController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeServiceRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceController.class);
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retiveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		logger.info("Retrive Exchance value is From {} to {}", from, to);
		CurrencyExchange currencyExchange =  repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to Find data for "+from+" to "+to);
		}
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}

	private Logger LoggerFactory(Class<CurrencyExchangeServiceController> class1) {
		// TODO Auto-generated method stub
		return null;
	}

}
