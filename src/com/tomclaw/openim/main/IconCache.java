package com.tomclaw.openim.main;

import java.util.HashMap;
import javax.swing.Icon;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class IconCache {

  private static HashMap<String, Icon> icons = new HashMap();

  public static Icon getImage( String path ) {
    if ( icons.containsKey( path ) ) {
      return icons.get( path );
    }
    Icon icon = new javax.swing.ImageIcon( Class.class.getClass().getResource( path ) );
    icons.put( path, icon );
    return icon;
  }
}
