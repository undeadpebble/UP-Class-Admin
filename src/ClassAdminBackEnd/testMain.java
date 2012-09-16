package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.tmatesoft.sqljet.core.SqlJetException;

import ClassAdminFrontEnd.Frame;

import ClassAdminFrontEnd.EntityTypePanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.RapidAssessmentCanvas;
import ClassAdminFrontEnd.TreeView;
import ClassAdminFrontEnd.RapidAssessmentCanvas.MyMarkPoint;

import jxl.*;
public class testMain
{
	
	public static void main(String[] args) {
		ClassAdminBackEnd.FileHandler fh = ClassAdminBackEnd.FileHandler.get();
		

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						BufferedImage background = ImageIO.read(getClass().getResource("/ClassAdminFrontEnd/sheep.jpg"));
						JFrame frame = new RapidAssessmentCanvas(background, new SuperEntity(new EntityType("test1"), 1.0));
						
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		
		
	}

}
