/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.ui.about;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import ptidej.viewer.widget.Dialog;
import ptidej.viewer.widget.EmbeddedPanel;
import ptidej.viewer.widget.ScrollPane;

public class AboutDialog extends Dialog {
  private static final long serialVersionUID = 1L;

  private static AboutDialog UniqueInstance;

  public static AboutDialog getUniqueInstance() {
    return AboutDialog.getUniqueInstance(null);
  }

  public static AboutDialog getUniqueInstance(final Frame owner) {
    return AboutDialog.getUniqueInstance(
        owner, Resources.getFrameTitle(Resources.ABOUT, AboutDialog.class), true, 630, 520);
  }

  public static AboutDialog getUniqueInstance(
      final Frame owner,
      final String title,
      final boolean modal,
      final int width,
      final int height) {

    if (AboutDialog.UniqueInstance == null) {
      AboutDialog.UniqueInstance = new AboutDialog(owner, title, modal, width, height);
      AboutDialog.UniqueInstance.setLocationRelativeTo(owner);
    }
    return AboutDialog.UniqueInstance;
  }

  public static void main(final String[] args) {
    AboutDialog.getUniqueInstance().setVisible(true);
  }

  private JTabbedPane pnlTabs;

  private AboutDialog(
      final Frame owner, final String title, final boolean modal, final int width, final int height)
      throws HeadlessException {

    super(owner, title, modal, width, height);
    AboutDialog.UniqueInstance = this;
    this.getContentPane()
        .add(
            new JLabel(Utils.getImageIcon(Resources.PTIDEJ_LOGO, AboutDialog.class), 0),
            BorderLayout.NORTH);

    this.pnlTabs = new JTabbedPane();
    this.addTab(Resources.PTIDEJ);
    this.addTab(Resources.DEVELOPPERS);
    this.addTab(Resources.COPYRIGHT);

    this.getContentPane().add(this.pnlTabs, BorderLayout.CENTER);
    // TODO Redo the PtidejFooter.png image
    //	this.getContentPane().add(
    //		new JLabel(Utils.getIcon(
    //			Constants.PTIDEJ_LOGO_FOOTER,
    //			AboutDialog.class), 0),
    //		BorderLayout.SOUTH);
  }

  private void addTab(final String aKey) {
    final EmbeddedPanel panel = new EmbeddedPanel();
    panel.addTextPane(aKey, AboutDialog.class);
    this.pnlTabs.addTab(Resources.getTabTitle(aKey, AboutDialog.class), new ScrollPane(panel));
  }
}
