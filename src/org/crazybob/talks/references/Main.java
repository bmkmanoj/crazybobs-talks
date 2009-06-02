package org.crazybob.talks.references;

import org.crazybob.deck.Deck;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Code;
import org.crazybob.deck.Text;
import org.crazybob.deck.Bullets;
import org.crazybob.deck.Spacer;
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

    // TODO: trash truck picture
    deck.add(new Slide("Goals").add(bullets()
        .$("Take the mystery out of garbage collection.")
        .$("Perform manual cleanup the Right way.")
        .$("Become honorary VM sanitation engineers.")
    ));

    addHeapSlides(deck);

    deck.add(new Slide("The GC can't do everything.").add(bullets()
        .$("Some things require manual cleanup.", bullets()
            .$("Listeners")
            .$("File descriptors")
            .$("Native memory")
            .$("External state (|IdentityHashMap|)")
        )
        .$("Tools at your disposal:", bullets()
            .$("|finally|")
            .$("Overriding |Object.finalize()|")
            .$("Reference queues")
        )
    ));

    deck.add(new Slide("Try |finally| first.").add(bullets()
        .$("Reasons to not use |finally|:", bullets()
            .$("More work for programmers")
            .$("More error prone")
            .$("Cleanup happens in main thread")
        )
        .$("ARM will help.")
    ));

    deck.add(new Slide("What is a finalizer?").add(
        Code.parseFile(PATH + "eg1/Foo.java")
    ));

    deck.add(new Slide("Finalizers are seductively simple, but...").add(bullets()
        .$("They're not guaranteed to run, especially not timely.")
        .$("Avoid |System.runFinalizersOnExit()| and |runFinalization()|.")
        .$("Undefined threading model, can run concurrently!")
        .$("You must call |super.finalize()|.")
        .$("Exceptions are ignored (per spec).")
        .$("You can resurrect references.")
        .$("Keeps objects alive longer.")
        .$("Can make allocation/reclamation 430X slower (Bloch, Effective Java)")
    ));

    deck.add(new Slide("An external resource").add(
        Code.parseFile(PATH + "nm/NativeMemory.java")));

    Slide s = new Slide("Let's play War!").add(
        new Text("|SegfaultFactory| can cause a segfault if its finalizer"
            + " executes after |NativeResource|'s."),
        Spacer.vertical(40),
        Code.parseFile(PATH + "nm/SegfaultFactory.java")
    );
    s.add(Picture.parseFile("images/references/war.jpg")
        .position(950, 350));
    deck.add(s);

    deck.add(new Slide("Use protection.").add(
        Code.parseFile(PATH + "snm/NativeMemory.java")));

    deck.add(new Slide("Finalizers are good for one thing.").add(
        new Text("Logging warnings")));

    deck.add(new Slide("The alternative").add(
        new Text("An API-based approach:"),
        Spacer.vertical(30),
        Code.parseFile(PATH + "WeakReference.java")
    ));

    deck.add(new Slide("What is a finalizer?").add(
        Code.parseFile(PATH + "Button.java")
    ));

    deck.add(new Slide("The alternative").add(
        Code.parseFile(PATH + "ReferenceQueue.java")
    ));
    
    deck.add(new Slide("Reachability").add(bullets()
        .$("An object is _reachable_ if a live thread can access it.")
        .$("Examples of heap roots:", bullets()
          .$("System classes (which have static fields)")
          .$("Thread stacks")
          .$("In-flight exceptions")
          .$("JNI global references")
          .$("The finalizer queue")
          .$("The interned String pool")
          .$("etc. (VM-dependent)")
        )
    ));

    deck.add(new Slide("Making maps").add(
        Code.parseFile(PATH + "BytecodeCache.java")
    ));

    highlightBullets(deck, "Dante's Heap - The Levels of Reachability",
        "Strong", "Soft", "Weak", "Finalizer", "Phantom, JNI weak",
        "Unreachable");

    MarkAndSweep tracer = new MarkAndSweep(deck, 12);
    tracer.addSlides();

    deck.add(new Slide("Weak references aren't for caching!").add(bullets()
        .$("Many collectors will reclaim weak refs immediately.")
        .$("Use soft reference for caching, as intended:"),
        Spacer.vertical(50),
        new Text("_ÒVirtual machine implementations are encouraged"
            + " to bias against clearing recently-created or recently-used"
            + " soft references.Ó_"),
        Spacer.vertical(30),
        new Text("- The |SoftReference| documentation").scale(75)
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