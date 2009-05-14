package org.crazybob.deck;

/**
 *
 */
public interface Template {

  Slide titleSlide(Deck deck);
  Picture defaultBackground();
  Font defaultFont();
  Margins defaultMargins();
  Font titleFont();
  Font subtitleFont();
  Font bulletFont(int depth);
  Text bullet(int depth);
  int titleSpacing();
}
