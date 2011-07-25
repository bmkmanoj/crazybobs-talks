// Copyright 2010 Square, Inc.
package org.crazybob.talks.renaissance;

import org.crazybob.deck.Box;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Template;
import org.crazybob.deck.Text;
import org.crazybob.deck.templates.Oscon;

/**
 * On the Cusp of a Java Renaissance.
 *
 * @author Bob Lee (bob@squareup.com)
 */
public class Renaissance {
  static final String PATH = "src/org/crazybob/talks/renaissance/";

  public static void main(String[] args) {
    Template template = new Oscon();

    Deck deck = new Deck()
        .title("On the cusp of a Java renaissance")
        .subtitle("Where we've been and where we're going")
        .author("Bob Lee")
        .company("Square Inc.");

    deck.add(new Slide().add(picture("definition.png").center()));
    // Presumably, interest waned at some point.

    deck.add(new Slide("When did interest wane?").add(bullets()
        .$("Bullet 1")
        .$("Bullet 2")
        .$("Bullet 3")
    ));

    deck.writePdf(template, "out/renaissance.pdf", true);
  }

  private static Picture picture(String name) {
    return Picture.parseFile("images/renaissance/" + name);
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

  private static void revealBullets(Deck deck, String title,
                                    String... bullets) {
    for (int max = 0; max <= bullets.length; max++) {
      Bullets b = new Bullets();
      for (int i = 0; i < max; i++) {
        b.add(bullets[i]);
      }
      deck.add(new Slide(title).add(b));
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
