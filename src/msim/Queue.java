package msim;

import com.tomclaw.openim.main.Cookie;
import com.tomclaw.openim.main.QueueAction;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author solkin
 */
public class Queue {

  private static ArrayList actions = new ArrayList();

  public void pushQueueAction(QueueAction action) {
    actions.add( action );
  }

  public QueueAction popQueueAction(Cookie cookie) {
    System.out.println( "Actions count: " + actions.size() );

    QueueAction queueAction;
    for ( int c = 0; c < actions.size(); c++ ) {
      queueAction = ( QueueAction ) actions.get( c );
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

  public void runQueueAction(Cookie cookie, HashMap params) {
    QueueAction queueAction;
    for ( int c = 0; c < actions.size(); c++ ) {
      queueAction = ( QueueAction ) actions.get( c );
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
