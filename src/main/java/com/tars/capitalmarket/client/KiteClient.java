package com.tars.capitalmarket.client;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.LTPQuote;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Singleton
public class KiteClient {

    public final KiteConnect kiteConnect;

    public KiteClient(KiteConnect kiteConnect) {
        this.kiteConnect = kiteConnect;
    }


    public Optional<LTPQuote> getLtp(String instrument) {
        try {
            Map<String, LTPQuote> instrumentsLTPMap = kiteConnect.getLTP(new String[]{instrument});
            System.out.println("instrumentsLTPMap = " + instrumentsLTPMap);
            LTPQuote ltpQuote = instrumentsLTPMap.get(instrument);
            return Optional.of(ltpQuote);
        } catch (KiteException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred.");
        }
    }
}
