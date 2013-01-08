package com.tomclaw.openim.main;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public abstract class GroupItem extends javax.swing.tree.DefaultMutableTreeNode {

  private AccountRoot accountRoot;

  public abstract void setGroupName( String groupName );

  public abstract String getGroupName();

  public void addItem( BuddyItem buddyItem ) {
    add( buddyItem );
  }

  public void setAccountRoot( AccountRoot accountRoot ) {
    this.accountRoot = accountRoot;
  }

  public AccountRoot getAccountRoot() {
    return accountRoot;
  }
}
