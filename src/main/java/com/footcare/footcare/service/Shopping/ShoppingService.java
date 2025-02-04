package com.footcare.footcare.service.Shopping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ShoppingService {

    private final RestTemplate restTemplate;

    @Value("${naver.client-id}")
    private String CLIENT_ID;

    @Value("${naver.client-secret}")
    private String CLIENT_SECRET;

    @Value("${naver.api-url}")
    private String PRODUCT_URL;

    @Value("${naver.token-url}")
    private String TOKEN_URL;

    private static String accessToken = "";
    private static long tokenIssuedTime = 0; // 토큰 발급 시간 저장

    public ShoppingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 🔹 토큰이 만료되었는지 확인 (3시간 기준)
    private boolean isTokenExpired() {
        long currentTime = System.currentTimeMillis();
        return accessToken.isEmpty() || (currentTime - tokenIssuedTime) >= 10800000; // 3시간 (1000 * 60 * 60 * 3)
    }

    // 🔹 토큰 발급 또는 유지 후 상품 조회
    public String getProductInfo() {
        // 토큰이 없거나 만료되었으면 새로 발급
        if (isTokenExpired()) {
            System.out.println("🔄 토큰이 만료됨. 새로운 토큰 발급 중...");
            getAccessToken();
        } else {
            System.out.println("✅ 기존 토큰 유지");
        }

        String originProductNo = "50068233"; // 그룹상품번호
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(PRODUCT_URL + originProductNo, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    // 🔹 네이버 API에 액세스 토큰 요청
    private void getAccessToken() {
        long timestamp = System.currentTimeMillis();
        String hashedPassword = generateHashedPassword(CLIENT_ID, timestamp, CLIENT_SECRET);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(TOKEN_URL)
                .queryParam("client_id", CLIENT_ID)
                .queryParam("timestamp", timestamp)
                .queryParam("grant_type", "client_credentials")
                .queryParam("client_secret_sign", hashedPassword)
                .queryParam("type", "SELF");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);

        // 응답에서 토큰 추출 및 저장
        accessToken = extractAccessToken(response.getBody());
        tokenIssuedTime = System.currentTimeMillis(); // 현재 시간 저장
        System.out.println("✅ 새 토큰 발급 완료: " + accessToken);
    }

    // 🔹 해시 생성
    private String generateHashedPassword(String clientId, long timestamp, String clientSecret) {
        try {
            String password = clientId + "_" + timestamp;
            String hashed = BCrypt.hashpw(password, clientSecret);
            return Base64.getEncoder().encodeToString(hashed.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("해시 생성 실패", e);
        }
    }

    // 🔹 응답에서 액세스 토큰 추출
    private String extractAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("토큰 파싱 실패", e);
        }
    }
}
