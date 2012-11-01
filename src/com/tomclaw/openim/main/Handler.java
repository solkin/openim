package com.tomclaw.openim.main;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author solkin
 */
public interface Handler {
  
  public void receivePresence(AccountRoot accountRoot, String from, int statusId);  
  public void receiveMessage(AccountRoot accountRoot, String from, String message);
  public void receiveBuddyList(AccountRoot accountRoot, ArrayList<GroupItem> items);
  public void operationSuccess(AccountRoot accountRoot, Cookie cookie, HashMap params);
  public void operationFail(AccountRoot accountRoot, Cookie cookie);
}
