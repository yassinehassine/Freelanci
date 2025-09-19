package com.example.freelanci.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class InternationalizationTest {

    @Autowired
    private MessageSource messageSource;

    @Test
    public void testFrenchMessages() {
        String welcomeMessage = messageSource.getMessage("app.welcome", null, Locale.FRENCH);
        assertThat(welcomeMessage).isEqualTo("Bienvenue sur Freelanci");

        String currentLanguage = messageSource.getMessage("app.language.current", null, Locale.FRENCH);
        assertThat(currentLanguage).isEqualTo("Français");

        String userNotFound = messageSource.getMessage("user.not.found", null, Locale.FRENCH);
        assertThat(userNotFound).isEqualTo("Utilisateur non trouvé");
    }

    @Test
    public void testEnglishMessages() {
        String welcomeMessage = messageSource.getMessage("app.welcome", null, Locale.ENGLISH);
        assertThat(welcomeMessage).isEqualTo("Welcome to Freelanci");

        String currentLanguage = messageSource.getMessage("app.language.current", null, Locale.ENGLISH);
        assertThat(currentLanguage).isEqualTo("English");

        String userNotFound = messageSource.getMessage("user.not.found", null, Locale.ENGLISH);
        assertThat(userNotFound).isEqualTo("User not found");
    }

    @Test
    public void testDefaultLocaleIsFrench() {
        // Test that default messages fall back to French
        String welcomeMessage = messageSource.getMessage("app.welcome", null, new Locale("unknown"));
        assertThat(welcomeMessage).isEqualTo("Bienvenue sur Freelanci");
    }
}