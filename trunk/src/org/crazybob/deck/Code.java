package org.crazybob.deck;

import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.DocumentException;

import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;

public class Code extends Element {

  final List<String> lines;

  Code(List<String> lines) {
    this.lines = lines;
  }

  public static Code parseFile(String path) {
    try {
      List<String> lines = new ArrayList<String>();
      BufferedReader in = new BufferedReader(new FileReader(path));
      String line;
      while ((line = in.readLine()) != null) {
        lines.add(line);
      }
      in.close();
      return new Code(lines);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  void writePdf(Deck deck, ColumnText column) throws DocumentException {
    Template template = deck.template;
    Font font = template.codeFont();
    for (String line : lines) {
      String trimmed = line.trim();
      if (trimmed.startsWith("///")) {
        String command = trimmed.substring(4);
        if (command.equals("HIGHLIGHT")) {
          font = template.highlightedCodeFont();
        } else if (command.equals("NORMAL")) {
          font = template.codeFont();
        } else if (command.startsWith("...")) {
          String indent = line.substring(0, line.indexOf("///"));
          column.addElement(font.newParagraph(indent + "... // "
              + command.substring(3)));
        }
      } else if (trimmed.startsWith("package ")) {
        continue;
      } else {
        column.addElement(font.newParagraph(line));
      }
    }
  }
}
