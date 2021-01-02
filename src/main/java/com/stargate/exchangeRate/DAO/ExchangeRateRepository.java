package com.stargate.exchangeRate.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stargate.exchangeRate.models.CurrencyRate;

@Repository
public interface ExchangeRateRepository extends JpaRepository<CurrencyRate, Long> {

}
