package org.crazybob.deck;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.ColumnText;
import org.crazybob.deck.Deck;

/**
 *
 */
public abstract class PositionedElement {

  abstract void writePdf(Deck deck) throws DocumentException;  
  protected void layOut(Deck deck, Slide slide) {}
}