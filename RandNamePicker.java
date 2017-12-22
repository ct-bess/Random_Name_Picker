/*
 *  RandNamePicker.java
 *
 *  By: ct-bess
 *
 *    Takes an input file of random names as strings (separated by \n)
 *    User inputs how many names they want (n)
 *    Program outputs the random name selection to GUI 
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RandNamePicker {

  // --- / GUI \ --- //

  JFrame mainFrame = new JFrame( "Random Name Picker" );
  JTextField usertxtField = new JTextField( 0x40 );
  JTextArea nametxtArea = new JTextArea( 0x10, 0x40 );

  public RandNamePicker() {

    // Prepare GUI
    usertxtField.setEditable( true );
    nametxtArea.setEditable( false );
    mainFrame.getContentPane().add( usertxtField, "North" );
    mainFrame.getContentPane().add( new JScrollPane( nametxtArea ), "Center" );
    mainFrame.pack();

    mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    usertxtField.addActionListener( new ActionListener() {

      public void actionPerformed( ActionEvent e ) {

        pickNames( usertxtField.getText() );
        usertxtField.setText( "" );

      }

    });

  }

  // --- / LOGIC \ --- //

  public void pickNames( String m ) {

    int n; 

    // Check for invalid inputs
    try {

      n = Integer.parseInt( m );

    } 

    catch ( NumberFormatException e ) { 

      // TODO: Make a dedicated error display
      return;

    }

    int randInt, min, max;

    Vector<String> nameVec = new Vector<String>();
   
    // Store input file locally
    try {

      Scanner inFile = new Scanner( new FileInputStream( "NAMES.txt" ) );

      // Store input file
      while ( inFile.hasNextLine() ) {

        nameVec.addElement( inFile.nextLine() );

      }

      inFile.close();

    } 

    catch ( FileNotFoundException fnfe ) { }

    min = 0;
    max = nameVec.size() - 1;

    // Treat large inputs as max inputs
    if ( n > max ) {

      // TODO: Output to that dedicated error display
      n = max;

    }

    nametxtArea.append( " ---------------- \n" );

    // Unrolled once: Java doesn't benefit much from double unrolls

    int i = 0;
    while ( i < n - 1 ) {

      randInt = ThreadLocalRandom.current().nextInt( min, max );
      nametxtArea.append( nameVec.get( randInt ) + "\n" );
      nameVec.removeElementAt( randInt );
      max = max - 1;

      randInt = ThreadLocalRandom.current().nextInt( min, max );
      nametxtArea.append( nameVec.get( randInt ) + "\n" );
      nameVec.removeElementAt( randInt );
      max = max - 1;

      i = i + 2;

    }
 
    // Account for loop unroll edge case
    if ( i == n - 1 ) {

      randInt = ThreadLocalRandom.current().nextInt( min, max );
      nametxtArea.append( nameVec.get( randInt ) + "\n" );
      nameVec.removeElementAt( randInt );
      //max = max - 1; // We don't need this

    }

    nametxtArea.append( " ---------------- \n\n" );

    // Reset i if you use it later

    return;

  } // eo pickNames

  // --- / MAIN \ --- //

  public static void main( String args[] ) {

    RandNamePicker RNP = new RandNamePicker();
    RNP.mainFrame.setVisible( true );

  } // eo main

} // eo RandNamePicker
