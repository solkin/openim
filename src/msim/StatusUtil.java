/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package msim;

/**
 *
 * @author i.solkin
 */
public class StatusUtil implements com.tomclaw.openim.main.StatusUtil {

    private static int offlineIndex = 0;
    private static int onlineIndex = 1;
    public String[] statuses = new String[]{"offline", "online"};
    public String[] descrptn = new String[]{"Не в сети", "В сети"};

    @Override
    public int getStatusCount() {
        return statuses.length;
    }

    @Override
    public String getStatusDescr(int statusIndex) {
        return descrptn[statusIndex];
    }

    @Override
    public String getStatusValue(int statusIndex) {
        return statuses[statusIndex];
    }

    @Override
    public int getStatusIndex(String statusValue) {
        if (statusValue != null && !statusValue.isEmpty()) {
            for (int c = 0; c < statuses.length; c++) {
                if (statuses[c].equals(statusValue)) {
                    return c;
                }
            }
        }
        return offlineIndex;
    }

    @Override
    public int getOfflineIndex() {
        return offlineIndex;
    }

    @Override
    public int getOnlineIndex() {
        return onlineIndex;
    }
}
