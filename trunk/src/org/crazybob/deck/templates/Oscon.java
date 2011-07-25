package org.crazybob.deck.templates;

import java.awt.Color;
import org.crazybob.deck.Box;
import org.crazybob.deck.Deck;
import org.crazybob.deck.Font;
import org.crazybob.deck.Margins;
import org.crazybob.deck.Picture;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Spacer;
import org.crazybob.deck.Template;
import org.crazybob.deck.Text;

import static java.awt.Color.BLACK;

/**
 * OSCON template.
 */
public class Oscon implements Template {

  public static final Color BLUE = new Color(31, 84, 123);
  public static final Color LIGHT_BLUE = new Color(81, 125, 156);
  public static final Color GRAY = new Color(0x99, 0x99, 0x99);
  public static final Color DARK_GRAY = new Color(0x50, 0x50, 0x50);

  public Picture defaultBackground() {
    return Picture.parseFile("images/oscon/background.png");
  }

  public Slide titleSlide(Deck deck) {
    Slide titleSlide = new Slide()
        .background(Picture.parseFile("images/oscon/title.png"));

    Box titleBox = new Box(690, 490, 36, 36);

    Font titleFont = new Font(Font.Face.HELVETICA, 28, Font.Style.BOLD, BLACK);
    Font subtitleFont = new Font(Font.Face.HELVETICA, 24, Font.Style.BOLD, BLACK, 30);
    titleBox.add(new Text(deck.title()).font(titleFont));
    titleBox.add(new Text(deck.subtitle()).font(subtitleFont));

    titleBox.add(Spacer.vertical(20));

    titleBox.add(new Text(deck.author()).font(
        new Font(Font.Face.HELVETICA, 22, Font.Style.NORMAL, GRAY)));
    titleBox.add(new Text(deck.company()).font(
        new Font(Font.Face.HELVETICA, 18, Font.Style.NORMAL, GRAY, 24)));

    titleSlide.add(titleBox);

    return titleSlide;
  }

  public Font titleFont() {
    return new Font(Font.Face.HELVETICA, 36, Font.Style.BOLD, BLACK);
  }

  public Margins titleMargins() {
    return new Margins(75, 30, 50, 300);
  }

  public Margins contentMargins() {
    return new Margins(75, 175, 50, 120);
  }

  public Font defaultFont() {
    return new Font(Font.Face.HELVETICA, 28, Font.Style.NORMAL, BLACK);
  }

  public Font bulletFont(int depth) {
    return new Font(Font.Face.HELVETICA, bulletFontSize(depth), Font.Style.NORMAL, BLACK);
  }

  public Text bullet(int depth) {
    return new Text("� ").font(
        new Font(Font.Face.HELVETICA, bulletFontSize(depth), Font.Style.NORMAL, DARK_GRAY));
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
