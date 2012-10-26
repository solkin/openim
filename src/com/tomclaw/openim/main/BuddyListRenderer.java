/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomclaw.openim.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;

/**
 *
 * @author solkin
 */
public class BuddyListRenderer extends JPanel implements javax.swing.tree.TreeCellRenderer {

  private JLabel userUserIdLabel = new JLabel();
  private JLabel userNickLabel = new JLabel();

  public BuddyListRenderer() {
    userUserIdLabel = new JLabel();
    userNickLabel = new JLabel();
    setLayout( new javax.swing.BoxLayout( this, javax.swing.BoxLayout.Y_AXIS ) );
    userNickLabel.setFont( userNickLabel.getFont().deriveFont( Font.BOLD ) );
    add( userNickLabel );
    add( userUserIdLabel );
    setVisible( true );
    setOpaque( false );
    userUserIdLabel.setVisible( true );
    userNickLabel.setVisible( true );

  }

  @Override
  public Component getTreeCellRendererComponent(JTree tree,
          Object value, boolean sel, boolean expanded, boolean leaf,
          int row, boolean hasFocus) {
    if ( hasFocus ) {
      userNickLabel.setForeground( javax.swing.UIManager.getDefaults().getColor( "List.selectionForeground" ) );
      userUserIdLabel.setForeground( javax.swing.UIManager.getDefaults().getColor( "List.selectionForeground" ) );
    } else {
      userNickLabel.setForeground( javax.swing.UIManager.getDefaults().getColor( "List.foreground" ) );
      userUserIdLabel.setForeground( javax.swing.UIManager.getDefaults().getColor( "List.foreground" ) );
    }
    userUserIdLabel.setText( "" );
    userNickLabel.setText( "" );
    userNickLabel.setIcon( null );


    if ( value instanceof AccountRoot ) {
      AccountRoot accountRoot = ( AccountRoot ) value;
      userNickLabel.setText( accountRoot.getLogin() );
      if ( hasFocus ) {
        OpenIM.mainFrame.selectedBuddyItem = null;
        OpenIM.mainFrame.selectedGroupItem = null;
        OpenIM.mainFrame.jTree1.setComponentPopupMenu( null );
      }
    } else if ( value instanceof GroupItem ) {
      GroupItem groupItem = ( GroupItem ) value;
      userNickLabel.setText( groupItem.getGroupName() );
      if ( hasFocus ) {
        OpenIM.mainFrame.selectedBuddyItem = null;
        OpenIM.mainFrame.selectedGroupItem = groupItem;
        OpenIM.mainFrame.jTree1.setComponentPopupMenu( groupItem.getAccountRoot().getGroupPopup() );
      }
    } else if ( value instanceof BuddyItem ) {
      BuddyItem buddyItem = ( BuddyItem ) value;
      userUserIdLabel.setText( buddyItem.getUserId() );
      userNickLabel.setText( buddyItem.getNickName() );
      userNickLabel.setIcon( IconCache.getImage( "/".concat( buddyItem.getAccountRoot().getType() ).concat( "/res/" ).
              concat( buddyItem.getAccountRoot().getStatusUtil().getStatusValue( buddyItem.getStatusId() ) ).
              concat( ".png" ) ) );
      if ( hasFocus ) {
        OpenIM.mainFrame.selectedGroupItem = null;
        OpenIM.mainFrame.selectedBuddyItem = buddyItem;
        OpenIM.mainFrame.jTree1.setComponentPopupMenu( buddyItem.getAccountRoot().getBuddyPopup() );
      }
    }

    userUserIdLabel.setVisible( true );
    userNickLabel.setVisible( true );

    return this;
  }
}
