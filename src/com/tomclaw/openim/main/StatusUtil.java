package com.tomclaw.openim.main;

/**
 *
 * @author i.solkin
 */
public interface StatusUtil {
    
    public int getStatusCount();
    public String getStatusDescr(int statusIndex);
    public String getStatusValue(int statusIndex);
    public int getStatusIndex(String value);
    public int getOfflineIndex();
    public int getOnlineIndex();
}
