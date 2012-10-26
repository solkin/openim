package com.tomclaw.openim.main;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author i.solkin
 */
public class EventHandler implements Handler {

  @Override
  public void receivePresence(AccountRoot accountRoot, String from, int statusId) {
    System.out.println( "from: " + from + " presence: " + statusId );
    BuddyItem buddyItem = accountRoot.getBuddyItem( from );
    buddyItem.setStatusId(statusId);
    OpenIM.mainFrame.updateBuddyList();
  } 
    
  @Override
  public void receiveMessage(AccountRoot accountRoot, String from, String message) {
    System.out.println( "from: " + from + " message: " + message );
    BuddyItem buddyItem = accountRoot.getBuddyItem( from );
    ChatPanel chatPanel = OpenIM.chatFrame.getChatTab( buddyItem, false );
    chatPanel.addChatItem(message, true);
  }

  @Override
  public void receiveBuddyList(AccountRoot accountRoot, Vector<GroupItem> items) {
    accountRoot.removeAllChildren();
    for ( int c = 0; c < items.size(); c++ ) {
      accountRoot.add( items.elementAt( c ) );
    }
    OpenIM.mainFrame.updateBuddyList();
  }
  
  @Override
  public void operationSuccess(AccountRoot accountRoot, Cookie cookie, Hashtable params) {
    accountRoot.runQueue( cookie, params );
  }
  
  @Override
  public void operationFail(AccountRoot accountRoot, Cookie cookie) {
    accountRoot.popQueue( cookie );
  }
}
