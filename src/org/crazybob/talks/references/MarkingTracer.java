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

  final Random random = new Random(16);
  final Deck deck;
  final DiGraph heap = new DiGraph();
  final List<Node> allObjects = new ArrayList<Node>();
  final Set<Node> objectSet = new HashSet<Node>();
  final Set<Node> softs = new LinkedHashSet<Node>();
  final Set<Node> weaks = new LinkedHashSet<Node>();
  final Set<Node> phantoms = new LinkedHashSet<Node>();
  private static final String GRAY = "#cccccc";
  final Node root = heap.newNode("root").fillColor(BLACK).fontColor("white")
      .lineColor(BLACK);
  final Set<Node> marks = new HashSet<Node>();
  private static final String BLACK = "black";
  private static final String WHITE = "#ffffff";
  private static final String MARKED_OBJECT = "#999999";
  private static final String MARKED_SOFT = "#009900";
  private static final String MARKED_WEAK = "#000099";
  private static final String MARKED_PHANTOM = "#990000";
  private static final String UNMARKED_SOFT = "#eeffee";
  private static final String UNMARKED_WEAK = "#eeeeff";
  private static final String UNMARKED_PHANTOM = "#ffeeee";

  public MarkingTracer(Deck deck) {
    this.deck = deck;

    allObjects.add(root);

    List<Node> objects = new ArrayList<Node>();
    for (int i = 0; i < 35; i++) {
      Node o = heap.newNode("object").fillColor(MARKED_OBJECT).fontColor(WHITE);
      objects.add(o);
      objectSet.add(o);
    }
    allObjects.addAll(objects);

    for (int i = 0; i < 5; i++) {
      softs.add(heap.newNode("soft").fillColor(MARKED_SOFT).fontColor(WHITE));
    }
    allObjects.addAll(softs);
    for (int i = 0; i < 5; i++) {
      weaks.add(heap.newNode("weak").fillColor(MARKED_WEAK).fontColor(WHITE));
    }
    allObjects.addAll(weaks);
    for (int i = 0; i < 5; i++) {
      phantoms.add(heap.newNode("phantom").fillColor(MARKED_PHANTOM)
          .fontColor(WHITE));
    }
    allObjects.addAll(phantoms);

    for (int i = 0; i < 5; i++) {
      randomlyLink(root);
    }
    for (int i = 0; i < 60; i++) {
      int sourceIndex = random.nextInt(objects.size());
      Node source = objects.get(sourceIndex);
      randomlyLink(source);
    }
    for (Node soft : softs) {
      randomlyLink(soft);
    }
    for (Node weak : weaks) {
      randomlyLink(weak);
    }
    for (Node phantom : phantoms) {
      randomlyLink(phantom);
    }

    addSlide("A mark-and-sweep collector cycle");

    root.fillColor(GRAY).lineColor(GRAY).fontColor(WHITE);
    for (Node o : objects) o.fillColor(WHITE).fontColor(GRAY).lineColor(GRAY);
    for (Node soft : softs) soft.fillColor(UNMARKED_SOFT).fontColor(GRAY)
        .lineColor(GRAY);
    for (Node weak : weaks) weak.fillColor(UNMARKED_WEAK).fontColor(GRAY)
        .lineColor(GRAY);
    for (Node phantom : phantoms) phantom.fillColor(UNMARKED_PHANTOM)
        .fontColor(GRAY).lineColor(GRAY);
    for (Node o : allObjects) {
      for (Link link : o.links()) link.color(GRAY);
    }

    addSlide("1. Nothing is marked, to start.");
  }

  private void addSlide(String title) {
    this.deck.add(new Slide(title).add(
        Picture.parseDot(heap.toString()).fill().center()));
  }

  public void traceStrongReferences() {
    root.fillColor(BLACK).lineColor(BLACK);

    addSlide("2. Start at a root.");
    for (Link link : root.links()) {
      link.color(BLACK);
      followStrongReferencesFrom(link.target());
    }

    followStrongReferencesFrom(root);
  }

  public void traceSoftReferences() {
    addSlide("4. Optionally clear soft references.");

    for (Node soft : softs) {
      if (marks.contains(soft)) {
        followSoftReferencesFrom(soft);
      }
    }
  }

  private void followSoftReferencesFrom(Node node) {
    boolean added = marks.add(node);

    if (objectSet.contains(node)) {
      node.fillColor(MARKED_OBJECT).lineColor(BLACK).fontColor(WHITE);
    } else if (softs.contains(node)) {
      node.fillColor(MARKED_SOFT).lineColor(BLACK).fontColor(WHITE);
    } else if (weaks.contains(node)) {
      node.fillColor(MARKED_WEAK).lineColor(BLACK).fontColor(WHITE);
    } else if (phantoms.contains(node)) {
      node.fillColor(MARKED_PHANTOM).lineColor(BLACK).fontColor(WHITE);
    }

    addSlide("3. Trace & mark softly-referenced objects.");

    if (added && (objectSet.contains(node) || softs.contains(node))) {
      for (Link link : node.links()) {
        link.color(BLACK);
        followSoftReferencesFrom(link.target());
      }
    }
  }

  int counter = 0;

  private void followStrongReferencesFrom(Node node) {
    boolean added = marks.add(node);

    boolean ref = !objectSet.contains(node);
    if (!ref) {
      node.fillColor(MARKED_OBJECT).lineColor(BLACK).fontColor(WHITE);
    } else if (softs.contains(node)) {
      node.fillColor(MARKED_SOFT).lineColor(BLACK).fontColor(WHITE);
    } else if (weaks.contains(node)) {
      node.fillColor(MARKED_WEAK).lineColor(BLACK).fontColor(WHITE);
    } else if (phantoms.contains(node)) {
      node.fillColor(MARKED_PHANTOM).lineColor(BLACK).fontColor(WHITE);
    }

    counter++;
    if (counter == 7) {
      counter = 0;
      addSlide("3. Trace & mark strongly-referenced objects.");
    }

    if (!ref && added) {
      for (Link link : node.links()) {
        link.color(BLACK);
        followStrongReferencesFrom(link.target());
      }
    }
  }

  private void randomlyLink(Node source) {
    Node other;
    do {
      int destIndex = random.nextInt(allObjects.size());
      other = allObjects.get(destIndex);
    } while (other == source);
    source.pointTo(other);
  }
}
