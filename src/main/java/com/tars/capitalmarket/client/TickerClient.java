package com.tars.capitalmarket.client;

import com.tars.capitalmarket.json.JsonMapper;
import com.zerodhatech.models.Tick;
import com.zerodhatech.ticker.KiteTicker;
import com.zerodhatech.ticker.OnConnect;
import com.zerodhatech.ticker.OnTicks;
import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class TickerClient {

    private String accessToken;
    private String apiKey;
    private JsonMapper jsonMapper;
    private KiteTicker tickerProvider;
    ArrayList tokens = new ArrayList(List.of(10081282L));
    Logger logger = LoggerFactory.getLogger(TickerClient.class);

    public TickerClient(@Value("${zerodha.accessToken}") String accessToken,
                        @Value("${zerodha.apiKey}") String apiKey, JsonMapper jsonMapper) {
        this.accessToken = accessToken;
        this.apiKey = apiKey;
        this.jsonMapper = jsonMapper;
        tickerProvider = new KiteTicker(accessToken, apiKey);
    }

    @PostConstruct
    public void init() {
        start();
    }

    public void start() {
        tickerProvider.setOnConnectedListener(new OnConnect() {
            @Override
            public void onConnected() {
                /** Subscribe ticks for token.
                 * By default, all tokens are subscribed for modeQuote.
                 * */
                tickerProvider.subscribe(tokens);
                tickerProvider.setMode(tokens, KiteTicker.modeFull);
            }
        });

        tickerProvider.setOnTickerArrivalListener(new OnTicks() {
            @Override
            public void onTicks(ArrayList<Tick> ticks) {
                ticks.forEach(tick -> logger.info("parsed: " + jsonMapper.serialize(tick)));

            }
        });

        tickerProvider.connect();
    }
}
