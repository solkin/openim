/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomclaw.openim.main;

import com.tomclaw.bingear.BinGear;
import com.tomclaw.bingear.GroupNotFoundException;
import com.tomclaw.bingear.IncorrectValueException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author solkin
 */
public class Storage {

  public static BinGear accounts;
  public static BinGear settings;
  public static String appFolder;
  public static String accountsFile = "accounts.ini";
  public static String settingsFile = "settings.ini";

  public static void initStorage() {
    appFolder = System.getProperty( "user.dir" );
    if ( !appFolder.endsWith( System.getProperty( "file.separator" ) ) ) {
      appFolder += System.getProperty( "file.separator" );
    }
    System.out.println( "Runned from: " + appFolder );
    accounts = new BinGear();
    settings = new BinGear();
    try {
      File file;
      file = new File( appFolder + accountsFile );
      if ( !file.exists() ) {
        file.createNewFile();
      }
      accounts.importFromIni( new java.io.DataInputStream( new FileInputStream( file ) ) );
      file = new File( appFolder + settingsFile );
      if ( !file.exists() ) {
        file.createNewFile();
      }
      settings.importFromIni( new java.io.DataInputStream( new FileInputStream( file ) ) );
    } catch ( Throwable ex ) {
      System.out.println( ex.getMessage() );
    }
  }

  public static void saveAccounts() {
    try {
      File file;
      file = new File( appFolder + accountsFile );
      if ( !file.exists() ) {
        file.createNewFile();
      }
      accounts.exportToIni( new FileOutputStream( file ) );
    } catch ( Throwable ex ) {
      System.out.println( ex.getMessage() );
    }
  }
}