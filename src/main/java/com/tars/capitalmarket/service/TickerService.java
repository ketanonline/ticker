package com.tars.capitalmarket.service;

import com.tars.capitalmarket.client.KiteClient;
import com.zerodhatech.models.LTPQuote;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class TickerService {

    KiteClient kiteClient;

    public TickerService(KiteClient kiteClient) {
        this.kiteClient = kiteClient;
    }

    public Optional<LTPQuote> getLtp(String instrument) {
        return kiteClient.getLtp(instrument);
    }
}
