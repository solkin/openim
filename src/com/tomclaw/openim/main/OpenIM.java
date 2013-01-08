package com.tomclaw.openim.main;

import javax.swing.UIManager;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class OpenIM {

  public static MainFrame mainFrame;
  public static ChatFrame chatFrame;

  /**
   * @param args the command line arguments
   */
  public static void main( String[] args ) {
    System.out.println( "Started at: " + System.currentTimeMillis() );
    // setLookAndFeel();
    try {
      UIManager.setLookAndFeel( "com.lipstikLF.LipstikLookAndFeel" );
    } catch ( Throwable ex ) {
    }
    Storage.initStorage();
    mainFrame = new MainFrame();
    chatFrame = new ChatFrame();
    mainFrame.loadAccounts();
    mainFrame.setVisible( true );
  }

  private static void setLookAndFeel() {
    // Well, let's try to set Nimbus explicitly first.
    try {
      UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" );
      System.out.println( "LaF: com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" );
      return; // success
    } catch ( Throwable ex ) {
      // do nothing
    }

    // No? Let's try to find proper class name from the installed LookAndFeel's.
    for ( UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels() ) {
      if ( "Nimbus".equals( info.getName() ) ) {
        try {
          UIManager.setLookAndFeel( info.getClassName() );
          System.out.println( "LaF: " + info.getClassName() + " (found)" );
          return; // success
        } catch ( Throwable ex ) {
          // do nothing
        }
      }
    }

    // Still no? Let's just use the system one.
    try {
      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
      System.out.println( "LaF: " + UIManager.getSystemLookAndFeelClassName() + " (system)" );
      return; // success
    } catch ( Throwable ex2 ) {
      System.out.println( "LaF: everything failed, leaving default one" );
      // Sadly.
    }
  }
}
