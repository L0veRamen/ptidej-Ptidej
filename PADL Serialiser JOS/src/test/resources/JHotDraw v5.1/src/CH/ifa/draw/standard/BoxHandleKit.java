/*
 * @(#)BoxHandleKit.java 5.1
 *
 */

package CH.ifa.draw.standard;

import CH.ifa.draw.framework.Figure;
import CH.ifa.draw.framework.Handle;
import java.util.Vector;

/**
 * A set of utility methods to create Handles for the common locations on a figure's display box.
 *
 * @see Handle
 */

// TBD: use anonymous inner classes (had some problems with JDK 1.1)

public class BoxHandleKit {

  /** Fills the given Vector with handles at each corner of a figure. */
  public static void addCornerHandles(Figure f, Vector handles) {
    handles.addElement(southEast(f));
    handles.addElement(southWest(f));
    handles.addElement(northEast(f));
    handles.addElement(northWest(f));
  }

  /**
   * Fills the given Vector with handles at each corner and the north, south, east, and west of the
   * figure.
   */
  public static void addHandles(Figure f, Vector handles) {
    addCornerHandles(f, handles);
    handles.addElement(south(f));
    handles.addElement(north(f));
    handles.addElement(east(f));
    handles.addElement(west(f));
  }

  public static Handle south(Figure owner) {
    return new SouthHandle(owner);
  }

  public static Handle southEast(Figure owner) {
    return new SouthEastHandle(owner);
  }

  public static Handle southWest(Figure owner) {
    return new SouthWestHandle(owner);
  }

  public static Handle north(Figure owner) {
    return new NorthHandle(owner);
  }

  public static Handle northEast(Figure owner) {
    return new NorthEastHandle(owner);
  }

  public static Handle northWest(Figure owner) {
    return new NorthWestHandle(owner);
  }

  public static Handle east(Figure owner) {
    return new EastHandle(owner);
  }

  public static Handle west(Figure owner) {
    return new WestHandle(owner);
  }
}
