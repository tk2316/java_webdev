package atmProject;

import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Desktop;  
import java.io.*;  
import java.io.FileWriter;   // Import the FileWriter class
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  
import java.io.IOException;  


public class balanceFrame extends Thread implements ActionListener{
	static JFrame bf = new JFrame();
	int bal;
	File f;
	String acc;
	public JFrame jf;
	JLabel currBalance; 
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();

	public balanceFrame(String accNum, int balance, JFrame jf) {
		bal = balance;
		this.jf = jf;
		acc = accNum;
	}
	
	public void run() {
		jf.setVisible(false);
		bf.setLayout(new GridLayout(10, 1));
		bf.setSize(410, 360);	
		bf.setResizable(false);
		
		JButton done = new JButton("Done");
		currBalance = new JLabel("Your current balance: " + bal);
		jp1.add(currBalance);
		bf.add(jp1);
		
		done.addActionListener(this);
		jp2.add(done);
		bf.add(jp2);
		
		bf.setVisible(true);
		bf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jf.dispose();
		bf.dispose();


        try {
        	String fileName = acc + ".txt";
            File myObj = new File(fileName);
            f = myObj;
            
            if (!f.createNewFile()) {
            	FileWriter fw = new FileWriter(f, true); 
            	fw.write("Current Balance: $" + bal +  "\n");
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
