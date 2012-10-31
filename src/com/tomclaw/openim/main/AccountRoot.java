package com.tomclaw.openim.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author solkin
 */
public abstract class AccountRoot extends javax.swing.tree.DefaultMutableTreeNode {

  /** Ui **/
  private StatusButton statusButton;
  private JPopupMenu buddyPopup;
  private JPopupMenu groupPopup;
  private GroupItem tempGroup;
  /** Core **/
  public static final int CAP_REGISTER = 0;
  public static final int CAP_AUTH_REQ = 1;
  public static final int CAP_SEND_MSG = 2;
  public static final int CAP_BLST_REQ = 3;
  public static final int CAP_BUDD_ADD = 4;
  public static final int CAP_REMB_ITM = 5;
  public static final int CAP_RENB_ITM = 6;
  public static final int CAP_GRLS_REQ = 7;
  public static final int CAP_REMG_ITM = 8;
  public static final int CAP_RENG_ITM = 9;

  public abstract boolean[] getCaps();

  public abstract String getType();

  public abstract void setHandler(Handler handler);

  public abstract StatusUtil getStatusUtil();

  /** Settings **/
  public abstract void setLogin(String login);

  public abstract void setPassword(String password);

  public abstract void setHostPort(String hostPort);

  public abstract String getLogin();

  public abstract String getPassword();

  public abstract String getHostPort();

  /** Serializing **/
  public abstract void setParams(Hashtable params);

  public abstract Hashtable getParams();

  /** Actions **/
  public abstract GroupItem getGroupInstance(String groupName);

  public abstract BuddyItem getBuddyInstance(String userId, String nickName);

  public abstract void connect(int statusIndex) throws IOException, LoginFailedException;

  public abstract void disconnect() throws IOException;

  public abstract Cookie sendMessage(BuddyItem buddyItem, String message) throws IOException;

  public abstract Cookie removeItem(BuddyItem buddyItem) throws IOException;

  public abstract Cookie renameItem(BuddyItem buddyItem, String nickName) throws IOException;

  public abstract Cookie removeGroup(GroupItem groupItem) throws IOException;

  public abstract Cookie renameGroup(GroupItem groupItem, String nickName) throws IOException;

  public abstract void setStatusIndex(int statusIndex) throws IOException;

  public abstract int getStatusIndex();

  public abstract int getRegisterStepsCount();

  public abstract String[][] getRegisterFields(int stepIndex) throws IOException;
  
  public abstract String[][] getLoginFields();

  public abstract void setRegisterStepFields(int stepIndex, String[][] params) throws IOException, InvalidFormException;
  
  public abstract void setLoginStepFields(String[][] params);

  /** Queue stack **/
  public abstract void pushQueue(QueueAction queueAction);

  public abstract void runQueue(Cookie cookie, Hashtable params);

  public abstract QueueAction popQueue(Cookie cookie);

  /** Platform dependent **/
  public BuddyItem getBuddyItem(String userId) {
    int groupsCount = getChildCount();
    for ( int c = 0; c < groupsCount; c++ ) {
      GroupItem groupItem = ( GroupItem ) getChildAt( c );
      int buddyCount = groupItem.getChildCount();
      for ( int i = 0; i < buddyCount; i++ ) {
        BuddyItem buddyItem = ( BuddyItem ) groupItem.getChildAt( i );
        if ( buddyItem.getUserId().equals( userId ) ) {
          return buddyItem;
        }
      }
    }
    if ( tempGroup == null ) {
      tempGroup = getGroupInstance( "Временные" );
    }
    BuddyItem buddyItem = getBuddyInstance( userId, userId );
    tempGroup.addItem( buddyItem );
    OpenIM.mainFrame.updateBuddyList();
    return buddyItem;
  }

  public void setStatusButton(StatusButton statusButton) {
    this.statusButton = statusButton;
  }

  public StatusButton getStatusButton() {
    return statusButton;
  }

  public void initBuddyPopup() {
    buddyPopup = new JPopupMenu();
    boolean[] caps = getCaps();
    if ( caps[CAP_SEND_MSG] ) {
      JMenuItem menuItem = new JMenuItem( "Открыть диалог", IconCache.getImage( "/res/mail-mark-read.png" ) );
      menuItem.addActionListener( new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          System.out.println( "CAP_SEND_MSG" );
          OpenIM.chatFrame.getChatTab( OpenIM.mainFrame.selectedBuddyItem, true );
          OpenIM.chatFrame.setVisible( true );
        }
      } );
      buddyPopup.add( menuItem );
    }
    if ( caps[CAP_REMB_ITM] ) {
      JMenuItem menuItem = new JMenuItem( "Удалить контакт", IconCache.getImage( "/res/list-remove.png" ) );
      menuItem.addActionListener( new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          int result = javax.swing.JOptionPane.showConfirmDialog( null, "Вы действительно хотите удалить контакт?", "Удаление контакта", javax.swing.JOptionPane.YES_NO_OPTION );
          if ( result == javax.swing.JOptionPane.YES_OPTION ) {
            try {
              Cookie cookie = removeItem( OpenIM.mainFrame.selectedBuddyItem );
              QueueAction action = new QueueAction( AccountRoot.this, OpenIM.mainFrame.selectedBuddyItem, cookie ) {

                @Override
                public void actionPerformed(Hashtable params) {
                  System.out.println( "Removing buddy" );
                  GroupItem groupItem = OpenIM.mainFrame.selectedBuddyItem.getParentGroup();
                  groupItem.remove( OpenIM.mainFrame.selectedBuddyItem );
                  OpenIM.mainFrame.updateBuddyList();
                }
              };
              pushQueue( action );
            } catch ( IOException ex ) {
              Logger.getLogger( AccountRoot.class.getName() ).log( Level.SEVERE, null, ex );
            }
          }
        }
      } );
      buddyPopup.add( menuItem );
    }
    if ( caps[CAP_RENB_ITM] ) {
      JMenuItem menuItem = new JMenuItem( "Переименовать контакт", IconCache.getImage( "/res/gtk-edit.png" ) );
      menuItem.addActionListener( new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          final String result = javax.swing.JOptionPane.showInputDialog( null, "Введите новый ник контакта", "Переименование контакта", javax.swing.JOptionPane.OK_CANCEL_OPTION );
          if ( result != null ) {
            try {
              Cookie cookie = renameItem( OpenIM.mainFrame.selectedBuddyItem, result );
              QueueAction action = new QueueAction( AccountRoot.this, OpenIM.mainFrame.selectedBuddyItem, cookie ) {

                @Override
                public void actionPerformed(Hashtable params) {
                  System.out.println( "Renaming buddy" );
                  OpenIM.mainFrame.selectedBuddyItem.setNickName( result );
                  OpenIM.mainFrame.updateBuddyList();
                }
              };
              pushQueue( action );
            } catch ( IOException ex ) {
              Logger.getLogger( AccountRoot.class.getName() ).log( Level.SEVERE, null, ex );
            }
          }
        }
      } );
      buddyPopup.add( menuItem );
    }
  }

  public void initGroupPopup() {
    groupPopup = new JPopupMenu();
    boolean[] caps = getCaps();
    if ( caps[CAP_REMG_ITM] ) {
      JMenuItem menuItem = new JMenuItem( "Удалить группу", IconCache.getImage( "/res/list-remove.png" ) );
      menuItem.addActionListener( new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          System.out.println( "CAP_REMG_ITM" );
          int result = javax.swing.JOptionPane.showConfirmDialog( null, "Вы действительно хотите удалить группу и все вложенные в неё контакты?", "Удаление группы", javax.swing.JOptionPane.YES_NO_OPTION );
          if ( result == javax.swing.JOptionPane.YES_OPTION ) {
            try {
              Cookie cookie = removeGroup( OpenIM.mainFrame.selectedGroupItem );
              QueueAction action = new QueueAction( AccountRoot.this, OpenIM.mainFrame.selectedGroupItem, cookie ) {

                @Override
                public void actionPerformed(Hashtable params) {
                  System.out.println( "Removing buddy" );
                  remove( OpenIM.mainFrame.selectedGroupItem );
                  OpenIM.mainFrame.updateBuddyList();
                }
              };
              pushQueue( action );
            } catch ( IOException ex ) {
              Logger.getLogger( AccountRoot.class.getName() ).log( Level.SEVERE, null, ex );
            }
          }
        }
      } );
      groupPopup.add( menuItem );
    }
    if ( caps[CAP_RENG_ITM] ) {
      JMenuItem menuItem = new JMenuItem( "Переименовать группу", IconCache.getImage( "/res/gtk-edit.png" ) );
      menuItem.addActionListener( new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          System.out.println( "CAP_RENG_ITM" );
          final String result = javax.swing.JOptionPane.showInputDialog( null, "Введите новое название группы", "Переименование группы", javax.swing.JOptionPane.OK_CANCEL_OPTION );
          if ( result != null ) {
            try {
              Cookie cookie = renameGroup( OpenIM.mainFrame.selectedGroupItem, result );
              QueueAction action = new QueueAction( AccountRoot.this, OpenIM.mainFrame.selectedGroupItem, cookie ) {

                @Override
                public void actionPerformed(Hashtable params) {
                  System.out.println( "Renaming group" );
                  OpenIM.mainFrame.selectedGroupItem.setGroupName( result );
                  OpenIM.mainFrame.updateBuddyList();
                }
              };
              pushQueue( action );
            } catch ( IOException ex ) {
              Logger.getLogger( AccountRoot.class.getName() ).log( Level.SEVERE, null, ex );
            }
          }
        }
      } );
      groupPopup.add( menuItem );
    }
  }

  public JPopupMenu getBuddyPopup() {
    return buddyPopup;
  }

  public JPopupMenu getGroupPopup() {
    return groupPopup;
  }
}
