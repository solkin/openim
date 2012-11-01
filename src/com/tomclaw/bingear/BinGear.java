package com.tomclaw.bingear;

import com.tomclaw.utils.ArrayUtil;
import com.tomclaw.utils.StringUtil;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2012
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class BinGear {

  public HashMap hashtable;

  public BinGear() {
    hashtable = new HashMap();
  }

  public void addGroup(String groupName) throws IncorrectValueException {
    if ( groupName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    hashtable.put( groupName, new HashMap() );
  }

  public void addItem(String groupName, String itemName, String value) throws GroupNotFoundException, IncorrectValueException {
    if ( groupName == null || itemName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      ( ( HashMap ) hashtable.get( groupName ) ).put( itemName, value );
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
    }
  }

  public HashMap getGroup(String groupName) throws IncorrectValueException, GroupNotFoundException {
    if ( groupName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      return ( HashMap ) hashtable.get( groupName );
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
    }
  }

  public String getValue(String groupName, String itemName) throws GroupNotFoundException, IncorrectValueException {
    if ( groupName == null || itemName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      return ( String ) ( ( HashMap ) hashtable.get( groupName ) ).get( itemName );
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
    }
  }

  public String getValue(String groupName, String itemName, boolean isFullCompare) throws GroupNotFoundException, IncorrectValueException {
    if ( groupName == null || itemName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    if ( isFullCompare || hashtable.containsKey( groupName ) ) {
      return getValue( groupName, itemName );
    } else {
      try {
        Iterator groupKeys = (hashtable.keySet()).iterator();
        String tempName;
        for ( ; groupKeys.hasNext(); ) {
          tempName = ( String ) groupKeys.next();
          if ( tempName.startsWith( groupName ) || groupName.startsWith( tempName ) ) {
            return ( String ) ( ( HashMap ) hashtable.get( tempName ) ).get( itemName );
          }
        }
      } catch ( NullPointerException ex1 ) {
        throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
      }
    }
    return null;
  }

  public void renameGroup(String groupOldName, String groupNewName) throws IncorrectValueException, GroupNotFoundException {
    if ( groupOldName == null || groupNewName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      hashtable.put( groupNewName, hashtable.get( groupOldName ) );
      hashtable.remove( groupOldName );
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupOldName.concat( " is not exist" ) );
    }
  }

  public String[] listGroups() {
    String[] groups = new String[ hashtable.size() ];
    Iterator groupKeys = (hashtable.keySet()).iterator();
    String groupName;
    for ( int c = 0; groupKeys.hasNext(); c++ ) {
      groupName = ( String ) groupKeys.next();
      groups[c] = groupName;
    }
    return groups;
  }

  public String[] listItems(String groupName) throws IncorrectValueException, GroupNotFoundException {
    if ( groupName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      String[] items = new String[ ( ( HashMap ) hashtable.get( groupName ) ).size() ];
      Iterator itemKeys = (( ( HashMap ) hashtable.get( groupName ) ).keySet()).iterator();
      for ( int c = 0; itemKeys.hasNext(); c++ ) {
        groupName = ( String ) itemKeys.next();
        items[c] = groupName;
      }
      return items;
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
    }
  }

  public String[] listItems(String groupName, boolean isFullCompare) throws IncorrectValueException, GroupNotFoundException {
    if ( groupName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    if ( isFullCompare || hashtable.containsKey( groupName ) ) {
      return listItems( groupName );
    } else {
      try {
        Iterator groupKeys = hashtable.keySet().iterator();
        String tempName;
        for ( ; groupKeys.hasNext(); ) {
          tempName = ( String ) groupKeys.next();
          if ( tempName.startsWith( groupName ) || groupName.startsWith( tempName ) ) {
            String[] items = new String[ ( ( HashMap ) hashtable.get( tempName ) ).size() ];
            Iterator itemKeys = ( ( HashMap ) hashtable.get( tempName ) ).keySet().iterator();
            for ( int c = 0; itemKeys.hasNext(); c++ ) {
              items[c] = ( String ) itemKeys.next();
            }
            return items;
          }
        }
        return null;
      } catch ( NullPointerException ex1 ) {
        throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
      }
    }
  }

  public void renameItem(String groupName, String itemOldName, String itemNewName) throws IncorrectValueException, GroupNotFoundException {
    if ( groupName == null || itemOldName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      ( ( HashMap ) hashtable.get( groupName ) ).put( itemNewName, ( ( HashMap ) hashtable.get( groupName ) ).get( itemOldName ) );
      ( ( HashMap ) hashtable.get( groupName ) ).remove( itemOldName );
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
    }
  }

  public void setValue(String groupName, String itemName, String value) throws GroupNotFoundException, IncorrectValueException {
    if ( groupName == null || itemName == null ) {
      throw new IncorrectValueException( "nulltype is not allowed here as patameter" );
    }
    try {
      ( ( HashMap ) hashtable.get( groupName ) ).put( itemName, value );
    } catch ( NullPointerException ex1 ) {
      throw new GroupNotFoundException( groupName.concat( " is not exist" ) );
    }
  }

  public void removeGroup(String groupName) {
    hashtable.remove( groupName );
  }

  public void removeItem(String groupName, String itemName) {
    ( ( HashMap ) hashtable.get( groupName ) ).remove( itemName );
  }

  public void exportToIni(OutputStream outputStream) throws IOException {
    Iterator groupKeys = hashtable.keySet().iterator();
    Iterator itemKeys;
    String itemName;
    String groupName;
    for ( ; groupKeys.hasNext(); ) {
      groupName = ( String ) groupKeys.next();
      outputStream.write( StringUtil.stringToByteArray( "[".concat( groupName ).concat( "]\n" ), true ) );
      itemKeys = ( ( HashMap ) hashtable.get( groupName ) ).keySet().iterator();
      for ( ; itemKeys.hasNext(); ) {
        itemName = ( String ) itemKeys.next();
        outputStream.write( StringUtil.stringToByteArray( itemName.concat( 
                "=" ).concat( ( String ) ( ( HashMap ) hashtable.get( 
                groupName ) ).get( itemName ) ).concat( "\n" ), true ) );
      }
    }
    outputStream.flush();
  }

  public void saveToDat(DataOutputStream outputStream) throws IOException {
    /**
     *   [int] groupsCount
     * г [int] groupNameLength
     * | [String] groupName
     * | [int] itemsCount
     * | [int] itemNameLength
     * | [String] itemName
     * | [int] valueLength
     * L [String] value
     * г ----
     * | ...
     * L ----
     * ...
     */
    Iterator groupKeys = hashtable.keySet().iterator();
    Iterator itemKeys;
    String itemName;
    String groupName;
    String value;
    /** Groups count **/
    outputStream.writeChar( hashtable.size() );
    for ( ; groupKeys.hasNext(); ) {
      groupName = ( String ) groupKeys.next();
      /** Group name length **/
      //outputStream.writeChar(groupName.length());
      /** Group name **/
      outputStream.writeUTF( groupName );
      itemKeys = ( ( HashMap ) hashtable.get( groupName ) ).keySet().iterator();
      /** Items count **/
      outputStream.writeChar( ( ( HashMap ) hashtable.get( groupName ) ).size() );
      for ( ; itemKeys.hasNext(); ) {
        itemName = ( String ) itemKeys.next();
        /** Item name length **/
        //outputStream.writeChar(itemName.length());
        /** Item name **/
        outputStream.writeUTF( itemName );
        value = ( String ) ( ( HashMap ) hashtable.get( groupName ) ).get( itemName );
        /** Value length **/
        //outputStream.writeChar(value.length());
        /** Value **/
        outputStream.writeUTF( value );
      }
    }
    outputStream.flush();
  }

  public void readFromDat(DataInputStream inputStream) throws IOException, IncorrectValueException, GroupNotFoundException, EOFException {
    /**
     *   [int] groupsCount
     * г [int] groupNameLength
     * | [String] groupName
     * | [int] itemsCount
     * | [int] itemNameLength
     * | [String] itemName
     * | [int] valueLength
     * L [String] value
     * г ----
     * | ...
     * L ----
     * ...
     */
    hashtable.clear();
    int groupCount = inputStream.readChar();
    String groupName;
    String itemName;
    String value;
    for ( int c = 0; c < groupCount; c++ ) {
      groupName = inputStream.readUTF();
      // System.out.println("[".concat(groupName).concat("]"));
      addGroup( groupName );
      int itemsCount = inputStream.readChar();
      for ( int i = 0; i < itemsCount; i++ ) {
        itemName = inputStream.readUTF();
        value = inputStream.readUTF();
        // System.out.println(itemName.concat("=").concat(value));
        addItem( groupName, itemName, value );
      }
    }
  }

  public void importFromIni(String data) throws IOException, IncorrectValueException, GroupNotFoundException, Throwable {
    hashtable.clear();
    char ch;
    String prevHeader = null;
    boolean isFirstIndex = true;
    String buffer = new String();
    for ( int c = 0; c < data.length(); c++ ) {
      ch = data.charAt( c );
      if ( ch == 13 ) {
        continue;
      }
      if ( ch == 10 ) {
        if ( buffer.length() <= 1 ) {
          continue;
        }
        if ( buffer.charAt( 0 ) == '[' && buffer.charAt( buffer.length() - 1 ) == ']' ) {
          prevHeader = buffer.substring( 1, buffer.length() - 1 );
          addGroup( prevHeader );
        } else {
          int equivIndex;
          if ( isFirstIndex ) {
            equivIndex = buffer.indexOf( '=' );
          } else {
            equivIndex = buffer.lastIndexOf( '=' );
          }
          if ( equivIndex > 0 ) {
            addItem( prevHeader,
                    buffer.substring( 0, equivIndex ),
                    buffer.substring( equivIndex + 1, buffer.length() ) );
          }
        }
        buffer = "";
        continue;
      }
      buffer += ch;
    }
  }

  public void importFromIni(DataInputStream inputStream) throws IOException, IncorrectValueException, GroupNotFoundException, EOFException, Throwable {
    hashtable.clear();
    byte ch;
    String prevHeader = null;
    boolean isFirstIndex = true;
    ArrayUtil buffer = new ArrayUtil();
    while ( inputStream.available() > 0 && ( ch = inputStream.readByte() ) != -1 ) {
      if ( ch == 13 ) {
        continue;
      }
      if ( ch == 10 ) {
        if ( buffer.length() <= 1 ) {
          continue;
        }
        if ( buffer.byteString[0] == '[' && buffer.byteString[buffer.length() - 1] == ']' ) {
          prevHeader = StringUtil.byteArrayToString( buffer.subarray( 1, buffer.length() - 1 ), true );
          // System.out.println("[".concat(prevHeader).concat("]"));
          addGroup( prevHeader );
        } else {
          int equivIndex;
          if ( isFirstIndex ) {
            equivIndex = buffer.indexOf( '=' );
          } else {
            equivIndex = buffer.lastIndexOf( '=' );
          }
          if ( equivIndex > 0 ) {
            // System.out.println((StringUtil.byteArrayToString(buffer.substring(0, equivIndex), true)).concat("=").concat(StringUtil.byteArrayToString(buffer.substring(equivIndex + 1, buffer.length()), true)));
            addItem( prevHeader,
                    StringUtil.byteArrayToString( buffer.subarray( 0, equivIndex ), true ),
                    StringUtil.byteArrayToString( buffer.subarray( equivIndex + 1, buffer.length() ), true ) );
          }
        }
        buffer.clear();
        continue;
      }
      buffer.append( ch );
    }
  }
}
