package org.crazybob.talks.foj;

import org.crazybob.deck.Bullets;
import org.crazybob.deck.Code;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Font;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Spacer;
import org.crazybob.deck.Text;
import org.crazybob.deck.templates.Plain;

/**
 * The Future of Java
 */
public class FutureOfJava {

  static final String PATH = "src/org/crazybob/talks/foj/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("The Future of Java")
        .subtitle("")
        .author("Bob Lee")
        .company("Google Inc.");

    /*
     * Some speakers like to picture the audience naked... I take a different
     * approach.
     */
    deck.add(new Slide("Who is Bob Lee?").add(bullets()
        .$("Google engineer")
        .$("Android core library lead")
        .$("Guice creator")
        .$("JSR-330 lead")
        .$("EC rep")
        .$("St. Louisan")
        .$("Speedo model")
    ).add(speedo()));

    /*
     * How I picked these:
     *   1) Projects I worked directly on.
     *   2) Topics of possible general interest, i.e. to non-Java programmers.
     */
    deck.add(new Slide("Let's talk about...").add(bullets()
        .$("Project Coin")
        .$("JSR-330: Dependency Injection for Java")
        .$("MapMaker")
    ));

    deck.add(new Slide("Project Coin").add(
        fillRight(Picture.parseFile("images/misc/coins.jpg"), 1400, 2048)
    ).add(new Text("Small language *change*s")));

    deck.add(new Slide("Currently accepted proposals").add(bullets()
        .$("Strings in switch")
        .$("Automatic Resource Management (ARM)")
        .$("Improved generic type inference for constructors")
        .$("Simplified varags method invocation")
        .$("Collection literals and access syntax")
        .$("Better integral literals")
        .$("JSR-292 (Invokedynamic) support")
    ));

    deck.add(new Slide("Currently accepted proposals").add(bullets()
        .$("Strings in switch")
        .$("*Automatic Resource Management (ARM)*")
        .$("Improved generic type inference for constructors")
        .$("*Simplified varags method invocation*")
        .$("Collection literals and access syntax")
        .$("Better integral literals")
        .$("JSR-292 (Invokedynamic) support")
    ));

    deck.add(new Slide("ARM").add(bullets()
        .$("Automatic Resource Management")
        .$("Helps dispose of resources")
        .$("Proposed by Josh Bloch")
    ));

    deck.add(new Slide("*Example:* Parsing a file header")
        .add(Code.parseFile(PATH + "arm/plain/HeaderParser.java")
    ));

    deck.add(new Slide("*Example:* Parsing a file header")
        .add(Code.parseFile(PATH + "arm/plain/HeaderParser.java"))
        .add(Spacer.vertical(30),
          new Text("*See the problem?*")
    ));

    deck.add(new Slide("If we don't reach |close()|, we leak. ")
        .add(Code.parseFile(PATH + "arm/leaky/HeaderParser.java")));

    deck.add(new Slide("|finally| ensures |close()| is always called.")
        .add(Code.parseFile(PATH + "arm/notleaky/HeaderParser.java")));

    deck.add(new Slide("But what happens when |close()| throws?")
        .add(Code.parseFile(PATH + "arm/wrongexception/HeaderParser.java")));

    deck.add(new Slide("We _could_ ignore the exception from |close()|.")
        .add(Code.parseFile(PATH + "arm/ignoreclose/HeaderParser.java")));

    deck.add(new Slide("But it's better to throw the right exception.")
        .add(Code.parseFile(PATH + "arm/correct/HeaderParser.java")));

    String armExample = "Equivalent code, using an ARM block.";
    deck.add(new Slide(armExample )
        .add(Code.parseFile(PATH + "arm/witharm/HeaderParser.j")));

    deck.add(new Slide(armExample)
        .add(Code.parseFile(PATH + "arm/witharm/HeaderParser.j"))
        .add(Spacer.vertical(30),
          new Text("*Note:* Techincally, we could still leak.")
    ));

    deck.add(new Slide(armExample)
        .add(Code.parseFile(PATH + "arm/witharm2/HeaderParser.j")));

    deck.add(new Slide("Why ARM is important").add(bullets()
        .$("Out of 110 uses of |close()| in the JDK...", bullets()
          .$("74 (2/3) leaked")
          .$("In other words, 2/3 of all uses were broken"))
        .$("")
    ));

    deck.writePdf(new Plain(), "out/foj.pdf", true);
  }

  private static Picture speedo() {
    return fillRight(Picture.parseFile("images/misc/speedo.jpg"), 683, 1024);
  }


  private static Picture fillRight(Picture p, int w, int h) {
    p.height(Deck.HEIGHT);
    int newWidth = Deck.HEIGHT * w / h;
    p.position(Deck.WIDTH - newWidth, 0);
    return p;
  }

  private static void highlightBullets(Deck deck, String title,
      String... bullets) {
    highlightBullet(deck, title, null, bullets);
    for (String current : bullets) {
      highlightBullet(deck, title, current, bullets);
    }
  }

  @SuppressWarnings("StringEquality")
  private static void highlightBullet(Deck deck, String title, String current,
      String... bullets) {
    Bullets b = new Bullets();
    for (String s : bullets) {
      b.add(current == s ? "*" + s + "*" : s);
    }
    deck.add(new Slide(title).add(b));
  }

  private static Code parseCode(String path) {
    return Code.parseFile(PATH + path);
  }

  /** Starts bullets. */
  private static $Bullets bullets() {
    return new $Bullets();
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
