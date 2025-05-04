package com.example.freelanci.gestionUser.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String UPLOAD_DIR = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Fichier vide");
        }

        // Générer un nom unique pour le fichier
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        // Créer le dossier s'il n'existe pas
        Files.createDirectories(filePath.getParent());

        // Sauvegarder le fichier
        Files.write(filePath, file.getBytes());

        return fileName; // Retourne le nom du fichier pour stockage dans la DB
    }
}
