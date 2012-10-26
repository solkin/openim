/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msim;

import com.tomclaw.openim.main.Cookie;
import com.tomclaw.openim.main.QueueAction;
import com.tomclaw.utils.LogUtil;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author solkin
 */
public class Queue {

  private static Vector actions = new Vector();

  public void pushQueueAction(QueueAction action) {
    actions.addElement( action );
  }

  public QueueAction popQueueAction(Cookie cookie) {
    System.out.println( "Actions count: " + actions.size() );

    QueueAction queueAction;
    for ( int c = 0; c < actions.size(); c++ ) {
      queueAction = ( QueueAction ) actions.elementAt( c );
      if ( queueAction != null ) {
        if ( queueAction.cookie.cookieString.equals( cookie.cookieString ) ) {
          System.out.println( "QueueAction found!" );
          actions.remove( queueAction );
          return queueAction;
        }
      }
    }
    return null;
  }

  public void runQueueAction(Cookie cookie, Hashtable params) {
    QueueAction queueAction;
    for ( int c = 0; c < actions.size(); c++ ) {
      queueAction = ( QueueAction ) actions.elementAt( c );
      if ( queueAction != null ) {
        if ( queueAction.cookie.cookieString.equals( cookie.cookieString ) ) {
          System.out.println( "QueueAction found!" );
          try {
            queueAction.actionPerformed( params );
          } catch ( Throwable ex ) {
            System.out.println( ex.toString() );
          }
          actions.remove( queueAction );
          return;
        }
      }
    }
  }
}
