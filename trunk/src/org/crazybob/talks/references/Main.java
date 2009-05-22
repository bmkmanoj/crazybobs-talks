package org.crazybob.talks.references;

import org.crazybob.deck.Deck;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Code;
import org.crazybob.deck.Text;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.dot.DiGraph;
import org.crazybob.deck.dot.Node;
import org.crazybob.deck.dot.Link;
import org.crazybob.deck.templates.JavaOne09;

public class Main {

  static final String PATH = "src/org/crazybob/talks/references/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("The Ghost in the Virtual Machine")
        .subtitle("A Reference to References")
        .author("Bob Lee")
        .company("Google Inc.");

    deck.add(new Slide("Goals").add(new Bullets()
        .add("Perform manual cleanup the right way.")
        .add("Take the mystery out of garbage collection.")
        .add("Become honorary VM sanitation engineers.")
    ));

    addHeapSlides(deck);

    deck.add(new Slide("Reachability").add(new Bullets()
        .add("An object is _reachable_ if a live thread can access it.")
        .add("Examples of heap roots:", new Bullets()
          .add("System classes (which have static fields)")
          .add("Thread stacks")
          .add("In-flight exceptions")
          .add("JNI global references")
          .add("The finalizer queue")
          .add("The interned String pool")
          .add("etc. (VM-dependent)")
        )
    ));

    highlightBullets(deck, "Dante's Heap - The Levels of Reachability",
        "Strong", "Soft", "Weak", "Finalizer", "Phantom, JNI weak",
        "Unreachable");

    MarkAndSweep tracer = new MarkAndSweep(deck, 12);
    tracer.addSlides();

    deck.add(new Slide("Two options for freeing native resources")
          .add(new Bullets()
        .add("Use a finalizer.", new Bullets()
            .add("You must defend against subsequent use!"))
        .add("Or use a phantom reference.")
    ));

    deck.writePdf(new JavaOne09(), "out/references.pdf", true);
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

  private static void addHeapSlides(Deck deck) {
    DiGraph heap = new DiGraph();

    Node root1 = heap.newNode("root").fillColor("black").fontColor("white");
    Node root2 = heap.newNode("root").fillColor("black").fontColor("white");

    Node a = heap.newNode("a").fillColor("white");
    Node b = heap.newNode("b").fillColor("white");
    Node c = heap.newNode("c").fillColor("white");
    Node d = heap.newNode("d").fillColor("white");
    Node e = heap.newNode("e").fillColor("white");

    Link r1a = root1.pointTo(a);
    Link r1d = root1.pointTo(d);

    Link r2b = root2.pointTo(b);

    Link ab = a.pointTo(b);
    Link ac = a.pointTo(c);
    Link bc = b.pointTo(c);

    Link de = d.pointTo(e);
    Link eb = e.pointTo(b);

    deck.add(new Slide("How does garbage collection work?").add(
        Picture.parseDot(heap.toString()).fill().center()));

    r1d.hide();

    deck.add(new Slide("If the reference to D goes away...").add(
        Picture.parseDot(heap.toString()).fill().center()));

    d.fillColor("lightgrey").lineColor("lightgrey");
    e.fillColor("lightgrey").lineColor("lightgrey");    
    de.color("lightgrey");
    eb.color("lightgrey");    

    deck.add(new Slide("We can no longer reach D or E.").add(
        Picture.parseDot(heap.toString()).fill().center()));

    d.hide();
    e.hide();

    deck.add(new Slide("So the collector reclaims them.").add(
        Picture.parseDot(heap.toString()).fill().center()));
  }

  private static Code parseCode(String path) {
    return Code.parseFile(PATH + path);
  }
}