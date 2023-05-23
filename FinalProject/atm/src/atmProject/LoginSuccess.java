package atmProject;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  
import java.io.IOException;  

public class LoginSuccess extends Thread implements ActionListener {
	static File f;
	static  JFrame loggedFrame = new JFrame();
	static JButton balanceButton = new JButton("Check Balance");
	static JButton depositButton = new JButton("Deposit");
	static JButton withdrawlButton = new JButton("Withdraw");
	static JButton exitButton = new JButton("Exit");
	
	static String accNum;
	static int bal;
	
	static ResultSet rs;
	static balanceFrame bf = new balanceFrame(accNum, bal, loggedFrame);

	static depositFrame df;
	static withdrawFrame wf;
	
	public LoginSuccess(String acc) {
		accNum = acc;
	}
	
    public void run() {	
        try {
        	String fileName = accNum + ".txt";
            File myObj = new File(fileName);
            f = myObj;
            
            if (f.createNewFile()) {
            	FileWriter fw = new FileWriter(f, true); 
            	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
            	LocalDateTime now = LocalDateTime.now();
            	fw.write("Logged Into Account " + accNum + " at " + dtf.format(now) + "\n");
            	fw.close();     

            } 
            else {
            	FileWriter fw = new FileWriter(f, true); 
            	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
            	LocalDateTime now = LocalDateTime.now();
            	fw.write("Logged Into Account " + accNum + " at " + dtf.format(now) + "\n");
            	fw.close();            
            	}
        } 
        catch (IOException e) {
        	System.out.println("An error occurred.");
        	e.printStackTrace();
        }
    	

		
    	loggedFrame.setLayout(new GridLayout(5, 1));
    	loggedFrame.setSize(410, 360);	
    	loggedFrame.setResizable(false);

    	loggedFrame.add(balanceButton);
    	balanceButton.addActionListener(this);
    	
    	loggedFrame.add(depositButton);
    	depositButton.addActionListener(this);
    			
    	loggedFrame.add(withdrawlButton);
    	withdrawlButton.addActionListener(this);
    	
    	loggedFrame.add(exitButton);
    	exitButton.addActionListener(this);
    	    
    	loggedFrame.setVisible(true);
    	loggedFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	

    }
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JButton source = (JButton)e.getSource();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from list WHERE accountNum = " + accNum);
			if(rs.next()){
				if(source.getText() == "Check Balance") {
				   bal = rs.getInt(10);
				   bf = new balanceFrame(accNum, bal, loggedFrame);
				   bf.run();
				}
				if(source.getText() == "Deposit") {
					bal = rs.getInt(10);
					df = new depositFrame(bal, accNum, loggedFrame);
					df.run();
				}
				if(source.getText() == "Withdraw") {
					bal = rs.getInt(10);
					wf = new withdrawFrame(bal, accNum, loggedFrame);
					wf.run();
				}
				if(source.getText() == "Exit") {
					loggedFrame.dispose();
				}
			}
			
		}
		catch(Exception except) {
			System.out.println(except);
		}		
	}

}

