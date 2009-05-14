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

package org.crazybob.deck.styles;

import org.crazybob.deck.Style;
import org.crazybob.deck.Slide;
import org.crazybob.deck.Deck;

/**
 *
 *
 */
@Style(
    width = 1280,
    height = 960,
    dpi = 60,
    titleSlide = @Style.TitleSlide(
        background = "images/javaone09/title.png",
        titleColor = "20547A",
        subtitleColor = "20547A",
        authorColor = "8B8D92",
        companyColor = "8B8D92",
        titleSize = 28,
        subtitleSize = 28,
        authorSize = 26,
        companySize = 18,
        titleX = 0,
        titleY = 0
    ),
    contentSlide = @Style.ContentSlide(
        background = "images/javaone09/content.png"
    )
)
public class JavaOne09 implements Style.Provider {

  public Style style() {
    return JavaOne09.class.getAnnotation(Style.class);
  }

  public Slide titleSlide(Deck deck) {
    throw new UnsupportedOperationException();
  }
}
