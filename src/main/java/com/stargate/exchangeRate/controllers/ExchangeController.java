package com.stargate.exchangeRate.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.stargate.exchangeRate.DAO.ExchangeRateRepository;
import com.stargate.exchangeRate.models.CurrencyRate;

@Controller
public class ExchangeController {

    @Autowired
    ExchangeRateRepository exchangeRateRepo;

    @GetMapping("/")
    public String index(Model model){
    	List<CurrencyRate> currencyRateLs=exchangeRateRepo.findAll();
    	
    	model.addAttribute("currencyRateList",currencyRateLs);
    	
    	return "index";
    }
}
