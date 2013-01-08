package com.tomclaw.openim.main;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public interface StatusUtil {

  public int getStatusCount();

  public String getStatusDescr( int statusIndex );

  public String getStatusValue( int statusIndex );

  public int getStatusIndex( String value );

  public int getOfflineIndex();

  public int getOnlineIndex();
}
