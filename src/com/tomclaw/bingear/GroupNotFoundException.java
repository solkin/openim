package com.tomclaw.bingear;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class GroupNotFoundException extends Throwable {

  /**
   * Constructs a <code>GroupNotFoundException</code> with no detail message.
   */
  public GroupNotFoundException() {
    super();
  }

  /**
   * Constructs a <code>GroupNotFoundException</code> with the specified
   * detail message.
   *
   * @param   s   the detail message.
   */
  public GroupNotFoundException( String s ) {
    super( s );
  }
}
