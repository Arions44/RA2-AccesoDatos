package views;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class InterfaceModel {
	
	public static void FrameModel(JFrame jf, String tittle) {
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setTitle(tittle);
		Image icon1 = Toolkit.getDefaultToolkit().getImage("resources/icon/logo.JPG");
		jf.setIconImage(icon1);
		
	}
	
	public static void ButtonModel(JButton jb) {
		
	}
}
