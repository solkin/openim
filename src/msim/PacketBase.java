package msim;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class PacketBase {

  public static Packet registerRequest( String userName, String password ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_REGISTER, userName.concat( "|" ).concat( password ) );
    return packet;
  }

  public static Packet authRequest( String userName, String password ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_AUTH, userName.concat( "|" ).concat( password ) );
    return packet;
  }

  public static Packet sendMessage( String userName, String message ) {
    Packet packet = new Packet( userName, PacketType.OPER_MESSAGE, message );
    return packet;
  }

  public static Packet requestBuddyList() {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_LIST, "" );
    return packet;
  }

  public static Packet addBuddyItem( String userName, String nickName, String groupName ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_ADD, userName.concat( "|" ).concat( nickName ).concat( "|" ).concat( groupName ) );
    return packet;
  }

  public static Packet removeBuddyItem( String userName ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_REMOVE, userName );
    return packet;
  }

  public static Packet renameBuddyItem( String userName, String nickName ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_RENAME, userName.concat( "|" ).concat( nickName ) );
    return packet;
  }

  public static Packet requestGroupsList() {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_GROUPS_LIST, "" );
    return packet;
  }

  public static Packet removeGroup( String groupName ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_GROUPS_REMOVE, groupName );
    return packet;
  }

  public static Packet renameGroup( String groupName, String newGroupName ) {
    Packet packet = new Packet( PacketType.KEYWORD_SERVER, PacketType.OPER_CONTACTS_GROUPS_RENAME, groupName.concat( "|" ).concat( newGroupName ) );
    return packet;
  }
}
