package msim;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class BuddyItem extends com.tomclaw.openim.main.BuddyItem {

  private String nickName = "";
  private String userId = "";
  private int statusId = 0;

  public BuddyItem( String userId, String nickName ) {
    this.userId = userId;
    this.nickName = nickName;
  }

  @Override
  public void setNickName( String nickName ) {
    this.nickName = nickName;
  }

  @Override
  public String getNickName() {
    return nickName;
  }

  @Override
  public void setUserId( String userId ) {
    this.userId = userId;
  }

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public void setStatusId( int statusId ) {
    this.statusId = statusId;
  }

  @Override
  public int getStatusId() {
    return statusId;
  }
}
