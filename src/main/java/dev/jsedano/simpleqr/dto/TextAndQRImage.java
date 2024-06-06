package dev.jsedano.simpleqr.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TextAndQRImage {

  private String text;
  private String base64Image;
}
