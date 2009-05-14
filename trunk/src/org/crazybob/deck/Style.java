package org.crazybob.deck;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Style {
  int width();
  int height();
  TitleSlide titleSlide();
  ContentSlide contentSlide();
  int dpi();

  public @interface TitleSlide {
    String background();
    String titleColor();
    String subtitleColor();
    String authorColor();
    String companyColor();
    int titleSize();
    int subtitleSize();
    int authorSize();
    int companySize();
    int titleX();
    int titleY();
  }

  public @interface ContentSlide {
    String background();
  }

  public @interface Margins {
    int top();
    int left();
    int right();
    int bottom();
  }

  public interface Provider {
    public Style style();
    public Slide titleSlide(Deck deck);
  }
}
