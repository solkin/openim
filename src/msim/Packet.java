package msim;

import com.tomclaw.openim.main.Connection;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class Packet {

  protected String to;
  protected String type;
  protected String content;

  public Packet() {
    to = "";
    type = "";
    content = "";
  }

  public Packet( String to, String type, String content ) {
    this.to = to;
    this.type = type;
    this.content = content;
  }

  public void send( Connection connection ) throws IOException {
    connection.getOutputStream().writeUTF( to );
    connection.getOutputStream().writeUTF( type );
    connection.getOutputStream().writeUTF( content );
    connection.getOutputStream().flush();
  }

  public void read( Connection connection ) throws IOException {
    to = connection.getInputStream().readUTF();
    type = connection.getInputStream().readUTF();
    content = connection.getInputStream().readUTF();
  }

  public void dump( OutputStream outputStream ) throws IOException {
    String dump = System.currentTimeMillis() + " [" + to + "] type: '" + type + "' content: <" + content + ">\n";
    outputStream.write( dump.getBytes() );
    outputStream.flush();

  }
}
