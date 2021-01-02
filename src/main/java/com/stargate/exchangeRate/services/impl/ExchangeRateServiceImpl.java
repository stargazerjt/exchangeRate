package com.stargate.exchangeRate.services.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stargate.exchangeRate.DAO.ExchangeRateRepository;
import com.stargate.exchangeRate.models.CurrencyRate;
import com.stargate.exchangeRate.models.ExchangeSet;
import com.stargate.exchangeRate.services.ExchangeRateService;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private static String DATA_URL = "https://openexchangerates.org/api/latest.json?app_id=fa600c0aa15e445c8094e6266d072837";
    
    @Autowired
    ExchangeRateRepository exchangeRateRepo;
    
	@Override
    @PostConstruct
    @Scheduled(cron = "0 */30 * * * *")
    public void fetchAllData() throws IOException, InterruptedException, ParseException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DATA_URL))
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONParser jsonParser = new JSONParser();
        org.json.simple.JSONObject jsonObject = (JSONObject) jsonParser.parse(httpResponse.body());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ExchangeSet exchangeSet = objectMapper.readValue(jsonObject.toString(),ExchangeSet.class);
//        List<CountryRate> countriesRate=new ArrayList<>();
        
        System.out.println("exchangeSet.getBase():: "+exchangeSet.getBase());
        
        for(Map.Entry m:exchangeSet.getRates().entrySet()){  
     	   	
        	System.out.println(m.getKey()+" : "+m.getValue());  
        	
        	CurrencyRate countryRate=new CurrencyRate();
        	countryRate.setCurrencyCode((String)m.getKey());
        	countryRate.setExchangeRate((Double)m.getValue());
     	   	countryRate.setCreatedDate(new Date());
     	   	exchangeRateRepo.save(countryRate);
        } 
	}
}
