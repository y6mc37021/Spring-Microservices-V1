package com.company.microservice.currencyexchangeservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sampleApi")
	//@Retry(name = "default")
	//@Retry(name="sample-api", fallbackMethod ="hardcodedResponse" )
	//@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	//@RateLimiter(name="default")
	@Bulkhead(name="sample-api")
	public String sampleApi() {
		logger.info("--- CircuitBreakerController ----");
		ResponseEntity<String> entity = new RestTemplate().getForEntity("http://localhost:8000/dummy-api", String.class);
		return entity.getBody();
		//return "Sample API";
	}
	
	public String hardcodedResponse(Exception ex) {
		return "fallback-response";
	}

}
