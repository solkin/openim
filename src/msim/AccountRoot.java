package msim;

import com.tomclaw.openim.main.*;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author solkin
 */
public class AccountRoot extends com.tomclaw.openim.main.AccountRoot {

  private final boolean[] caps = new boolean[]{ true, true, true, true, true, true, true, true, true, true };
  private String login;
  private String password;
  private String host;
  private int port = 3215;
  private int statusIndex = 0;
  private Connection connection;
  private Session session;
  private Handler handler;
  private StatusUtil statusUtil = new StatusUtil();
  private Queue queue = new Queue();

  @Override
  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  @Override
  public boolean[] getCaps() {
    return caps; 
  }
  
  @Override
  public void setParams(HashMap params) {
    login = ( String ) ( params.get( "login" ) );
    password = ( String ) params.get( "password" );
    host = ( String ) params.get( "host" );
    port = Integer.parseInt( ( String ) params.get( "port" ) );
  }

  @Override
  public HashMap getParams() {
    HashMap hashtable = new HashMap();
    hashtable.put( "login", login );
    hashtable.put( "password", password );
    hashtable.put( "host", host );
    hashtable.put( "port", String.valueOf( port ) );
    return hashtable;
  }

  @Override
  public void setLogin(String login) {
    this.login = login;
  }

  @Override
  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getLogin() {
    return login;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void establishConnection() throws IOException {
    if ( connection != null ) {
      disconnect();
    }
    connection = new Connection();
    connection.connect( host, port );
    session = new Session( this, connection, handler );
  }

  @Override
  public void connect(int statusIndex) throws IOException, LoginFailedException {
    if ( connection != null ) {
      disconnect();
    }
    connection = new Connection();
    connection.connect( host, port );
    session = new Session( this, connection, handler );
    /** Login **/
    session.sendPacket( PacketBase.authRequest( login, password ) );
    Packet packet = session.parsePacket( true );
    if ( packet.type.equals( PacketType.TYPE_SUCCESS ) && packet.content.equals( PacketType.OPER_AUTH ) ) {
      session.sendPacket( PacketBase.requestBuddyList() );
      this.statusIndex = 1;
      /** Session start **/
      session.start();
    } else if ( packet.type.equals( PacketType.TYPE_FAIL ) && packet.content.equals( PacketType.OPER_AUTH ) ) {
      this.statusIndex = 0;
      throw new LoginFailedException();
    } else if ( packet.type.equals( PacketType.TYPE_WTF ) && packet.content.equals( PacketType.OPER_AUTH ) ) {
    } else {
      this.statusIndex = 0;
      throw new LoginFailedException();
    }
  }

  @Override
  public void disconnect() throws IOException {
    session.stop();
    connection.disconnect();
    connection = null;
    this.statusIndex = 0;
  }

  @Override
  public Cookie sendMessage(com.tomclaw.openim.main.BuddyItem buddyItem, String message) throws IOException {
    return session.sendPacket( PacketBase.sendMessage( buddyItem.getUserId(), message ) );
  }

  @Override
  public Cookie removeItem(com.tomclaw.openim.main.BuddyItem buddyItem) throws IOException {
    return session.sendPacket( PacketBase.removeBuddyItem( buddyItem.getUserId() ) );
  }

  @Override
  public Cookie renameItem(com.tomclaw.openim.main.BuddyItem buddyItem, String nickName) throws IOException {
    return session.sendPacket( PacketBase.renameBuddyItem( buddyItem.getUserId(), nickName ) );
  }

  @Override
  public Cookie removeGroup(com.tomclaw.openim.main.GroupItem groupItem) throws IOException {
    return session.sendPacket( PacketBase.removeGroup( groupItem.getGroupName() ) );
  }

  @Override
  public Cookie renameGroup(com.tomclaw.openim.main.GroupItem groupItem, String nickName) throws IOException {
    return session.sendPacket( PacketBase.renameGroup( groupItem.getGroupName(), nickName ) );
  }

  @Override
  public void setHostPort(String host) {
    this.host = host.substring( 0, host.indexOf( ':' ) );
    this.port = Integer.parseInt( host.substring( host.indexOf( ':' ) + 1 ) );
  }

  @Override
  public String getHostPort() {
    return host;
  }

  @Override
  public String getType() {
    return "msim";
  }

  @Override
  public void setStatusIndex(int statusIndex) throws IOException {
    this.statusIndex = statusIndex;
  }

  @Override
  public int getStatusIndex() {
    return statusIndex;
  }

  @Override
  public com.tomclaw.openim.main.StatusUtil getStatusUtil() {
    return statusUtil;
  }

  @Override
  public String[][] getRegisterFields(int stepIndex) throws IOException {
    String[][] params = new String[ 6 ][ 2 ];
    params[0] = new String[]{ "label", "¬ведите сервер дл€ регистрации:" };
    params[1] = new String[]{ "field", "localhost" };
    params[2] = new String[]{ "label", "¬ведите желаемый ник:" };
    params[3] = new String[]{ "field", System.getProperty( "user.name" ) };
    params[4] = new String[]{ "label", "¬ведите желаемый пароль:" };
    params[5] = new String[]{ "field", "112" };
    return params;
  }
  
  @Override
  public String[][] getLoginFields() {
    String[][] params = new String[ 6 ][ 2 ];
    params[0] = new String[]{ "label", "¬ведите сервер, где зарегистрирована yчЄтна€ запись:" };
    params[1] = new String[]{ "field", "localhost" };
    params[2] = new String[]{ "label", "¬ведите ник:" };
    params[3] = new String[]{ "field", System.getProperty( "user.name" ) };
    params[4] = new String[]{ "label", "¬ведите пароль:" };
    params[5] = new String[]{ "field", "112" };
    return params;
  }

  @Override
  public int getRegisterStepsCount() {
    return 1;
  }

  @Override
  public void setRegisterStepFields(int stepIndex, String[][] params) throws IOException, InvalidFormException {
    host = params[1][1];
    login = params[3][1];
    password = params[5][1];
    establishConnection();
    session.sendPacket( PacketBase.registerRequest( login, password ) );
    Packet packet = session.parsePacket( true );
    if ( !( packet.type.equals( PacketType.TYPE_SUCCESS ) && packet.content.equals( PacketType.OPER_REGISTER ) ) ) {
      throw new InvalidFormException();
    }
  }
  
  public void setLoginStepFields(String[][] params) {
    host = params[1][1];
    login = params[3][1];
    password = params[5][1];
  }

  @Override
  public void pushQueue(QueueAction queueAction) {
    queue.pushQueueAction( queueAction );
  }

  @Override
  public void runQueue(Cookie cookie, HashMap params) {
    queue.runQueueAction( cookie, params );
  }

  @Override
  public QueueAction popQueue(Cookie cookie) {
    return queue.popQueueAction( cookie );
  }

  @Override
  public GroupItem getGroupInstance(String groupName) {
    return new GroupItem( groupName );
  }

  @Override
  public BuddyItem getBuddyInstance(String userId, String nickName) {
    return new BuddyItem(userId, nickName);
  }
}
