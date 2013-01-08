package com.tomclaw.openim.main;

import javax.swing.JTree;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class BuddyList extends JTree {

  public BuddyList() {
    this.setCellRenderer( new BuddyListRenderer() );
  }
}
