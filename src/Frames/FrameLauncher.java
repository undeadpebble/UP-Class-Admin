package Frames;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FrameLauncher {

	private static String currentOs;
	private static String MAC_OS = "MAC";
	private static String WIN_OS = "WINDOWS";
	private static UIDefaults menuTheme;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {

		// determine which OS the app is running on
		determineOS();
		if (currentOs == MAC_OS) {
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "UP-Admin");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.live-resize", "true");

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
		} else if ((currentOs == WIN_OS) || (currentOs == null)) {

			UIManager.put("nimbusBase", new Color(0x525252));
			UIManager.put("control", new Color(0x949494));
			UIManager.put("nimbusSelectionBackground", new Color(0x171717));
			UIManager.put("Menu.background", new Color(0x2B2B2B));
			UIManager.put("background", new Color(0x171717));
			UIManager.put("DesktopIcon.background", new Color(0x171717));
			UIManager.put("nimbusLightBackground", new Color(0xE3E3E3));

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
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
		if (currentOs.contains("MAC")) {
			currentOs = MAC_OS;
		} else if (currentOs.contains("WINDOWS")) {
			currentOs = WIN_OS;
		} else {
			currentOs = null;
		}
	}
}
