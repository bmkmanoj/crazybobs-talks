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

import java.awt.*;

/**
 *
 *
 */
class ColorParser {
  
  Color parse(String rgb) {
    if (rgb.length() != 6) {
      throw new IllegalArgumentException("Expected length 6: " + rgb);
    }
    int r = Integer.parseInt(rgb.substring(0, 2), 16);
    int g = Integer.parseInt(rgb.substring(2, 4), 16);
    int b = Integer.parseInt(rgb.substring(4, 6), 16);
    return new Color(r, g, b);
  }
}
