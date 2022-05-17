package com.tars.capitalmarket.controller;

import com.tars.capitalmarket.service.TickerService;
import com.zerodhatech.models.LTPQuote;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Singleton;

import java.util.Optional;

@Controller
public class TickerController {

    TickerService tickerService;

    public TickerController(TickerService tickerService) {
        this.tickerService = tickerService;
    }

    @Get("/ltp/{instrument}")
    public Optional<LTPQuote> getLtp(String instrument) {
        return tickerService.getLtp("NSE:NIFTY BANK");
    }

}
