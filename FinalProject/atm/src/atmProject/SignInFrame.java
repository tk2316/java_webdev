package atmProject;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignInFrame extends Thread implements ActionListener {
	static JFrame siFrame = new JFrame();
	static JTextField acc_num = new JTextField(8);
	static JTextField pin_num = new JTextField(4);
	static JPanel jp5 = new JPanel();
	static JLabel failedAttemp = new JLabel("Login Attempt Failed. Try again! ");


    public void run() {			
    	siFrame.setLayout(new GridLayout(6, 1));
    	siFrame.setSize(410, 360);	
    	siFrame.setResizable(false);
    	
       	JPanel jp1 = new JPanel();       	
       	jp1.setLayout(new FlowLayout());
       	JLabel jl1 = new JLabel("Account Number");
       	jp1.add(jl1);
       	siFrame.add(jp1);

       	JPanel jp2 = new JPanel();       	
       	jp2.setLayout(new FlowLayout());
       	jp2.add(acc_num);
       	siFrame.add(jp2);

       	JPanel jp3 = new JPanel();       	
       	jp3.setLayout(new FlowLayout());
       	JLabel jl2 = new JLabel("Pin Number");
       	jp3.add(jl2);
       	siFrame.add(jp3);
       	
       	JPanel jp4 = new JPanel();       	
       	jp4.setLayout(new FlowLayout());
       	jp4.add(pin_num);
       	siFrame.add(jp4);
       	
       	JButton login = new JButton("Login");
       	login.addActionListener(this);
       	siFrame.add(login);
       	
    	siFrame.setVisible(true);	
    	siFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			int count = 0;
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from list WHERE accountNum = " + acc_num.getText() + " AND pin = " + pin_num.getText());
			
			while(rs.next()) {
				count ++;
			}
			if(count > 0) {
				siFrame.dispose();
				LoginSuccess ls = new LoginSuccess(acc_num.getText());
				ls.run();
			}
			else {
				siFrame.setVisible(false);
				jp5.add(failedAttemp);
				siFrame.add(jp5);
				siFrame.setVisible(true);
				
			}
		} 
		catch(Exception except) {
			System.out.println(except);
		}
		
	}

}
