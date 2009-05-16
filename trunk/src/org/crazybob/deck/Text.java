package org.crazybob.deck;

import com.lowagie.text.Paragraph;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Chunk;
import com.lowagie.text.pdf.ColumnText;

/**
 *
 */
public class Text extends Element {

  final String s;
  Font font;

  public Text(String s) {
    this.s = s;
  }

  public Text font(Font font) {
    this.font = font;
    return this;
  }

  Paragraph toParagraph(Font defaultFont) {
    Font f = font == null ? defaultFont : font;
    return f.newParagraph(s);
  }

  Chunk toChunk(Deck deck) {
    return new Chunk(s, font == null ? deck.template.defaultFont().pdfFont
        : font.pdfFont);
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    column.addElement(toParagraph(deck.template.defaultFont()));
  }
}
