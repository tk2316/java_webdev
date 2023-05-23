package atmProject;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;


public class startFrame implements ActionListener {
	
	static JFrame frame = new JFrame();

    public void show() {
    	BufferedImage img = null;
		try {
			img = ImageIO.read(new URL("https://www.schudio.com/wp-content/uploads/2016/08/welcome-page-blog-header.jpg"));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
    	ImageIcon icon = new ImageIcon(img);
    	frame.setLayout(new FlowLayout());
    	frame.setSize(1000, 750);
    	frame.setResizable(false);

    	JLabel lbl = new JLabel();
    	lbl.setIcon(icon);
    	frame.add(lbl);
    	JButton signUp= new JButton("Sign Up");
    	signUp.addActionListener(this);
    	JButton signIn = new JButton("Sign In");
    	signIn.addActionListener(this);
    	frame.add(signUp);
    	frame.add(signIn);

    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton)e.getSource();
        if (source.getText() == "Sign Up") {
        	System.out.println("Sign Up");
        	frame.dispose();
        	SignUpFrame signUp = new SignUpFrame();
        	signUp.run();
        }
        if (source.getText() == "Sign In") {
        	System.out.println("Sign In");
        	frame.dispose();
        	SignInFrame signIn = new SignInFrame();
        	signIn.run();

        }
    }

}

