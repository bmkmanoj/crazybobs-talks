package org.crazybob.talks.references;

import org.crazybob.deck.Deck;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.Dot;
import org.crazybob.deck.templates.JavaOne09;

public class Main {

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("The Ghost in the Virtual Machine")
        .subtitle("A Reference to References")
        .author("Bob Lee")
        .company("Google Inc.");

    deck.add(new Slide()
        .title("Slide #1")
        .subtitle("Subtitle")
        .add(new Bullets()
            .add("Top level bullet", new Bullets()
                .add("Second level bullet"))));

    deck.add(new Slide()
      .add(Dot.parse("digraph g {\n"
        + "    foo -> bar\n"
        + "    bar -> tee\n"
        + "    tee -> foo\n"
        + "}")));

    deck.writePdf(new JavaOne09(), "out/references.pdf", true);
  }
}