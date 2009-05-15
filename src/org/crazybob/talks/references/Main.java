package org.crazybob.talks.references;

import org.crazybob.deck.Deck;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.Picture;
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
                .add("Second level bullet", new Bullets()
                    .add("Level 3")))
            .add("Top level bullet", new Bullets()
            .add("Second level bullet")
            .add("Second level bullet", new Bullets()
                .add("Level 3"))))
    );

    Picture dot = Picture.parseDot("digraph g {\n"
        + "    foo -> bar\n"
        + "    bar -> tee\n"
        + "    tee -> foo\n"
        + "}").height(900).center();

    deck.add(new Slide()
        .title("Example dot diagram")
        .add(dot));

    deck.writePdf(new JavaOne09(), "out/references.pdf", true);
  }
}