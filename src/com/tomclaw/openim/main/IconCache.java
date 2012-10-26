/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomclaw.openim.main;

import java.util.HashMap;
import javax.swing.Icon;

/**
 *
 * @author solkin
 */
public class IconCache {

  private static HashMap<String, Icon> icons = new HashMap();

  public static Icon getImage(String path) {
    if ( icons.containsKey( path ) ) {
      return icons.get( path );
    }
    Icon icon = new javax.swing.ImageIcon( Class.class.getClass().getResource( path ) );
    icons.put( path, icon );
    return icon;
  }
}
