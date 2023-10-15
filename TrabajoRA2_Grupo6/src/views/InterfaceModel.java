package views;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class InterfaceModel {
	
	public static void FrameModel(JFrame jf, String title) {
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setTitle(title);
		Image icon1 = Toolkit.getDefaultToolkit().getImage("resources/icon/logo.JPG");
		jf.setIconImage(icon1);
		
	}
}
