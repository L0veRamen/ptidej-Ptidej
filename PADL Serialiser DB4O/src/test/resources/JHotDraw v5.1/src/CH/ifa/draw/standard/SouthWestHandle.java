/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import CH.ifa.draw.framework.DrawingView;
import CH.ifa.draw.framework.Figure;
import java.awt.Point;
import java.awt.Rectangle;

class SouthWestHandle extends LocatorHandle {
  SouthWestHandle(Figure owner) {
    super(owner, RelativeLocator.southWest());
  }

  public void invokeStep(int x, int y, int anchorX, int anchorY, DrawingView view) {
    Rectangle r = owner().displayBox();
    owner()
        .displayBox(
            new Point(Math.min(r.x + r.width, x), r.y), new Point(r.x + r.width, Math.max(r.y, y)));
  }
}
