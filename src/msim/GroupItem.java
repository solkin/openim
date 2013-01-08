package msim;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class GroupItem extends com.tomclaw.openim.main.GroupItem {

  private String groupName;

  public GroupItem( String groupName ) {
    this.groupName = groupName;
  }

  @Override
  public void setGroupName( String groupName ) {
    this.groupName = groupName;
  }

  @Override
  public String getGroupName() {
    return groupName;
  }
}
