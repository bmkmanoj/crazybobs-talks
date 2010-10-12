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
    introduction(template, deck);
    squarewave(template, deck);
    queuefile(template, deck);
    shakeSensor(template, deck);
    retrofit(template, deck);

    deck.writePdf(template, "out/androidsquared.pdf", true);
  }

  private static void introduction(Template template, Deck deck) {
    // TODO Bob's section
  }

  private static void squarewave(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "Swipe Decoding"));

    deck.add(new Slide("Square Reader")
        .add(fillRight(picture("reader.png"),
            362, 393))
        .add(new Text("Acts as a microphone")));

    deck.add(new Slide("Ideal Waveform")
        .add(picture("ideal-waveform.png").center()));

    deck.add(new Slide()
        .background(picture("dr-evil-epic-4g.png")));

    deck.add(new Slide("Actual Swipe Recording")
        .add(picture("swipe-1.png").center()));

    deck.add(new Slide("Swipe Start")
        .add(picture("swipe-2.png").center()));

    deck.add(new Slide("Swipe End")
        .add(picture("swipe-3.png").center()));

    revealBullets(deck, "Challenges",
        "Swipe speed",
        "Device sample rate",
        "Audio correction");

    steps(deck,
        "Swipe Detection", "swipe-detection.png",
        "Denoising", "denoising.png",
        "Peak Detection", "peak-detection.png",
        "Window Peak Removal", "window-peak-removal.png",
        "Consecutive Peak Removal", "consecutive-peak-removal.png",
        "Decoding", "decoding.png"
    );

    deck.add(new Slide("Workbench")
        .add(picture("workbench.png").center()));

    revealBullets(deck, "95% Accuracy",
        "Record hundreds of swipes",
        "Decode all, record results",
        "Adjust parameters",
        "Repeat",
        "After finding best options...",
        "Repeat entire process on failed swipes");
  }

  private static void queuefile(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "QueueFile"));
    // TODO Bob's section
  }

  private static void shakeSensor(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "Shake Detection"));
    // TODO - Eric's section
  }

  private static void retrofit(Template template, Deck deck) {
    deck.add(sectionTitleSlide(template, "Retrofit"));

    // TODO - Bob's section

    // Introduce the Retrofit project
  }

  private static Picture picture(String name) {
    return Picture.parseFile("images/androidsquared/" + name);
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
