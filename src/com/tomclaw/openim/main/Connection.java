package com.tomclaw.openim.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class Connection {

  private Socket socket;
  private SocketAddress socketAddress;
  private DataInputStream inputStream;
  private DataOutputStream outputStream;
  public String host;
  public int port;

  public void connect( String host, int port ) throws IOException {
    this.host = host;
    this.port = port;
    socket = new Socket();
    socketAddress = new java.net.InetSocketAddress( host, port );
    socket.connect( socketAddress );
    inputStream = new DataInputStream( socket.getInputStream() );
    outputStream = new DataOutputStream( socket.getOutputStream() );
  }

  public void disconnect() throws IOException {
    inputStream.close();
    outputStream.close();
    socket.close();
  }

  public DataInputStream getInputStream() {
    return inputStream;
  }

  public DataOutputStream getOutputStream() {
    return outputStream;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }
}
