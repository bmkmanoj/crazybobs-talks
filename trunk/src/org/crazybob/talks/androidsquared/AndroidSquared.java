// Copyright 2010 Square, Inc.
package org.crazybob.talks.androidsquared;

import org.crazybob.deck.Deck;
import org.crazybob.deck.templates.Square;

/**
 * Android Squared.
 *
 * @author Eric Burke (eric@squareup.com)
 */
public class AndroidSquared {
  static final String PATH = "src/org/crazybob/talks/androidsquared/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("Android Squared")
        .subtitle("")
        .author("Bob Lee & Eric Burke")
        .company("Square, Inc.");

    

    deck.writePdf(new Square(), "out/androidsquared.pdf", true);
  }
}
