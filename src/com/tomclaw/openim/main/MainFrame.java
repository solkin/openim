package com.tomclaw.openim.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class MainFrame extends javax.swing.JFrame {

  private DefaultMutableTreeNode rootNode;
  private javax.swing.tree.DefaultTreeModel model;
  public ArrayList<AccountRoot> accountRoots = new ArrayList();
  public EventHandler handler;
  public BuddyItem selectedBuddyItem;
  public GroupItem selectedGroupItem;

  /** Creates new form MainFrame */
  public MainFrame() {
    initComponents();
    setLocationRelativeTo( null );
    handler = new EventHandler();
  }

  public void loadAccounts() {
    rootNode = new DefaultMutableTreeNode( "Accounts" );
    model = new javax.swing.tree.DefaultTreeModel( rootNode );
    accountRoots.clear();
    String[] groups = Storage.accounts.listGroups();
    for ( int c = 0; c < groups.length; c++ ) {
      try {
        String type = Storage.accounts.getValue( groups[c], "type" );
        AccountRoot accountRoot = ( AccountRoot ) Class.forName( type.concat( ".AccountRoot" ) ).newInstance();
        StatusUtil statusUtil = accountRoot.getStatusUtil();
        HashMap params = new HashMap();
        String[] values = Storage.accounts.listItems( groups[c] );
        for ( int i = 0; i < values.length; i++ ) {
          params.put( values[i], Storage.accounts.getValue( groups[c], values[i] ) );
        }
        accountRoot.setParams( params );
        StatusButton statusButton = new StatusButton( accountRoot, statusUtil );
        accountRoot.setStatusButton( statusButton );
        accountRoot.setHandler( handler );
        accountRoot.initBuddyPopup();
        accountRoot.initGroupPopup();
        statusBar.add( statusButton );
        accountRoots.add( accountRoot );
        rootNode.add( accountRoot );
      } catch ( Throwable ex ) {
        ex.printStackTrace();
      }
    }
    jTree1.setCellRenderer( new BuddyListRenderer() );
    jTree1.setModel( model );
    updateBuddyList();
  }

  public AccountRoot getAccountRoot( String type, String login ) {
    for ( int c = 0; c < accountRoots.size(); c++ ) {
      if ( accountRoots.get( c ).getType().equals( type )
              && accountRoots.get( c ).getLogin().equals( login ) ) {
        return accountRoots.get( c );
      }
    }
    return null;
  }

  public void removeAccountRoot( AccountRoot accountRoot ) {
    if ( accountRoot != null ) {
      try {
        accountRoot.disconnect();
      } catch ( Throwable ex ) {
        System.out.println( "Couldn't disconnect" );
      }
      try {
        rootNode.remove( accountRoot );
      } catch ( Throwable ex ) {
        System.out.println( "Couldn't remove root node" );
      }
      try {
        for ( int c = 0; c < statusBar.getComponentCount(); c++ ) {
          if ( ( ( StatusButton ) statusBar.getComponentAtIndex( c ) ).getAccountRoot().equals( accountRoot ) ) {
            statusBar.remove( c );
            return;
          }
        }
      } catch ( Throwable ex ) {
        System.out.println( "Couldn't remove status button" );
      }
      updateBuddyList();
      updateStatusBar();
    }
  }

  public void appendAccountRoot( AccountRoot accountRoot ) {
    try {
      String groupName = String.valueOf( System.currentTimeMillis() );
      StatusUtil statusUtil = accountRoot.getStatusUtil();
      HashMap params = accountRoot.getParams();
      Iterator keys = ( params.keySet() ).iterator();
      Storage.accounts.addGroup( groupName );
      Storage.accounts.addItem( groupName, "type", accountRoot.getType() );
      while ( keys.hasNext() ) {
        String itemName = ( String ) keys.next();
        String value = ( String ) params.get( itemName );
        Storage.accounts.addItem( groupName, itemName, value );
      }
      Storage.saveAccounts();
      accountRoot.setParams( params );
      StatusButton statusButton = new StatusButton( accountRoot, statusUtil );
      accountRoot.setStatusButton( statusButton );
      accountRoot.setHandler( handler );
      accountRoot.initBuddyPopup();
      accountRoot.initGroupPopup();
      statusBar.add( statusButton );
      accountRoots.add( accountRoot );
      rootNode.add( accountRoot );
      updateBuddyList();
      updateStatusBar();
    } catch ( Throwable ex ) {
      ex.printStackTrace();
    }
  }

  public void updateBuddyList() {
    javax.swing.SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        MainFrame.this.jTree1.updateUI();
      }
    } );
  }

  public void updateStatusBar() {
    javax.swing.SwingUtilities.invokeLater( new Runnable() {
      @Override
      public void run() {
        MainFrame.this.statusBar.updateUI();
      }
    } );
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolBar = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        statusBar = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        toolBar.setFloatable(false);
        toolBar.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/go-home.png"))); // NOI18N
        jButton1.setToolTipText("������� ����");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton1);
        toolBar.add(jSeparator1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/folder-new.png"))); // NOI18N
        jButton2.setToolTipText("������� ������");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton2);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/contact-new.png"))); // NOI18N
        jButton3.setToolTipText("�������� �������");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton3);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/edit-find.png"))); // NOI18N
        jButton4.setToolTipText("����� ��������");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolBar.add(jButton4);
        toolBar.add(jSeparator2);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/system-run.png"))); // NOI18N
        jButton5.setToolTipText("���������");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        toolBar.add(jButton5);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/mail-send-receive.png"))); // NOI18N
        jButton6.setToolTipText("������");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        toolBar.add(jButton6);

        statusBar.setFloatable(false);
        statusBar.setRollover(true);
        statusBar.setPreferredSize(new java.awt.Dimension(196, 32));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setRootVisible(false);
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
  AccountsDialog accountsDialog = new AccountsDialog( this, true );
  accountsDialog.setVisible( true );
}//GEN-LAST:event_jButton5ActionPerformed

  private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    jTree1.updateUI();
  }//GEN-LAST:event_jButton6ActionPerformed

  private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
  }//GEN-LAST:event_jTree1MouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    public javax.swing.JTree jTree1;
    private javax.swing.JToolBar statusBar;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables
}
