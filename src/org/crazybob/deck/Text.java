package org.crazybob.deck;

import com.lowagie.text.Paragraph;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Chunk;
import com.lowagie.text.pdf.ColumnText;

/**
 *
 */
public class Text extends InlineElement {

  final String s;
  Font font;

  public Text(String s) {
    this.s = s;
  }

  public Text font(Font font) {
    this.font = font;
    return this;
  }

  Paragraph toParagraph(Deck deck, Font defaultFont) {
    return new Paragraph(s, font == null ? defaultFont.pdfFont : font.pdfFont);
  }

  Chunk toChunk(Deck deck) {
    return new Chunk(s, font == null ? deck.template.defaultFont().pdfFont
        : font.pdfFont);
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    column.addElement(toParagraph(deck, deck.template.defaultFont()));
  }
}
