package atmProject;

import java.awt.Desktop;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class depositFrame extends Thread implements ActionListener{
	static int depo;
	File f;
	static String acc;
	static JFrame df = new JFrame();
	static JPanel jp1 = new JPanel();
	static JPanel jp2 = new JPanel();
	static JLabel jl1 = new JLabel("Enter Deposit Amount: ");
	static JTextField depoText = new JTextField(15);
	public JFrame jf;
	static JButton done = new JButton("Done");
	
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	
	int balance;
	public depositFrame(int bal, String accNum, JFrame jf) {
		this.jf = jf;
		acc = accNum;
		balance = bal;			
	}
	
	public void run() {
		jf.setVisible(false);
		df.setLayout(new GridLayout(3, 1));
		df.setSize(410, 360);	
		df.setResizable(false);
		
		jp1.add(jl1);
		jp2.add(depoText);
		df.add(jp1);
		df.add(jp2);
		done.addActionListener(this);

		df.add(done);
		
		df.setVisible(true);
		df.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
			depo = Integer.parseInt(depoText.getText());
			stmt.executeUpdate("UPDATE list SET Balance =  " + (depo + balance) + " WHERE accountNum = " + acc);
		}
		catch(Exception except) {
			System.out.println(except);
		}		
		
		jf.dispose();
		df.dispose();
		
		
        try {
        	String fileName = acc + ".txt";
            File myObj = new File(fileName);
            f = myObj;
            
            if (!f.createNewFile()) {
            	FileWriter fw = new FileWriter(f, true); 
            	fw.write("Current Balance: $" + (depo + balance) +  "\n");
            	fw.close();            
            	
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


