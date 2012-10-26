package msim;

/**
 *
 * @author i.solkin
 */
public final class PacketType {

  /** Types **/
  public static final String TYPE_MSG = "message";
  public static final String TYPE_CONTACTLIST = "contacts-list";
  public static final String TYPE_WTF = "wtf";
  public static final String TYPE_SUCCESS = "success";
  public static final String TYPE_FAIL = "fail";
  public static final String TYPE_PING = "ping";
  public static final String TYPE_PRESENCE = "presence";
  /** Operations **/ 
  public static final String OPER_REGISTER = "register";
  public static final String OPER_AUTH = "auth";
  public static final String OPER_MESSAGE = "message";
  public static final String OPER_CONTACTS_LIST = "contacts-list";
  public static final String OPER_CONTACTS_ADD = "contacts-add";
  public static final String OPER_CONTACTS_REMOVE = "contacts-remove";
  public static final String OPER_CONTACTS_RENAME = "contacts-rename";
  public static final String OPER_CONTACTS_GROUPS_LIST = "contacts-groups-list";
  public static final String OPER_CONTACTS_GROUPS_REMOVE = "contacts-groups-remove";
  public static final String OPER_CONTACTS_GROUPS_RENAME = "contacts-groups-rename";
  /** Keywords **/
  public static final String KEYWORD_SERVER = "SERVER";
}
