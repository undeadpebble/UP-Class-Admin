package ClassAdminBackEnd;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ClassAdminFrontEnd.RapidAssessmentCanvas;

public class testMain
{
	
	public static void main(String[] args) {


		

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						
						JFrame frame = new RapidAssessmentCanvas("/ClassAdminFrontEnd/sheep.jpg", new EntityType("test1"));
						
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}


}});
	}}

