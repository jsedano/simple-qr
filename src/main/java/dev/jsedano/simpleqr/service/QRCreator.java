package dev.jsedano.simpleqr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class QRCreator {

  private final QRCodeWriter qrCodeWriter;

  // https://www.digitalocean.com/community/tutorials/java-qr-code-generator-zxing-example
  // https://stackoverflow.com/a/51395668

  public String createQRImage(String qrCodeText, int size) {
    try {
      return unsafeCreateQRImage(qrCodeText, size);
    } catch (WriterException | IOException e) {
      log.error("Error creating QR code", e);
      return "";
    }
  }

  private String unsafeCreateQRImage(String qrCodeText, int size)
      throws WriterException, IOException {
    // Create the ByteMatrix for the QR-Code that encodes the given String
    Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    BitMatrix byteMatrix =
        qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
    // Make the BufferedImage that are to hold the QRCode
    int matrixWidth = byteMatrix.getWidth();
    BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
    image.createGraphics();

    Graphics2D graphics = (Graphics2D) image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, matrixWidth, matrixWidth);
    // Paint and save the image using the ByteMatrix
    graphics.setColor(Color.BLACK);

    for (int i = 0; i < matrixWidth; i++) {
      for (int j = 0; j < matrixWidth; j++) {
        if (byteMatrix.get(i, j)) {
          graphics.fillRect(i, j, 1, 1);
        }
      }
    }
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(image, "png", bos);
    byte[] imageBytes = bos.toByteArray();

    Base64.Encoder encoder = Base64.getEncoder();
    return encoder.encodeToString(imageBytes);
  }
}
