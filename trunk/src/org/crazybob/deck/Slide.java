package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.*;

import java.util.List;
import java.util.ArrayList;

/**
 *
 */
public class Slide {

  String title;
  String subtitle;
  final List<InlineElement> inlineElements = new ArrayList<InlineElement>();
  final List<PositionedElement> positionedElement
      = new ArrayList<PositionedElement>();
  Picture background;

  public Slide title(String title) {
    this.title = title;
    return this;
  }

  public Slide subtitle(String title) {
    this.subtitle = title;
    return this;
  }

  public Slide background(Picture background) {
    this.background = background;
    return this;
  }

  public Slide add(InlineElement element) {
    inlineElements.add(element);
    return this;
  }

  public Slide add(PositionedElement element) {
    positionedElement.add(element);
    return this;
  }

  void writePdf(Deck deck) throws DocumentException {
    Template template = deck.template;

    Image backgroundImage = background == null
        ? template.defaultBackground().image
        : background.image;
    backgroundImage.setAlignment(Image.UNDERLYING);
    deck.document.add(backgroundImage);

    // TODO: Decouple the title from the content.
    if (title != null || subtitle != null || !inlineElements.isEmpty()) {
      ColumnText column = new ColumnText(deck.writer.getDirectContent());
      template.defaultMargins().applyTo(column);

      if (title != null) {
        column.addElement(template.titleFont().newParagraph(title));
      }
      if (subtitle != null) {
        column.addElement(template.subtitleFont().newParagraph(subtitle));
      }

      if (title != null || subtitle != null) {
        Spacer.vertical(template.titleSpacing()).writePdf(deck, column);        
      }

      for (InlineElement element : inlineElements) {
        element.writePdf(deck, column);
      }
      if (column.go() == ColumnText.NO_MORE_COLUMN) {
        System.err.println("Warning: Overrun in slide #" + deck.slideIndex);
      }
    }

    for (PositionedElement element : positionedElement) {
      element.writePdf(deck);
    }
  }
}
