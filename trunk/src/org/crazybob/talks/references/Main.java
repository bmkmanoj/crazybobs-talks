package org.crazybob.talks.references;

import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.*;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.ColumnText;

public class Main {

  /** Used to convert from 60 to 72dpi. */
  public static final float FONT_FACTOR = 1.5f;

  /** JavaOne blue. */
  private static final Color BLUE = new Color(31, 84, 123);

  /** JavaOne light blue. */
  private static final Color LIGHT_BLUE = new Color(81, 125, 156);

  /** JavaOne gray. */
  private static final Color GRAY = new Color(99, 101, 103);
  
  /** Document width. */
  private static final int WIDTH = 1280;

  /** Document height. */
  private static final int HEIGHT = 960;

  public static void main(String[] args) throws DocumentException,
      IOException {
    Document document = new Document(new Rectangle(WIDTH, HEIGHT));
    document.setMargins(0, 0, 0, 0);

    PdfWriter writer = PdfWriter.getInstance(document,
        new FileOutputStream("out/references.pdf"));

    document.open();

    Image titleBackground = Image.getInstance("images/javaone09/title.png");
    titleBackground.setAlignment(Image.UNDERLYING);
    document.add(titleBackground);
    emitTitle(writer);
    Image googleLogo = Image.getInstance("images/google.jpg");
    googleLogo.setAbsolutePosition(200, 75);

    document.add(googleLogo);

    document.newPage();

    Image contentBackground = Image.getInstance("images/javaone09/content.png");
    contentBackground.setAlignment(Image.UNDERLYING);
    document.add(contentBackground);

    ColumnText ct = new ColumnText(writer.getDirectContent());
    ct.setSimpleColumn(50, 36, WIDTH - 36, HEIGHT - 100);
    ct.addElement(new Paragraph("Slide Heading",
        new Font(Font.HELVETICA, 36 * FONT_FACTOR, Font.NORMAL, BLUE)));
    ct.addElement(new Paragraph("Subhead",
        new Font(Font.HELVETICA, 28 * FONT_FACTOR, Font.NORMAL, LIGHT_BLUE)));

    ct.addElement(new Paragraph(" ",
        new Font(Font.HELVETICA, 6 * FONT_FACTOR, Font.NORMAL, LIGHT_BLUE)));

    Chunk spacer = new Chunk(" ",
        new Font(Font.HELVETICA, 28 * FONT_FACTOR, Font.NORMAL, LIGHT_BLUE));

    Chunk level1 = new Chunk(">",
        new Font(Font.HELVETICA, 22 * FONT_FACTOR, Font.NORMAL, LIGHT_BLUE));
    level1.setTextRise(6);

    Paragraph b1 = new Paragraph(level1);
    b1.add(spacer);
    b1.setLeading(28 * FONT_FACTOR * 1.5f);
    b1.add(new Paragraph("Level One bullet",
        new Font(Font.HELVETICA, 28 * FONT_FACTOR, Font.NORMAL, Color.BLACK)));
    ct.addElement(b1);
    ct.addElement(b1);

    Chunk level2 = new Chunk(".",
        new Font(Font.HELVETICA, 28 * FONT_FACTOR, Font.BOLD, LIGHT_BLUE));
    level2.setTextRise(10);

    spacer.setHorizontalScaling(5);
    Paragraph b2 = new Paragraph(spacer);
    b2.add(spacer);
    spacer.setHorizontalScaling(1);
    b2.setLeading(26 * FONT_FACTOR * 1.5f);
    b2.add(level2);
    b2.add(spacer);
    b2.add(new Paragraph("Level Two bullet",
        new Font(Font.HELVETICA, 26 * FONT_FACTOR, Font.NORMAL, Color.BLACK)));
    ct.addElement(b2);

    ct.go();

		document.close();

    Runtime.getRuntime().exec(new String[] { "open", "out/references.pdf" });
  }

  private static void emitTitle(PdfWriter writer) throws DocumentException {
    ColumnText ct = new ColumnText(writer.getDirectContent());
    ct.setSimpleColumn(650, 0, 1200, 400);
    ct.addElement(new Paragraph("The Ghost in the Virtual Machine\n"
        + "A Reference to References\n\n",
        new Font(Font.HELVETICA, 24 * FONT_FACTOR, Font.NORMAL, BLUE)));
    ct.addElement(new Paragraph("Bob Lee",
        new Font(Font.HELVETICA, 18 * FONT_FACTOR, Font.NORMAL, GRAY)));
    ct.addElement(new Paragraph("Google Inc.",
        new Font(Font.HELVETICA, 14 * FONT_FACTOR, Font.NORMAL, GRAY)));
    ct.go();
  }
}
