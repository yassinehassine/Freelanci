package com.example.freelanci.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Get current language information
     */
    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentLanguage() {
        Locale currentLocale = LocaleContextHolder.getLocale();
        Map<String, Object> response = new HashMap<>();
        
        response.put("locale", currentLocale.getLanguage());
        response.put("language", currentLocale.getDisplayLanguage(currentLocale));
        response.put("message", messageSource.getMessage("app.language.current", null, currentLocale));
        response.put("switchMessage", messageSource.getMessage("app.language.switch", null, currentLocale));
        
        return ResponseEntity.ok(response);
    }

    /**
     * Switch language to French
     */
    @PostMapping("/switch/french")
    public ResponseEntity<Map<String, Object>> switchToFrench(
            HttpServletRequest request, 
            HttpServletResponse response) {
        
        Locale frenchLocale = Locale.FRENCH;
        localeResolver.setLocale(request, response, frenchLocale);
        
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("locale", "fr");
        responseMap.put("language", "Français");
        responseMap.put("message", messageSource.getMessage("app.language.current", null, frenchLocale));
        responseMap.put("welcomeMessage", messageSource.getMessage("app.welcome", null, frenchLocale));
        
        return ResponseEntity.ok(responseMap);
    }

    /**
     * Switch language to English
     */
    @PostMapping("/switch/english")
    public ResponseEntity<Map<String, Object>> switchToEnglish(
            HttpServletRequest request, 
            HttpServletResponse response) {
        
        Locale englishLocale = Locale.ENGLISH;
        localeResolver.setLocale(request, response, englishLocale);
        
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("locale", "en");
        responseMap.put("language", "English");
        responseMap.put("message", messageSource.getMessage("app.language.current", null, englishLocale));
        responseMap.put("welcomeMessage", messageSource.getMessage("app.welcome", null, englishLocale));
        
        return ResponseEntity.ok(responseMap);
    }

    /**
     * Switch language using language parameter
     */
    @PostMapping("/switch")
    public ResponseEntity<Map<String, Object>> switchLanguage(
            @RequestParam(name = "lang") String language,
            HttpServletRequest request, 
            HttpServletResponse response) {
        
        Locale targetLocale;
        String langCode;
        String langName;
        
        switch (language.toLowerCase()) {
            case "en":
            case "english":
                targetLocale = Locale.ENGLISH;
                langCode = "en";
                langName = "English";
                break;
            case "fr":
            case "french":
            case "français":
            default:
                targetLocale = Locale.FRENCH;
                langCode = "fr";
                langName = "Français";
                break;
        }
        
        localeResolver.setLocale(request, response, targetLocale);
        
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("success", true);
        responseMap.put("locale", langCode);
        responseMap.put("language", langName);
        responseMap.put("message", messageSource.getMessage("app.language.current", null, targetLocale));
        responseMap.put("welcomeMessage", messageSource.getMessage("app.welcome", null, targetLocale));
        
        return ResponseEntity.ok(responseMap);
    }

    /**
     * Get available languages
     */
    @GetMapping("/available")
    public ResponseEntity<Map<String, Object>> getAvailableLanguages() {
        Map<String, Object> languages = new HashMap<>();
        
        Map<String, String> french = new HashMap<>();
        french.put("code", "fr");
        french.put("name", "Français");
        french.put("displayName", "French");
        
        Map<String, String> english = new HashMap<>();
        english.put("code", "en");
        english.put("name", "English");
        english.put("displayName", "English");
        
        languages.put("fr", french);
        languages.put("en", english);
        languages.put("default", "fr");
        
        return ResponseEntity.ok(languages);
    }
}