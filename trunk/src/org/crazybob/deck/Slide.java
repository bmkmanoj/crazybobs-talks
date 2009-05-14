package org.crazybob.deck;

/**
 *
 */
public abstract class Slide {

  String title;
  String subtitle;

  public Slide title(String title) {
    this.title = title;
    return this;
  }

  public Slide subtitle(String title) {
    this.subtitle = title;
    return this;
  }

  abstract void addTo(Deck deck);
}
