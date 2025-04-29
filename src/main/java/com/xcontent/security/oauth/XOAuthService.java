package com.xcontent.security.oauth;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class XOAuthService {
    private static final Logger logger = LoggerFactory.getLogger(XOAuthService.class);
    
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String authorizationEndpoint = "https://api.twitter.com/2/oauth2/authorize";
    private final String tokenEndpoint = "https://api.twitter.com/2/oauth2/token";

    public XOAuthService() {
        this.clientId = System.getenv("X_CLIENT_ID");
        this.clientSecret = System.getenv("X_CLIENT_SECRET");
        this.redirectUri = System.getenv("X_REDIRECT_URI");

        if (clientId == null || clientSecret == null || redirectUri == null) {
            throw new IllegalStateException("X.com OAuth credentials are not properly configured");
        }
    }

    public String getAuthorizationUrl() {
        String scope = "tweet.read tweet.write users.read offline.access";
        
        return authorizationEndpoint + "?" +
                "response_type=code" +
                "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) +
                "&state=" + generateState();
    }

    public OAuthTokens exchangeCodeForTokens(String code) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(tokenEndpoint);
            
            String credentials = Base64.getEncoder().encodeToString(
                    (clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));
            
            post.setHeader("Authorization", "Basic " + credentials);
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            String body = "grant_type=authorization_code" +
                         "&code=" + code +
                         "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);

            post.setEntity(new StringEntity(body));

            return client.execute(post, response -> {
                // トークンレスポンスの処理
                // 実際の実装ではJSONレスポンスをパースしてOAuthTokensオブジェクトを返す
                return new OAuthTokens("access_token", "refresh_token");
            });
        } catch (IOException e) {
            logger.error("Error exchanging code for tokens", e);
            throw new RuntimeException("Error during OAuth token exchange", e);
        }
    }

    private String generateState() {
        byte[] randomBytes = new byte[32];
        new java.security.SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    public static class OAuthTokens {
        private final String accessToken;
        private final String refreshToken;

        public OAuthTokens(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
