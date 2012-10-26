/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomclaw.openim.main;

/**
 *
 * @author solkin
 */
public abstract class GroupItem extends javax.swing.tree.DefaultMutableTreeNode {
  
  private AccountRoot accountRoot;
  
  public abstract void setGroupName(String groupName);
  public abstract String getGroupName();
  
  public void addItem(BuddyItem buddyItem) {
      this.add(buddyItem);
  }
  
  public void setAccountRoot(AccountRoot accountRoot) {
    this.accountRoot = accountRoot;
  }
  
  public AccountRoot getAccountRoot() {
    return accountRoot;
  }
}
