/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomclaw.openim.main;

import javax.swing.JTree;

/**
 * ����� ���������� ������ ��� ������ � 
 * �������� �������� � �������� ���������
 * @author solkin
 */
public class BuddyList extends JTree {

  public BuddyList() {
    this.setCellRenderer( new BuddyListRenderer() );
  }
}
