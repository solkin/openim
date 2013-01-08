package com.tomclaw.openim.main;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class StatusButton extends JButton implements ActionListener {

  private AccountRoot accountRoot;
  private StatusUtil statusUtil;
  private JPopupMenu statusMenu;

  public StatusButton(final AccountRoot accountRoot, final StatusUtil statusUtil) {
    this.accountRoot = accountRoot;
    this.statusUtil = statusUtil;
    statusMenu = new JPopupMenu();
    for ( int c = 0; c < statusUtil.getStatusCount(); c++ ) {
      final int statusIndex = c;
      JMenuItem statusItem = new JMenuItem( statusUtil.getStatusDescr( c ) );
      statusItem.setIcon( IconCache.getImage(
              "/".concat( accountRoot.getType() ).concat( "/res/" ).
              concat( statusUtil.getStatusValue( c ) ).
              concat( ".png" ) ) );
      statusItem.addActionListener( new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent ae) {
          try {
            if ( accountRoot.getStatusIndex() == statusUtil.getOfflineIndex()
                    && statusIndex != statusUtil.getOfflineIndex() ) {
              accountRoot.connect( statusIndex );
            } else if ( accountRoot.getStatusIndex() != statusUtil.getOfflineIndex()
                    && statusIndex == statusUtil.getOfflineIndex() ) {
              accountRoot.disconnect();
            } else {
              accountRoot.setStatusIndex( statusIndex );
            }
            updateIcon();
          } catch ( IOException ex ) {
            javax.swing.JOptionPane.showMessageDialog( null, "Произошла ошибка ввода-вывода" );
          } catch ( LoginFailedException ex ) {
            javax.swing.JOptionPane.showMessageDialog( null, "Неверные параметры учётной записи" );
          }
        }
      } );
      statusMenu.add( statusItem );
    }
    updateIcon();
    this.addActionListener( StatusButton.this );
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    statusMenu.setInvoker( this );
    statusMenu.setVisible( true );
    Point screenPoint = this.getLocationOnScreen();
    statusMenu.setLocation( screenPoint.x, screenPoint.y + this.getHeight() );
  }
  
  public final void updateIcon() {
    this.setIcon( IconCache.getImage(
            "/".concat( accountRoot.getType() ).concat( "/res/" ).
            concat( statusUtil.getStatusValue( accountRoot.getStatusIndex() ) ).
            concat( ".png" ) ) );
  }
  
  public AccountRoot getAccountRoot() {
    return accountRoot;
  }
}
