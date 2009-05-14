/**
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

  Style style;
  String title;
  String subtitle;
  String author;
  String company;
  final List<Slide> slides = new ArrayList<Slide>();
  Document document;
  PdfWriter writer;

  public Style style() {
    return this.style;
  }

  public Deck style(Style style) {
    if (style != null) throw new IllegalStateException("style already set.");
    this.style = style;
    return this;
  }

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

  public void writeTo(String path, boolean open) {
    document = new Document(new Rectangle(style.width(),
        style.height()));
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

    for (Slide slide : slides) {
      slide.addTo(this);
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
  }
}
