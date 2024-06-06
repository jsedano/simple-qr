package dev.jsedano.simpleqr.controller;

import dev.jsedano.simpleqr.dto.TextAndQRImage;
import dev.jsedano.simpleqr.service.QRCreator;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/simpleqr")
@RequiredArgsConstructor
public class SimpleQRController {

  private final QRCreator qrCreator;

  @RequestMapping("/")
  public String newRandomSelect(Model model) {
    model.addAttribute("textAndQRImage", new TextAndQRImage());
    return "inputQR";
  }

  @RequestMapping("/show")
  public String showQR(Model model, TextAndQRImage textAndQRImage, HttpSession session) {
    if (Objects.isNull(textAndQRImage.getText()) || textAndQRImage.getText().isEmpty()) {
      return "redirect:/simpleqr/";
    }
    String qrImage = qrCreator.createQRImage(textAndQRImage.getText(), 800);
    if (Objects.isNull(qrImage) || qrImage.isEmpty()) {
      return "redirect:/simpleqr/";
    }
    textAndQRImage.setText(textAndQRImage.getText());
    textAndQRImage.setBase64Image(qrCreator.createQRImage(textAndQRImage.getText(), 800));

    model.addAttribute("textAndQRImage", textAndQRImage);
    return "showQR";
  }
}
