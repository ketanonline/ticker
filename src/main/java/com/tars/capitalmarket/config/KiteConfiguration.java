package com.tars.capitalmarket.config;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Depth;
import com.zerodhatech.models.Tick;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.micronaut.core.util.StringUtils;
import io.micronaut.serde.annotation.SerdeImport;
import jakarta.inject.Singleton;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SerdeImport(value = Tick.class, mixin = TickMixin.class)
@SerdeImport(value = Depth.class, mixin = DepthMixin.class)
@Factory
public class KiteConfiguration {

    private final Logger Logger = LoggerFactory.getLogger(this.getClass());

    private String userId;
    private String apiKey;
    private String accessToken;
    private String publicToken;

    public KiteConfiguration(@Value("${zerodha.userId}") String userId, @Value("${zerodha.apiKey}") String apiKey,
                             @Value("${zerodha.accessToken}") String accessToken, @Value("${zerodha.publicToken}") String publicToken) {
        this.userId = StringUtils.isEmpty(userId) ? System.getProperty("userId") : userId;
        this.apiKey = StringUtils.isEmpty(apiKey) ? System.getProperty("apiKey") : apiKey;
        this.accessToken = StringUtils.isEmpty(accessToken) ? System.getProperty("accessToken") : accessToken;
        this.publicToken = StringUtils.isEmpty(publicToken) ? System.getProperty("publicToken") : publicToken;
    }

    @Singleton
    public KiteConnect kiteConnect() throws JSONException, IOException, KiteException {

        KiteConnect kiteConnect = new KiteConnect(this.apiKey);
        try {
            kiteConnect.setUserId(this.userId);
            kiteConnect.setAccessToken(this.accessToken);
            kiteConnect.setPublicToken(this.publicToken);
        } catch (Exception e) {
            Logger.info(" kiteConnect Exception Occurred={} ", e);
        }
        return kiteConnect;
    }
}
