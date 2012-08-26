package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FrameLauncher {
	
	
	private static String currentOs;
	private static String MAC_OS = "MAC";
	private static String WIN_OS = "WINDOWS";

	public static void main(String[] args) {
		
		//determine which OS the app is running on
		determineOS();
		if (currentOs == MAC_OS)
		{
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "UP-Admin");
			
            try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if ((currentOs == WIN_OS) || (currentOs ==  null))
		{
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
						.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						UIManager.put("nimbusBase", new Color(0x7A7A7A));
						UIManager.put("nimbusSelectionBackground", new Color(
								0x171717));
						UIManager.put("Menu.background", new Color(0x2B2B2B));
						UIManager.put("background", new Color(0x171717));
						UIManager
								.put("DesktopIcon.background", new Color(0x171717));
						UIManager.put("nimbusLightBackground", new Color(0xE3E3E3));
						
						break;
					}
				}
			} catch (ClassNotFoundException ex) {
				java.util.logging.Logger.getLogger(Frame.class.getName()).log(
						java.util.logging.Level.SEVERE, null, ex);
			} catch (InstantiationException ex) {
				java.util.logging.Logger.getLogger(Frame.class.getName()).log(
						java.util.logging.Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				java.util.logging.Logger.getLogger(Frame.class.getName()).log(
						java.util.logging.Level.SEVERE, null, ex);
			} catch (javax.swing.UnsupportedLookAndFeelException ex) {
				java.util.logging.Logger.getLogger(Frame.class.getName()).log(
						java.util.logging.Level.SEVERE, null, ex);
			} 
		}
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

public static void determineOS() {
	currentOs = System.getProperty("os.name").toUpperCase();
    if(currentOs.contains("MAC")){
        currentOs = MAC_OS;
    }
    else if( currentOs.contains("WINDOWS") ){
        currentOs = WIN_OS;
    }
    else{
        currentOs = null;
    }
}
}
