/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msim;

/**
 *
 * @author i.solkin
 */
public class GroupItem extends com.tomclaw.openim.main.GroupItem {

    private String groupName;
    
    public GroupItem(String groupName) {
        this.groupName = groupName;
    }
    
    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }
    
}
