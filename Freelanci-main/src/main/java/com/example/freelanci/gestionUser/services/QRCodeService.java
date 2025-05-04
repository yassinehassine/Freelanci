package com.example.freelanci.gestionUser.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class QRCodeService {

    /**
     * Générer un QR Code et l'enregistrer dans un fichier.
     * @param text Le texte à encoder.
     * @param width Largeur du QR Code.
     * @param height Hauteur du QR Code.
     * @param filePath Chemin où enregistrer l'image.
     */
    public void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

        // Vérifie si le répertoire existe, sinon le créer
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * Générer un QR Code et le retourner sous forme de tableau de bytes.
     * @param text Le texte à encoder.
     * @param width Largeur du QR Code.
     * @param height Hauteur du QR Code.
     * @return Image en bytes (PNG).
     */
    public byte[] generateQRCodeBytes(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        return baos.toByteArray();
    }
}
