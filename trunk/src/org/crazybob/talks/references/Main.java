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

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main {

  static final String PATH = "src/org/crazybob/talks/references/";

  public static void main(String[] args) {
    Deck deck = new Deck()
        .title("The Ghost in the Virtual Machine")
        .subtitle("A Reference to References")
        .author("Bob Lee")
        .company("Google Inc.");

    addHeapSlides(deck);

    deck.add(new Slide("Object Reachability").add(new Bullets()
        .add("An object is _reachable_ if a live thread can access it.")
        .add("Examples of heap roots:", new Bullets()
          .add("System classes (which have static state)")
          .add("Thread stacks")
          .add("In-flight exceptions")
          .add("JNI global references")
          .add("The finalizer queue")
          .add("Interned strings")
          .add("etc. (VM-dependent)")
        )
    ));

    deck.add(new Slide("In the beginning, there was the finalizer...").add(
        new Text(" "),
        parseCode("eg1/Foo.java")
    ));

    deck.add(new Slide("In the beginning, there was the finalizer...").add(
        new Text(" "),
        parseCode("eg1/Foo.java")
    ));

    DiGraph heap = new DiGraph();
    List<Node> nodes = new ArrayList<Node>();
    Node root = heap.newNode("root").fillColor("black").fontColor("white");
    nodes.add(root);
    for (int i = 0; i < 20; i++) {
      nodes.add(heap.newNode(String.valueOf((char) ('a' + i))));
    }
    Random random = new Random(2);
    for (int i = 0; i < 20; i++) {
      int sourceIndex = random.nextInt(nodes.size());
      int destIndex = random.nextInt(nodes.size());
      nodes.get(sourceIndex).pointTo(nodes.get(destIndex));
    }
    for (int i = 0; i < 20; i++) {
      int sourceIndex = random.nextInt(nodes.size());
      int destIndex = random.nextInt(nodes.size());
      nodes.get(sourceIndex).pointTo(nodes.get(destIndex)).color("lightgrey");
    }

    deck.add(new Slide("Hello, World!").add(
        Picture.parseDot(heap.toString()).fill().center()));

    deck.writePdf(new JavaOne09(), "out/references.pdf", true);
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

    deck.add(new Slide("Garbage Collection").add(
        Picture.parseDot(heap.toString()).fill().center()));

    r1d.hide();

    deck.add(new Slide("Garbage Collection").add(
        Picture.parseDot(heap.toString()).fill().center()));

    d.fillColor("lightgrey").lineColor("lightgrey");
    e.fillColor("lightgrey").lineColor("lightgrey");    
    de.color("lightgrey");
    eb.color("lightgrey");    

    deck.add(new Slide("Garbage Collection").add(
        Picture.parseDot(heap.toString()).fill().center()));

    d.hide();
    e.hide();

    deck.add(new Slide("Garbage Collection").add(
        Picture.parseDot(heap.toString()).fill().center()));
  }

  private static Code parseCode(String path) {
    return Code.parseFile(PATH + path);
  }
}