package atmProject;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUpFrame extends Thread implements ActionListener {

		
	static JFrame suFrame = new JFrame();
   	static JTextField f_Name_text = new JTextField(10);
   	static JTextField l_Name_text  = new JTextField(10);
   	static JTextField Email_text = new JTextField("e.g. bob@java.edu", 12);
   	static JTextField DOB_text = new JTextField("e.g 20230530", 15);
   	static JTextField address_text = new JTextField(20);
   	static JTextField city_text = new JTextField(15);
   	static JTextField pin_text = new JTextField("Set 4 digit pin number", 10);
   	static JTextField zipCode_text = new JTextField(7);
   	static JTextField accountNum_text = new JTextField("Set 8 digit account number", 15); 


    @Override
    public void run() {
        	
        suFrame.setLayout(new GridLayout(11, 1));
       	suFrame.setSize(410, 360);
       	suFrame.setResizable(false);
       	
       	JPanel jp1 = new JPanel();
       	jp1.setLayout(new FlowLayout());
       	suFrame.add(jp1);
        	
       	JPanel jp2 = new JPanel();
       	jp2.setLayout(new FlowLayout());
       	suFrame.add(jp2);
        	
       	JPanel jp3 = new JPanel();
       	jp3.setLayout(new FlowLayout());
       	suFrame.add(jp3);
        	
       	JPanel jp4 = new JPanel();
       	jp4.setLayout(new FlowLayout());
       	suFrame.add(jp4);
        	
       	JPanel jp5 = new JPanel();
       	jp5.setLayout(new FlowLayout());
       	suFrame.add(jp5);
        	
       	JPanel jp6 = new JPanel();
       	jp6.setLayout(new FlowLayout());
       	suFrame.add(jp6);
        	
       	JPanel jp7 = new JPanel();
       	jp7.setLayout(new FlowLayout());
       	suFrame.add(jp7);
        	
       	JPanel jp8 = new JPanel();
       	jp8.setLayout(new FlowLayout());
       	suFrame.add(jp8);
        	
      	JPanel jp9 = new JPanel();
       	jp8.setLayout(new FlowLayout());
       	suFrame.add(jp9);
       	
    	JPanel jp10 = new JPanel();
       	jp10.setLayout(new FlowLayout());
       	suFrame.add(jp10);
       	
       	JLabel f_Name_jl = new JLabel("First Name: ");
       	jp1.add(f_Name_jl);
       	jp1.add(f_Name_text);
        	
       	JLabel l_Name_jl = new JLabel("Last Name: ");
       	jp2.add(l_Name_jl);
       	jp2.add(l_Name_text);

       	JLabel Email_jl = new JLabel("Email: ");
       	jp3.add(Email_jl);
       	jp3.add(Email_text);

        JLabel DOB_jl = new JLabel("Date of Birth: ");
       	jp4.add(DOB_jl);
       	jp4.add(DOB_text);

       	JLabel address_jl = new JLabel("Address: ");
       	jp5.add(address_jl);
       	jp5.add(address_text);

       	JLabel city_jl = new JLabel("City: ");
        jp6.add(city_jl);
       	jp6.add(city_text);
       	
       	JLabel zipCode_jl = new JLabel("ZipCode: ");
       	jp7.add(zipCode_jl);
       	jp7.add(zipCode_text);

       	JLabel accountNum_jl = new JLabel("Account Number: ");
       	jp8.add(accountNum_jl);
       	jp8.add(accountNum_text);

       	JLabel pin_jl = new JLabel("Pin: ");
       	jp9.add(pin_jl);
       	jp9.add(pin_text);

        JButton finish = new JButton("Create");
        finish.addActionListener(this);
        jp10.add(finish);
        
        suFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        suFrame.setVisible(true);        
        
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        String f_name = f_Name_text.getText();
        String l_name = l_Name_text.getText();
        String email = Email_text.getText();
        Integer DOB = Integer.parseInt(DOB_text.getText());
        String address = address_text.getText();
        String city = city_text.getText();
        Integer zip = Integer.parseInt(zipCode_text.getText());
        Integer accountNum = Integer.parseInt(accountNum_text.getText());
        Integer pin = Integer.parseInt(pin_text.getText());
        Integer balance = 0;
        
        if(registerSQL(f_name,l_name,email,DOB,address,city,zip,accountNum,pin,balance) == 1) {     
        	suFrame.dispose(); 
        	SignInFrame SI = new SignInFrame();
        	SI.run();
        }        
        
        else{
        	suFrame.setVisible(false);
			JPanel jp11 = new JPanel();
			JLabel invalidInp = new JLabel("AccNum already exists: ");
			jp11.add(invalidInp);
			suFrame.add(jp11);
        	System.out.println("Account Number already exists!");
            suFrame.setVisible(true);        

        }
	}
	
	public int registerSQL(String f_name, String l_name, String email, Integer DOB, String address, String city, Integer zip, Integer accountNum, Integer pin, Integer balance) {
		int returnVal = 0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","");
			Statement stmt = con.createStatement();
			int count = 0;
			
			ResultSet rs = stmt.executeQuery("select * from list WHERE accountNum = " + accountNum);
			while(rs.next()) {
				count ++;
			}
			
			if(count > 0) {
				returnVal = 0;
			}
			else {
				stmt.executeUpdate("INSERT INTO list(f_name, l_name, email, DOB, address, city, zip, accountNum, pin, balance) VALUES(\'" + f_name + "\', \'" + l_name + "\', \'" + email + "\', " + DOB + ", \'" + address + "\', \'" + city + "\', " + zip + ", " + accountNum + ", " + pin + ", " + balance + ")");
				returnVal = 1;
			}
		}
			
		catch(Exception e) {
			System.out.println(e);
		}
		return returnVal;
	}

}
