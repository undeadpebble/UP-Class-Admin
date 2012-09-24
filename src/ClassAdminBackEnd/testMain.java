package ClassAdminBackEnd;

import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.FileHandler;


import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import org.tmatesoft.sqljet.core.SqlJetException;


import ClassAdminFrontEnd.EntityTypePanel;
import ClassAdminFrontEnd.FrmTable;
import ClassAdminFrontEnd.RapidAssessmentCanvas;
import ClassAdminFrontEnd.TreeView;

import Frames.Frame;


import Rule.frmRule;

import ClassAdminFrontEnd.RapidAssessmentCanvas.MyMarkPoint;



import jxl.*;
public class testMain
{
	
	public static void main(String[] args) {
		ClassAdminBackEnd.FileHandler fh = ClassAdminBackEnd.FileHandler.get();

		

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						
						JFrame frame = new RapidAssessmentCanvas("/ClassAdminFrontEnd/sheep.jpg", new SuperEntity(new EntityType("test1"), 1.0));
						
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			});


	}

}

