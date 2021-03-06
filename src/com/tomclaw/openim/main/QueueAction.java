package com.tomclaw.openim.main;

import java.util.HashMap;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class QueueAction {

  public Cookie cookie;
  public AccountRoot accountRoot;
  public GroupItem groupItem;
  public BuddyItem buddyItem;

  public QueueAction( AccountRoot accountRoot, BuddyItem buddyItem, Cookie cookie ) {
    this.accountRoot = accountRoot;
    this.buddyItem = buddyItem;
    this.cookie = cookie;
  }

  public QueueAction( AccountRoot accountRoot, GroupItem groupItem, Cookie cookie ) {
    this.accountRoot = accountRoot;
    this.groupItem = groupItem;
    this.cookie = cookie;
  }

  public void setBuddyItem( BuddyItem buddyItem ) {
    this.buddyItem = buddyItem;
  }

  public void setBuddyItem( GroupItem groupItem ) {
    this.groupItem = groupItem;
  }

  public BuddyItem getBuddyItem() {
    return buddyItem;
  }

  public GroupItem getBuddyGroup() {
    return groupItem;
  }

  public void setAccountRoot( AccountRoot accountRoot ) {
    this.accountRoot = accountRoot;
  }

  public AccountRoot getAccountRoot() {
    return accountRoot;
  }

  public void setCookie( Cookie cookie ) {
    this.cookie = cookie;
  }

  public Cookie getCookie() {
    return cookie;
  }

  public void actionPerformed( HashMap params ) {
    // Empty method, will be overwritten 
  }
}
