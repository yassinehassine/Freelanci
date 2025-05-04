package com.example.freelanci.gestionUser.controllers;

import com.example.freelanci.gestionUser.entities.User;
import com.example.freelanci.gestionUser.services.PdfGenerator;
import com.example.freelanci.gestionUser.services.QRCodeService;
import com.example.freelanci.gestionUser.services.UserService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private PdfGenerator pdfGenerator ;

    // Create or Update User (POST/PUT)
    @PostMapping("/addUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Get User by ID (GET)
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all users (GET)
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Delete User by ID (DELETE)
    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update User (PUT)
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            user.setId(id); // Ensure the ID remains the same
            User updatedUser = userService.saveUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get user by username (GET)
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> userOptional = userService.getUserByUsername(username);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get user by email (GET)
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Pour inclure les attributs dans le QR code
    @GetMapping("/user/{id}/qrcode")
    public ResponseEntity<byte[]> getUserByIdForQRCode(@PathVariable Long id) {
        try {
            // Récupérer l'utilisateur à partir de l'ID
            Optional<User> user = userService.getUserById(id);
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Créer une chaîne de caractères avec les attributs de l'utilisateur
            String userData = "Username: " + user.get().getUsername() + "\n" +
                    "Email: " + user.get().getEmail() + "\n" +
                    "Name: " + user.get().getFirstName() + " " + user.get().getLastName() + "\n" +
                    "Description: " + user.get().getProfileDescription() + "\n" +
                    "Phone: " + user.get().getPhoneNumber() + "\n" +
                    "Balance: $" + user.get().getAccountBalance() + "\n" +
                    "Location: " + user.get().getLocation();

            // Générer le QR code pour cette chaîne sous forme byte[]
            byte[] qrCodeByte = generateQRCodeByte(userData);

            // Générer le fichier PDF avec le QR code
            String filePath = "sample.pdf";
            pdfGenerator.generatePdf(filePath, qrCodeByte);

            // Lire le fichier PDF en tant que tableau de bytes
            Path pdfPath = Paths.get(filePath);
            byte[] pdfBytes = Files.readAllBytes(pdfPath);

            // Retourner le PDF dans la réponse
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (WriterException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Méthode pour générer le QR code à partir de la chaîne de caractères
    private byte[] generateQRCodeByte(String data) throws IOException, WriterException {
        return qrCodeService.generateQRCodeBytes(data, 200, 200);
    }

    // Méthode pour l'upload d'une photo
    @PostMapping("/uploadPhoto/{userId}")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long userId, @RequestParam("photo") MultipartFile photo) {
        try {
            // Vérifie si le fichier n'est pas vide
            if (photo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fichier vide");
            }

            // Récupérer l'utilisateur
            Optional<User> userOptional = userService.getUserById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
            }
            User user = userOptional.get();

            // Générer un nom de fichier unique pour la photo
            String fileName = UUID.randomUUID().toString() + "-" + StringUtils.cleanPath(photo.getOriginalFilename());

            // Chemin de sauvegarde de la photo
            Path targetLocation = Paths.get("uploads/photos/" + fileName);

            // Créer le dossier s'il n'existe pas
            Files.createDirectories(targetLocation.getParent());

            // Copier le fichier dans le dossier cible
            Files.copy(photo.getInputStream(), targetLocation);

            // Mettre à jour l'URL de la photo dans le modèle User
            user.setProfilePictureUrl("/uploads/photos/" + fileName);
            userService.saveUser(user); // Sauvegarder l'utilisateur avec la nouvelle URL de photo

            return ResponseEntity.ok("Photo téléchargée avec succès");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload de la photo");
        }
    }

    // Filtrer les utilisateurs par rôle
    @GetMapping("/filterByRole")
    public ResponseEntity<List<User>> filterByRole(@RequestParam User.UserRole role) {
        List<User> users = userService.filterByRole(role);  // Appeler le service pour filtrer par rôle
        return users.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(users, HttpStatus.OK);
    }
}
