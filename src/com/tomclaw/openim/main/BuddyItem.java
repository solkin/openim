package com.tomclaw.openim.main;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public abstract class BuddyItem extends javax.swing.tree.DefaultMutableTreeNode {

  private AccountRoot accountRoot;
  private GroupItem groupItem;

  public abstract void setNickName( String nickName );

  public abstract String getNickName();

  public abstract void setUserId( String userId );

  public abstract String getUserId();

  public abstract void setStatusId( int statusId );

  public abstract int getStatusId();

  public void setParentGroup( GroupItem groupItem ) {
    this.groupItem = groupItem;
  }

  public GroupItem getParentGroup() {
    return groupItem;
  }

  public void setAccountRoot( AccountRoot accountRoot ) {
    this.accountRoot = accountRoot;
  }

  public AccountRoot getAccountRoot() {
    return accountRoot;
  }
}
