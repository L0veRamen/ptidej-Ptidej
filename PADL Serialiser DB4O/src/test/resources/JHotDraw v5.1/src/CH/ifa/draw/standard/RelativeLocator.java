/*
 * @(#)RelativeLocator.java 5.1
 *
 */

package CH.ifa.draw.standard;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Locator;
import CH.ifa.draw.util.StorableInput;
import CH.ifa.draw.util.StorableOutput;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

/**
 * A locator that specfies a point that is relative to the bounds of a figure.
 *
 * @see Locator
 */
public class RelativeLocator extends AbstractLocator {
  /*
   * Serialization support.
   */
  private static final long serialVersionUID = 2619148876087898602L;
  private int relativeLocatorSerializedDataVersion = 1;

  double fRelativeX;
  double fRelativeY;

  public RelativeLocator() {
    fRelativeX = 0.0;
    fRelativeY = 0.0;
  }

  public RelativeLocator(double relativeX, double relativeY) {
    fRelativeX = relativeX;
    fRelativeY = relativeY;
  }

  public Point locate(Figure owner) {
    Rectangle r = owner.displayBox();
    return new Point(r.x + (int) (r.width * fRelativeX), r.y + (int) (r.height * fRelativeY));
  }

  public void write(StorableOutput dw) {
    super.write(dw);
    dw.writeDouble(fRelativeX);
    dw.writeDouble(fRelativeY);
  }

  public void read(StorableInput dr) throws IOException {
    super.read(dr);
    fRelativeX = dr.readDouble();
    fRelativeY = dr.readDouble();
  }

  public static Locator east() {
    return new RelativeLocator(1.0, 0.5);
  }

  /** North. */
  public static Locator north() {
    return new RelativeLocator(0.5, 0.0);
  }

  /** West. */
  public static Locator west() {
    return new RelativeLocator(0.0, 0.5);
  }

  /** North east. */
  public static Locator northEast() {
    return new RelativeLocator(1.0, 0.0);
  }

  /** North west. */
  public static Locator northWest() {
    return new RelativeLocator(0.0, 0.0);
  }

  /** South. */
  public static Locator south() {
    return new RelativeLocator(0.5, 1.0);
  }

  /** South east. */
  public static Locator southEast() {
    return new RelativeLocator(1.0, 1.0);
  }

  /** South west. */
  public static Locator southWest() {
    return new RelativeLocator(0.0, 1.0);
  }

  /** Center. */
  public static Locator center() {
    return new RelativeLocator(0.5, 0.5);
  }
}
