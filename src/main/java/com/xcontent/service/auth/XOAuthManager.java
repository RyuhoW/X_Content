package com.xcontent.service.auth;

import com.google.gson.Gson;
import com.xcontent.config.XApiConfig;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

public class XOAuthManager {
    private static final Logger logger = LogManager.getLogger(XOAuthManager.class);
    private final OkHttpClient client;
    private final Gson gson;

    public XOAuthManager() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public String generateAuthorizationUrl() {
        String state = UUID.randomUUID().toString();
        String codeChallenge = generateCodeChallenge();

        return XApiConfig.OAUTH2_AUTHORIZE_URL + "?" +
               "response_type=code" +
               "&client_id=" + XApiConfig.CLIENT_ID +
               "&redirect_uri=" + URLEncoder.encode(XApiConfig.REDIRECT_URI, StandardCharsets.UTF_8) +
               "&scope=" + URLEncoder.encode(XApiConfig.SCOPE, StandardCharsets.UTF_8) +
               "&state=" + state +
               "&code_challenge=" + codeChallenge +
               "&code_challenge_method=S256";
    }

    public TokenResponse exchangeCodeForToken(String code) {
        RequestBody body = new FormBody.Builder()
            .add("grant_type", "authorization_code")
            .add("code", code)
            .add("redirect_uri", XApiConfig.REDIRECT_URI)
            .add("code_verifier", getStoredCodeVerifier())
            .build();

        Request request = new Request.Builder()
            .url(XApiConfig.OAUTH2_TOKEN_URL)
            .post(body)
            .header("Authorization", getBasicAuthHeader())
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response " + response);
            }
            return gson.fromJson(response.body().string(), TokenResponse.class);
        } catch (IOException e) {
            logger.error("Failed to exchange code for token", e);
            throw new RuntimeException("Authentication failed", e);
        }
    }

    private String getBasicAuthHeader() {
        String credentials = XApiConfig.CLIENT_ID + ":" + XApiConfig.CLIENT_SECRET;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    // Token response class
    public static class TokenResponse {
        private String access_token;
        private String refresh_token;
        private String token_type;
        private int expires_in;

        // Getters and setters
    }

    // Additional helper methods for PKCE
    private String generateCodeChallenge() {
        // Implement PKCE code challenge generation
        return "";
    }

    private String getStoredCodeVerifier() {
        // Retrieve stored code verifier
        return "";
    }
} 