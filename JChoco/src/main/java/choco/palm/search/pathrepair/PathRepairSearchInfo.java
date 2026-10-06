package choco.palm.search.pathrepair;

import choco.Constraint;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.explain.SearchInfo;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 *
 * <p>User: Administrateur
 *
 * <p>Date: 15 janv. 2004
 *
 * <p>Time: 14:59:44
 *
 * <p>To change this template use Options | File Templates.
 */
public class PathRepairSearchInfo implements SearchInfo {

  /*

  *  The weigth of a decision constraint appearing in the conflicts in memory

  *  It corresponds to its frequency among the all conflict set weighted by the size of each conflict

  */

  private class PathRepairComparator implements Comparator<Constraint> {

    public int compare(final Constraint o1, final Constraint o2) {

      final PalmConstraintPlugin plug1 = (PalmConstraintPlugin) o1.getPlugIn();

      final PalmConstraintPlugin plug2 = (PalmConstraintPlugin) o2.getPlugIn();

      if (((PathRepairSearchInfo) plug1.getSearchInfo()).getWeigth()
          > ((PathRepairSearchInfo) plug2.getSearchInfo()).getWeigth()) {
        return -1;
      } else if (((PathRepairSearchInfo) plug1.getSearchInfo()).getWeigth()
          == ((PathRepairSearchInfo) plug2.getSearchInfo()).getWeigth()) {

        if (plug1.getTimeStamp() > plug2.getTimeStamp()) {
          return -1;
        } else if (plug1.getTimeStamp() < plug2.getTimeStamp()) {
          return 1;
        } else {
          return 0;
        }

      } else {
        return 1;
      }
    }
  }

  private float weigthInfo = 0;

  private final Comparator<Constraint> comparatorInfo;

  public PathRepairSearchInfo() {

    this.comparatorInfo = new PathRepairComparator();
  }

  public void add(final float val) {
    this.weigthInfo += val;
  }

  public Comparator<Constraint> getComparator() {

    return this.comparatorInfo;
  }

  public float getWeigth() {

    return this.weigthInfo;
  }

  public void set(final float val) {
    this.weigthInfo = val;
  }
}
