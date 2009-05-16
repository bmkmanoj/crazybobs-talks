package org.crazybob.deck;

import com.lowagie.text.Paragraph;

import java.awt.Color;

/**
 *
 */
public class Font {

  public enum Face {
    HELVETICA(com.lowagie.text.Font.HELVETICA),
    COURIER(com.lowagie.text.Font.COURIER);

    final int id;
    Face(int id) {
      this.id = id;
    }
  }

  public enum Style {
    NORMAL(com.lowagie.text.Font.NORMAL),
    BOLD(com.lowagie.text.Font.BOLD);

    final int id;
    Style(int id) {
      this.id = id;
    }
  }

  final com.lowagie.text.Font pdfFont;
  final int leading;

  /**
   *
   * @param size in pts
   */
  public Font(Face face, int size, Style style, Color color) {
    this(face, size, style, color, size * 3 / 2);
  }

  /**
   *
   * @param size in pts
   */
  public Font(Face face, int size, Style style, Color color, int leading) {
    this.leading = Deck.ptsToPixels(leading);
    this.pdfFont = new com.lowagie.text.Font(face.id, Deck.ptsToPixels(size),
        style.id, color);
  }

  Paragraph newParagraph() {
    return new Paragraph(leading);
  }

  Paragraph newParagraph(String text) {
    return new Paragraph(leading, text, pdfFont);
  }
}
