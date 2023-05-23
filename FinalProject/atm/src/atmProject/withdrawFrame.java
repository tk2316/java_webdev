package atmProject;

import java.awt.Desktop;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;


public class withdrawFrame extends Thread implements ActionListener{
	
	static String acc;
	File f;
	static int withdraw;
	static int option;
	static JFrame wf = new JFrame();
	static JPanel jp1 = new JPanel();
	static JPanel jp2 = new JPanel();
	static JLabel jl1 = new JLabel("Enter Withdraw Amount: ");
	static JTextField withdrawText = new JTextField(15);
	public JFrame jf;
	static JButton done = new JButton("Done");
	
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	
	int balance;
	
	public withdrawFrame(int bal, String accNum, JFrame jf) {
		this.jf = jf;
		acc = accNum;
		balance = bal;
	}
	
	public void run() {
		jf.setVisible(false);
		wf.setLayout(new GridLayout(3, 1));
		wf.setSize(410, 360);	
		wf.setResizable(false);
		
		jp1.add(jl1);
		jp2.add(withdrawText);
		wf.add(jp1);
		wf.add(jp2);
		done.addActionListener(this);

		wf.add(done);
		
		wf.setVisible(true);
		wf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","");
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * from list WHERE accountNum = " + acc);
		}
		
		catch(Exception except) {
			System.out.println(except);
		}		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			withdraw = Integer.parseInt(withdrawText.getText());
			if (withdraw <= balance) {
				stmt.executeUpdate("UPDATE list SET Balance =  " + (balance - withdraw) + " WHERE accountNum = " + acc);
			}
			else {
				option = 1;
			}
		}
		catch(Exception except) {
			System.out.println(except);
		}		
		jf.dispose();
		wf.dispose();
		
        try {
        	String fileName = acc + ".txt";
            File myObj = new File(fileName);
            f = myObj;
            
            if (!f.createNewFile()) {
            	FileWriter fw = new FileWriter(f, true); 
            	if((balance - withdraw) >= 0) {
            		fw.write("Current Balance: $" + (balance - withdraw) +  "\n");
            		fw.close();            
            	}
            } 
        } 
        catch (IOException ex) {
        	System.out.println("An error occurred.");
        	ex.printStackTrace();
        }
        
        
		
		try{  
            File file_open = new File(acc + ".txt");   
            if(!Desktop.isDesktopSupported()) {  
                System.out.println("Desktop Support Not Present in the system.");  
                return;  
            }  
            Desktop desktop = Desktop.getDesktop();  
            if(file_open.exists()){
                desktop.open(file_open);    
            }		
		}
        catch(Exception ex)  {  
            ex.printStackTrace();  
        } 
	}
}

