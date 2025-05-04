package com.example.freelanci.gestionFreelancer.Services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service

public class StockTeleversement {
    private final Path rootLocation = Paths.get("uploads");

    // Méthode pour stocker un fichier
    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide.");
        }

        // Créez le dossier s'il n'existe pas
        if (!Files.exists(rootLocation)) {
            Files.createDirectories(rootLocation);
        }

        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Enregistrer le fichier
        Path destinationFile = rootLocation.resolve(fileName);
        Files.copy(file.getInputStream(), destinationFile);

        return fileName;
    }

}
