package com.tomclaw.openim.main;

import java.util.Hashtable;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author solkin
 */
public interface Handler {
  
  public void receivePresence(AccountRoot accountRoot, String from, int statusId);  
  public void receiveMessage(AccountRoot accountRoot, String from, String message);
  public void receiveBuddyList(AccountRoot accountRoot, Vector<GroupItem> items);
  public void operationSuccess(AccountRoot accountRoot, Cookie cookie, Hashtable params);
  public void operationFail(AccountRoot accountRoot, Cookie cookie);
}
