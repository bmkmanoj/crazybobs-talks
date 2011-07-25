// Copyright 2010 Square, Inc.
package org.crazybob.talks.renaissance;

import org.crazybob.deck.Bullets;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Template;
import org.crazybob.deck.Text;
import org.crazybob.deck.templates.Oscon;
import org.crazybob.talks.ThankYou;

/**
 * On the Cusp of a Java Renaissance.
 *
 * @author Bob Lee (bob@squareup.com)
 */
public class Renaissance {
  static final String PATH = "src/org/crazybob/talks/renaissance/";

  static Template template = new Oscon();

  public static void main(String[] args) {

    Deck deck = new Deck()
        .title("On the cusp of a Java renaissance")
        .subtitle("Where we've been and where we're going")
        .author("Bob Lee")
        .company("Square Inc.");

    centerText(deck, "*Ren¥ais¥sance* (n) A revival of or renewed interest in something");

    // For interest to be renewed, presumably it waned at some point.
    // What happened?

    Picture tardis = picture("tardis.png").position(1200, 150);

    revealBullets(deck, "2004", tardis,
        "No WMDs",
        "The Red Sox won the World Series",
        "FaceBook was founded",
        "Struts was #1",
        "*March:* JSF 1.0 released",
        "*July:* Rails open sourced",
        "*September:* Java 5 released"
    );

    sectionSlide(deck, "Turning the Tide");

    sectionSlide(deck, "Scalability");

    sectionSlide(deck, "You can only throw so much hardware at it.");

    revealBullets(deck, "What's changed?",
        "Scalability",
        "Maintainability",
        "Reliability",
        "Resources",
        "Java 5, 6, 7, 8...",
        "Android"
    );

    sectionSlide(deck, "Is JRuby the answer?");

    sectionSlide(deck, "What's new?");

    revealBullets(deck, "What's changed?",
        "Scalability",
        "Maintainability",
        "Reliability",
        "Resources",
        "Java 5, 6, 7, 8...",
        "Android"
    );

    sectionSlide(deck, "Risks");

    deck.add(ThankYou.slide());

    deck.writePdf(template, "out/renaissance.pdf", true);
  }

  static void centerText(Deck deck, String text) {
    deck.add(new Slide().add(new Text(text).toPicture(Deck.WIDTH, 80).center()));
  }

  static void sectionSlide(Deck deck, String text) {
    deck.add(new Slide().add(new Text(text).font(template.titleFont())
        .toPicture(Deck.WIDTH, 120).center()));
  }

  private static Picture picture(String name) {
    return Picture.parseFile("images/renaissance/" + name);
  }

  private static Picture fillBottom(Picture p, int w, int h) {
    p.width(Deck.WIDTH);
    int newHeight = Deck.WIDTH * h / w;
    System.out.println(newHeight);
    p.position(0, Deck.HEIGHT - newHeight);
    return p;
  }

  private static Picture fillRight(Picture p, int w, int h) {
    p.height(Deck.HEIGHT);
    int newWidth = Deck.HEIGHT * w / h;
    p.position(Deck.WIDTH - newWidth, 0);
    return p;
  }

  /** Starts bullets. */
  private static $Bullets bullets() {
    return new $Bullets();
  }

  /**
   * Creates a series of steps, with numbered titles like "Step 1: xxx" and an
   * image for each step.
   */
  private static void steps(Deck deck, String... titlesAndImages) {
    int step = 1;
    for (int i = 0; i < titlesAndImages.length; i += 2) {
      String title = titlesAndImages[i];
      String image = titlesAndImages[i + 1];

      deck.add(new Slide(String.format("Step %d: %s", step++, title))
          .add(picture(image).center()));
    }
  }

  private static void bullets(Deck deck, String title, String... bullets) {
    $Bullets b = new $Bullets();
    for (String bullet : bullets) {
      b.add(bullet);
    }

    deck.add(new Slide(title).add(b));
  }

  private static void revealBullets(Deck deck, String title, String... bullets) {
    revealBullets(deck, title, null, bullets);
  }

  private static void revealBullets(Deck deck, String title, Picture picture, String... bullets) {
    for (int max = 0; max <= bullets.length; max++) {
      Bullets b = new Bullets();
      for (int i = 0; i < max; i++) {
        b.add(bullets[i]);
      }
      Slide slide = new Slide(title).add(b);
      if (picture != null) slide.add(picture);
      deck.add(slide);
    }
  }

  static class $Bullets extends Bullets {
    public $Bullets $(Text text) {
      super.add(text);
      return this;
    }

    public $Bullets $(Text text, Bullets children) {
      super.add(text, children);
      return this;
    }

    public $Bullets $(String text) {
      super.add(text);
      return this;
    }

    public $Bullets $(String text, Bullets children) {
      super.add(text, children);
      return this;
    }
  }
}
