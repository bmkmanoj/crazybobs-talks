package org.crazybob.deck;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

import java.util.List;
import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 *
 */
public class Deck {

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 960;

  String title;
  String subtitle;
  String author;
  String company;
  final List<Slide> slides = new ArrayList<Slide>();

  public Deck add(Slide slide) {
    slides.add(slide);
    return this;
  }

  public String title() {
    return title;
  }

  public Deck title(String title) {
    this.title = title;
    return this;
  }

  public String subtitle() {
    return subtitle;
  }

  public Deck subtitle(String subtitle) {
    this.subtitle = subtitle;
    return this;
  }

  public String author() {
    return author;
  }

  public Deck author(String author) {
    this.author = author;
    return this;
  }

  public String company() {
    return company;
  }

  public Deck company(String company) {
    this.company = company;
    return this;
  }

  static int ptsToPixels(int pts) {
    return pts * WIDTH / 800;
  }

  Template template;
  Document document;
  PdfWriter writer;
  int slideIndex;

  /**
   *
   * @param path
   * @param open
   */
  public void writePdf(Template template, String path, boolean open) {
    try {
      this.template = template;
      document = new Document(new Rectangle(WIDTH, HEIGHT));
      document.setMargins(0, 0, 0, 0);
      try {
        writer = PdfWriter.getInstance(document,
            new FileOutputStream(path));
      } catch (DocumentException e) {
        throw new RuntimeException(e);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }

      document.open();

      slideIndex = 1;
      template.titleSlide(this).writePdf(this);
      for (Slide slide : slides) {
        document.newPage();
        ++slideIndex;
        slide.writePdf(this);
      }

      document.close();

      if (open) {
        try {
          Runtime.getRuntime().exec(
              new String[] { "open", "out/references.pdf" });
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    } catch (DocumentException e) {
      throw new RuntimeException("Error at slide #" + slideIndex, e);
    } finally {
      this.template = null;
      document = null;
      writer = null;
    }
  }
}
