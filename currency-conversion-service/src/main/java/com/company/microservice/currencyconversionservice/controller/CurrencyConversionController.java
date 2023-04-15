package com.company.microservice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.company.microservice.currencyconversionservice.CurrencyExchangeFeignClient;
import com.company.microservice.currencyconversionservice.bean.CurrencyConversion;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeFeignClient currencyExchangeProxy;
	
	@GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion CalculateCurrencyConvertionRestTemplate(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("from", from);
		map.put("to", to);
		ResponseEntity<CurrencyConversion> responseEntity=  new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, map);
		CurrencyConversion currencyConversion = responseEntity.getBody();
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment()+ " " + "rest template");
		
	}
	
	
	@GetMapping("currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion CalculateCurrencyConvertionFeignClient(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment()+ " " + "feign template");
		
	}

	
}
