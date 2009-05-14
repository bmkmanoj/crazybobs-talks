package org.crazybob.deck;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Chunk;
import com.lowagie.text.pdf.ColumnText;

import java.util.List;
import java.util.ArrayList;

/**
 *
 *
 */
public class Bullets extends InlineElement {

  final List<Bullet> list = new ArrayList<Bullet>();

  public Bullets add(Text text) {
    return add(text, null);
  }

  public Bullets add(Text text, Bullets children) {
    list.add(new Bullet(text, children));
    return this;
  }

  public Bullets add(String text) {
    return add(text, null);
  }

  public Bullets add(String text, Bullets children) {
    return add(new Text(text), children);
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    writePdf(deck, column, this, 0);
  }

  void writePdf(Deck deck, ColumnText column, Bullets bullets, int depth)
      throws DocumentException {
    Chunk bulletChunk = deck.template.bullet(depth).toChunk(deck);
    Font defaultFont = deck.template.bulletFont(depth);

    for (Bullet bullet : bullets.list) {
      Paragraph container = new Paragraph(bulletChunk);
      container.add(bullet.text.toParagraph(deck, defaultFont));

      container.setIndentationLeft(depth * 30);
      column.addElement(container);

      if (bullet.children != null) {
        writePdf(deck, column, bullet.children, depth + 1);
      }
    }
  }
}
