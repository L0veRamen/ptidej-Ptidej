/*
 * @(#)Helper.java
 *
 * Project:		JHotdraw - a GUI framework for technical drawings
 *				http://www.jhotdraw.org
 *				http://jhotdraw.sourceforge.net
 * Copyright:	(c) by the original author(s) and all contributors
 * License:		Lesser GNU Public License (LGPL)
 *				http://www.opensource.org/licenses/lgpl-license.html
 */

package org.jhotdraw.contrib;

import java.awt.*;
import org.jhotdraw.framework.DrawingView;

/**
 * @author C.L.Gilbert <dnoyeb@sourceforge.net>
 * @version <$CURRENT_VERSION$>
 */
public class Helper {

  public static DrawingView getDrawingView(Container container) {
    DrawingView oldDrawingView = null;
    Component[] components = container.getComponents();
    for (int i = 0; i < components.length; i++) {
      if (components[i] instanceof DrawingView) {
        return (DrawingView) components[i];
      } else if (components[i] instanceof Container) {
        oldDrawingView = getDrawingView((Container) components[i]);
        if (oldDrawingView != null) {
          return oldDrawingView;
        }
      }
    }
    return null;
  }

  public static DrawingView getDrawingView(Component component) {
    if (Container.class.isInstance(component)) {
      return getDrawingView((Container) component);
    } else if (DrawingView.class.isInstance(component)) {
      return (DrawingView) component;
    } else {
      return null;
    }
  }
}
