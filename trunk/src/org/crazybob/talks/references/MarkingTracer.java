package org.crazybob.talks.references;

import org.crazybob.deck.dot.DiGraph;
import org.crazybob.deck.dot.Node;
import org.crazybob.deck.dot.Link;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Deck;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.HashSet;

public class MarkingTracer {

  final Random random;
  final Deck deck;
  final DiGraph heap = new DiGraph();
  final Root root = new Root();
  final List<Normal> normalList = new ArrayList<Normal>();
  final Set<Normal> normals = new LinkedHashSet<Normal>();
  final Set<Soft> softs = new LinkedHashSet<Soft>();
  final Set<Weak> weaks = new LinkedHashSet<Weak>();
  final Set<Phantom> phantoms = new LinkedHashSet<Phantom>();
  final List<Obj> allObjects = new ArrayList<Obj>();
  final Set<Node> marks = new HashSet<Node>();

  private static final String GRAY = "#cccccc";
  private static final String BLACK = "black";
  private static final String WHITE = "#ffffff";

  abstract class Obj {
    final Node node;
    final List<Obj> referents = new ArrayList<Obj>();

    Obj(String label) {
      this.node = heap.newNode(label);
      dark();
    }

    abstract void light();
    abstract void dark();

    void hide() {
      node.hide();
    }

    void reference(Obj other) {
      referents.add(other);
      node.pointTo(other.node);
    }
  }

  class Root extends Obj {
    Root() {
      super("root");
    }
    void light() {
      node.fillColor(GRAY).lineColor(GRAY).fontColor(WHITE);
    }
    void dark() {
      node.fillColor(BLACK).fontColor(WHITE).lineColor(BLACK);
    }
  }

  class Normal extends Obj {
    Normal() {
      super("object");
    }
    void light() {
      node.fillColor(WHITE).fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#999999").fontColor(WHITE).lineColor(BLACK);
    }
  }

  class Soft extends Obj {
    Soft() {
      super("soft");
    }
    void light() {
      node.fillColor("#eeffee").fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#009900").fontColor(WHITE).lineColor(BLACK);
    }
  }

  class Weak extends Obj {
    Weak() {
      super("weak");
    }
    void light() {
      node.fillColor("#eeeeff").fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#000099").fontColor(WHITE).lineColor(BLACK);
    }
  }

  class Phantom extends Obj {
    Phantom() {
      super("phantom");
    }
    void light() {
      node.fillColor("#ffeeee").fontColor(GRAY).lineColor(GRAY);
    }
    void dark() {
      node.fillColor("#990000").fontColor(WHITE).lineColor(BLACK);
    }
  }

  public MarkingTracer(Deck deck, int seed) {
    this.deck = deck;
    this.random = new Random(seed);

    for (int i = 0; i < 50; i++) {
      Normal n = new Normal();
      normalList.add(n);
      normals.add(n);
      allObjects.add(n);
    }

    for (int i = 0; i < 8; i++) {
      softs.add(new Soft());
      weaks.add(new Weak());
      phantoms.add(new Phantom());
    }
    allObjects.addAll(softs);
    allObjects.addAll(weaks);
    allObjects.addAll(phantoms);

    for (int i = 0; i < 5; i++)
      randomlyLink(root);
    for (int i = 0; i < 80; i++)
      randomlyLink(normalList.get(random.nextInt(normalList.size())));

    List<Obj> refs = new ArrayList<Obj>();
    refs.addAll(softs);
    refs.addAll(weaks);
    refs.addAll(phantoms);
    for (Obj o : refs)
      o.reference(normalList.get(random.nextInt(normalList.size())));

    // Add root last so we don't reference it.
    allObjects.add(root);
  }

  public void addSlides() {
    addSlide("Let's mark and sweep a heap!");

    for (Obj o : allObjects) {
      o.light();
      for (Link link : o.node.links()) {
        link.color(GRAY);
      }
    }

    addSlide("1. To start, nothing is marked.");
  }

  private void randomlyLink(Obj source) {
    Obj other;
    do {
      int destIndex = random.nextInt(allObjects.size());
      other = allObjects.get(destIndex);
    } while (other == source);
    source.reference(other);
  }

  private void addSlide(String title) {
    this.deck.add(new Slide(title).add(
        Picture.parseDot(heap.toString()).fill().center()));
  }

  public void traceStrongReferences() {
//    root.fillColor(BLACK).lineColor(BLACK);
//
//    addSlide("2. Start at a root.");
//    for (Link link : root.links()) {
//      link.color(BLACK);
//      followStrongReferencesFrom(link.target());
//    }
//
//    followStrongReferencesFrom(root);
  }

  public void traceSoftReferences() {
//    addSlide("4. Optionally clear soft references.");
//
//    for (Node soft : softs) {
//      if (marks.contains(soft)) {
//        followSoftReferencesFrom(soft);
//      }
//    }
  }

//  int counter = 0;
//
//  private void followStrongReferencesFrom(Node node) {
//    boolean added = marks.add(node);
//
//    boolean ref = !normals.contains(node);
//    if (!ref) {
//      node.fillColor(MARKED_OBJECT).lineColor(BLACK).fontColor(WHITE);
//    } else if (softs.contains(node)) {
//      node.fillColor(MARKED_SOFT).lineColor(BLACK).fontColor(WHITE);
//    } else if (weaks.contains(node)) {
//      node.fillColor(MARKED_WEAK).lineColor(BLACK).fontColor(WHITE);
//    } else if (phantoms.contains(node)) {
//      node.fillColor(MARKED_PHANTOM).lineColor(BLACK).fontColor(WHITE);
//    }
//
//    counter++;
//    if (counter == 7) {
//      counter = 0;
//      addSlide("3. Trace & mark strongly-referenced objects.");
//    }
//
//    if (!ref && added) {
//      for (Link link : node.links()) {
//        link.color(BLACK);
//        followStrongReferencesFrom(link.target());
//      }
//    }
//  }
}
