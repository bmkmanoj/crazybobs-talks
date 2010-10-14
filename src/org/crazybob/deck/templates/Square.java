package org.crazybob.deck.templates;

import org.crazybob.deck.Box;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Font;
import org.crazybob.deck.Margins;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Spacer;
import org.crazybob.deck.Template;
import org.crazybob.deck.Text;

import java.awt.Color;

import static java.awt.Color.BLACK;

/**
 * Square, Inc. template.
 */
public class Square implements Template {

  public static final Color BLUE = new Color(31, 84, 123);
  public static final Color LIGHT_BLUE = new Color(81, 125, 156);
  public static final Color GRAY = new Color(0x99, 0x99, 0x99);
  public static final Color DARK_GRAY = new Color(0x50, 0x50, 0x50);

  public Picture defaultBackground() {
    return null;
  }

  public Slide titleSlide(Deck deck) {
    Slide titleSlide = new Slide();

    Box titleBox = new Box(600, 300, 50, 50);

    Font titleFont = new Font(Font.Face.HELVETICA, 42, Font.Style.NORMAL,
        DARK_GRAY);
    titleBox.add(new Text(deck.title()).font(titleFont));
    titleBox.add(new Text(deck.subtitle()).font(titleFont));

    titleBox.add(Spacer.vertical(20));

    titleBox.add(new Text(deck.author()).font(
        new Font(Font.Face.HELVETICA, 28, Font.Style.NORMAL, GRAY)));
    titleBox.add(new Text(deck.company()).font(
        new Font(Font.Face.HELVETICA, 22, Font.Style.NORMAL, GRAY)));

    titleSlide.add(titleBox);

    Picture squareLogo = Picture.parseFile(
        "images/square/square-3d-logo-400x400.png");
    squareLogo.position(150, 250);
    titleSlide.add(squareLogo);

    return titleSlide;
  }

  public Font titleFont() {
    return new Font(Font.Face.HELVETICA, 36, Font.Style.NORMAL, DARK_GRAY);
  }

  public Margins titleMargins() {
    return new Margins(50, 50, 50, 0);
  }

  public Margins contentMargins() {
    // 1180x710 (aspect=1.66)
    return new Margins(50, 175, 50, 50);
  }

  public Font defaultFont() {
    return new Font(Font.Face.HELVETICA, 28, Font.Style.NORMAL, BLACK);
  }

  public Font bulletFont(int depth) {
    return new Font(Font.Face.HELVETICA, bulletFontSize(depth),
        Font.Style.NORMAL, BLACK);
  }

  public Text bullet(int depth) {
    return new Text(depth == 0 ? "> " : "� ").font(
        new Font(Font.Face.HELVETICA, bulletFontSize(depth), Font.Style.NORMAL,
            DARK_GRAY));
  }

  private int bulletFontSize(int depth) {
    switch (depth) {
      case 0: return 30;
      case 1: return 28;
      case 2: return 24;
      default: return 18;
    }
  }

  public Font codeFont() {
    return new Font(Font.Face.COURIER, 18, Font.Style.BOLD, BLACK, 21);
  }

  public Font highlightedCodeFont() {
    return new Font(Font.Face.COURIER, 18, Font.Style.BOLD, Color.BLUE, 21);
  }

  public Font badCodeFont() {
    return new Font(Font.Face.COURIER, 18, Font.Style.BOLD, Color.RED, 21);
  }
}
