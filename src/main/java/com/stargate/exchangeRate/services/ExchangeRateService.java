package com.stargate.exchangeRate.services;

import java.io.IOException;

import org.json.simple.parser.ParseException;

public interface ExchangeRateService {
	void fetchAllData() throws IOException, InterruptedException, ParseException, java.text.ParseException;
}
