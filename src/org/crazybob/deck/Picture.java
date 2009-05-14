package org.crazybob.deck;

import com.lowagie.text.Image;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;

import java.io.IOException;

/**
 *
 */
public class Picture extends PositionedElement {
  
  final Image image;
  int x, y;

  public Picture(Image image) {
    this.image = image;
  }

  public Picture position(int x, int y) {
    this.x = x;
    this.y = y;
    return this;
  }

  public static Picture parseFile(String path) {
    try {
      return new Picture(Image.getInstance(path));
    } catch (BadElementException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void writePdf(Deck deck) throws DocumentException {
    image.setAbsolutePosition(x, Deck.HEIGHT - y);
    deck.document.add(image);
  }
}
