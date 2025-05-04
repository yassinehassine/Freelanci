package com.example.freelanci.freelance.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyMemoryTranslationService {

    private final String API_URL = "https://api.mymemory.translated.net/get";

    public String translate(String text, String sourceLang, String targetLang) {
        // Créer l'URL de requête
        String url = String.format("%s?q=%s&langpair=%s|%s", API_URL, text, sourceLang, targetLang);

        // Effectuer la requête
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        // Traiter la réponse
        if (response.getBody() != null) {
            // Exemple de traitement de la réponse JSON, il faut parser pour extraire la traduction
            String jsonResponse = response.getBody();
            // Ici, on suppose que la réponse contient la traduction sous "responseData.translatedText"
            return jsonResponse.split("\"translatedText\":\"")[1].split("\"")[0];
        }
        return "Error during translation";
    }
}