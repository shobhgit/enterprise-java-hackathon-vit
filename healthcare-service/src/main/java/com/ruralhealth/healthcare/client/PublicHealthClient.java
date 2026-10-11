
package com.ruralhealth.healthcare.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
public class PublicHealthClient {

    private final RestClient restClient;

    public PublicHealthClient(RestClient publicHealthRestClient) {
        this.restClient = publicHealthRestClient;
    }

    public List<Map<String, Object>> getMedicines() {
        return restClient.get()
                .uri("/api/medicines")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public List<Map<String, Object>> getLowStockMedicines() {
        return restClient.get()
                .uri("/api/medicines/low-stock")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Map<String, Object> getPendingSummary() {
        return restClient.get()
                .uri("/api/pending-summary")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public Map<String, Object> get(String path) {
        return restClient.get().uri(path).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public List<Map<String, Object>> getList(String path) {
        return restClient.get().uri(path).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public Map<String, Object> post(String path, Map<String, Object> body) {
        return restClient.post().uri(path).body(body).retrieve().body(new ParameterizedTypeReference<>() {});
    }

    public Map<String, Object> patch(String path, Map<String, Object> body) {
        return restClient.patch().uri(path).body(body).retrieve().body(new ParameterizedTypeReference<>() {});
    }

}
