// Copyright 2010 Square, Inc.
package org.crazybob.talks.androidsquared;

import org.crazybob.deck.*;
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

    Template template = new Square();

    // Break it down into main sections for ease of collaboration.
    squarewave(template, deck);
    shakeSensor(template, deck);
    retrofit(template, deck);

    deck.writePdf(template, "out/androidsquared.pdf", true);
  }

  private static void squarewave(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "Swipe Decoding"));

    deck.add(new Slide("Square Reader")
        .add(fillRight(Picture.parseFile("images/androidsquared/reader.png"),
            362, 393))
        .add(new Text("Acts as a microphone")));

    // TODO
  }

  private static void shakeSensor(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "Shake Detection"));
    // TODO
  }

  private static void retrofit(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "Retrofit"));

    // TODO
  }

  /** Returns a section title slide. */
  private static Slide sectionTitleSlide(Template template, String title) {
    Slide slide = new Slide();
    Box titleBox = new Box(300, 300, 50, 50);
    titleBox.add(new Text(title).font(template.titleFont().scale(125)));

    // TODO a horizontal line would look good here

    slide.add(titleBox);

    return slide;
  }

  private static Picture fillRight(Picture p, int w, int h) {
    p.height(Deck.HEIGHT);
    int newWidth = Deck.HEIGHT * w / h;
    p.position(Deck.WIDTH - newWidth, 0);
    return p;
  }
}
