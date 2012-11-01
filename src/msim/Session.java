package msim;

import com.tomclaw.bingear.BinGear;
import com.tomclaw.openim.main.Connection;
import com.tomclaw.openim.main.Cookie;
import com.tomclaw.openim.main.GroupItem;
import com.tomclaw.openim.main.Handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Solkin
 */
public class Session {

    public AccountRoot accountRoot;
    public Connection connection;
    public boolean isAlive = false;
    private long time;
    public Handler handler;
    private Thread thread;
    public int pingDelay = 20000;

    public Session(AccountRoot accountRoot, Connection connection, Handler eventHandler) {
        this.accountRoot = accountRoot;
        this.connection = connection;
        this.handler = eventHandler;
    }

    public Packet parsePacket(boolean isBlock) throws IOException {
        Packet packet = null;
        if (connection.getInputStream().available() > 0 || isBlock) {
            packet = new Packet();
            packet.read(connection);
            /**
             * Вывод пакета в консоль
             */
            packet.dump(System.out);
        }
        return packet;
    }

    public void stop() {
        isAlive = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void start() {
        isAlive = true;
        thread = new Thread() {
            @Override
            public void run() {
                time = System.currentTimeMillis();
                while (isAlive) {
                    try {
                        sleep(500);
                        Packet packet = parsePacket(false);
                        if (packet != null) {
                            /**
                             * Время последней пакетной активности
                             */
                            time = System.currentTimeMillis();
                            /**
                             * Отправка пакета на обработку
                             */
                            redirectPacket(packet);
                        }
                        if (System.currentTimeMillis() - time >= pingDelay) {
                            sendPacket(new Packet(PacketType.KEYWORD_SERVER, PacketType.TYPE_PING, ""));
                            time = System.currentTimeMillis();
                        }
                    } catch (Throwable ex) {
                        Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        thread.start();
    }

    public void redirectPacket(final Packet packet) {
        if (packet.type.equals(PacketType.TYPE_PRESENCE)) {
            handler.receivePresence(accountRoot, packet.to, 
                    accountRoot.getStatusUtil().getStatusIndex(packet.content));
        } else if (packet.type.equals(PacketType.TYPE_MSG)) {
            handler.receiveMessage(accountRoot, packet.to, packet.content);
        } else if (packet.type.equals(PacketType.TYPE_CONTACTLIST)) {
            BinGear binGear = new BinGear();
            try {
                binGear.importFromIni(packet.content);
            } catch (Throwable ex) {
            }
            ArrayList<GroupItem> items = new ArrayList();
            String[] groupItems = binGear.listGroups();
            for (int c = 0; c < groupItems.length; c++) {
                GroupItem groupItem = new msim.GroupItem(groupItems[c]);
                groupItem.setAccountRoot(accountRoot);
                try {
                    String[] list = binGear.listItems(groupItems[c]);
                    for (int i = 0; i < list.length; i++) {
                        BuddyItem buddyItem = new msim.BuddyItem(binGear.getValue(groupItems[c], list[i]), list[i]);
                        buddyItem.setAccountRoot(accountRoot);
                        buddyItem.setParentGroup(groupItem);
                        groupItem.addItem(buddyItem);
                    }
                    items.add(groupItem);
                } catch (Throwable ex) {
                }
            }
            handler.receiveBuddyList(accountRoot, items);
        } else if (packet.type.equals(PacketType.TYPE_SUCCESS)) {
            handler.operationSuccess(accountRoot, new Cookie(packet.content), null);
        } else if (packet.type.equals(PacketType.TYPE_FAIL)) {
            handler.operationFail(accountRoot, new Cookie(packet.content));
        }
    }

    public Cookie sendPacket(Packet packet) throws IOException {
        Cookie cookie = new Cookie(packet.type);
        packet.send(connection);
        packet.dump(System.out);
        time = System.currentTimeMillis();
        return cookie;
    }
}
