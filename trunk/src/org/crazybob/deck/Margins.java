package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;

/**
 *
 */
public class Margins {

  final int left, top, right, bottom;

  public Margins(int left, int top, int right, int bottom) {
    this.left = left;
    this.top = top;
    this.right = right;
    this.bottom = bottom;
  }

  public int centerX() {
    return (Deck.WIDTH - right - left) / 2 + left;
  }

  public int centerY() {
    return (Deck.HEIGHT - top - bottom) / 2 + top;
  }

  public int width() {
    return Deck.WIDTH - left - right;
  }

  public int height() {
    return Deck.HEIGHT - top - bottom;  
  }

  void applyTo(ColumnText column) {
    column.setSimpleColumn(left, bottom, Deck.WIDTH - right, Deck.HEIGHT - top);
  }
}
