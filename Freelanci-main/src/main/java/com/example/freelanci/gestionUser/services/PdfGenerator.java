package com.example.freelanci.gestionUser.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.apache.pdfbox.pdmodel.PDImageXObject;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

@Service
public class PdfGenerator {

    public static void generatePdf(String filePath, byte[] qrCodeImage) {
        try {
            // Création d'un nouveau document PDF
            PDDocument document = new PDDocument();

            // Création d'une page (A4 par défaut)
            PDPage page = new PDPage();
            document.addPage(page);

            // Création d'un flux de contenu pour ajouter des éléments sur la page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ajouter du texte (ex. "Badge de l'utilisateur")
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18); // Police et taille
            contentStream.newLineAtOffset(100, 750);  // Position du texte
            contentStream.showText("Badge de l'utilisateur");
            contentStream.endText();

            // Ajouter l'image du QR code
            org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject pdImage = org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject.createFromByteArray(document, qrCodeImage, "QRImage");
            contentStream.drawImage(pdImage, 100, 500);  // Position et dimensions de l'image (x, y)

            // Fermer le flux de contenu
            contentStream.close();

            // Sauvegarder le document
            document.save(filePath);
            document.close();

            System.out.println("✅ PDF créé avec succès : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
