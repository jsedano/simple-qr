package dev.jsedano.simpleqr.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

  @Bean
  public QRCodeWriter getQRCodeWriter() {
    return new QRCodeWriter();
  }
}
