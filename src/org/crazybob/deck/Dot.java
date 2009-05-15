package org.crazybob.deck;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.Image;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public class Dot extends InlineElement {

  final String dot;

  Dot(String dot) {
    this.dot = dot;
  }

  int newHeight = -1;

  public Dot height(int pixels) {
    this.newHeight = pixels;
    return this;
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    try {
      Process process = Runtime.getRuntime().exec(
          new String[] { "/usr/local/bin/dot", "-Tpdf" });
      OutputStream out = process.getOutputStream();
      out.write(dot.getBytes());
      out.close();
      PdfReader reader = new PdfReader(process.getInputStream());
      PdfImportedPage page = deck.writer.getImportedPage(reader, 1);
      Image image = Image.getInstance(page);
      int w = (int) image.getWidth();
      int h = (int) image.getHeight();
      image.scaleAbsolute(w * newHeight / h, newHeight);
      image.setAlignment(Element.ALIGN_MIDDLE);

      column.addElement(image);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (BadElementException e) {
      throw new RuntimeException(e);
    }
  }

  public static Dot parse(final String dot) {
    return new Dot(dot);
  }
}