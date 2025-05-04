package com.example.freelanci.gestionUser.controllers;

import com.example.freelanci.gestionUser.services.PdfGenerator;
import com.example.freelanci.gestionUser.services.QRCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    private final QRCodeService qrCodeService;
    @Autowired
    private final PdfGenerator pdfGenerator ;

    public QRCodeController(QRCodeService qrCodeService, PdfGenerator pdfGenerator) {
        this.qrCodeService = qrCodeService;
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String text) {
        try {
            // Générer le code QR (en tant que tableau de bytes)
            byte[] qrCodeImage = qrCodeService.generateQRCodeBytes(text, 200, 200);

            // Générer le fichier PDF avec le QR code
            String filePath = "sample.pdf";
            pdfGenerator.generatePdf(filePath, qrCodeImage);

            // Lire le fichier PDF en tant que tableau de bytes
            Path pdfPath = Paths.get(filePath);
            byte[] pdfBytes = Files.readAllBytes(pdfPath);

            // Retourner le PDF dans la réponse
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (WriterException | IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
