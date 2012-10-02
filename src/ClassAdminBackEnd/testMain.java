package ClassAdminBackEnd;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.sun.xml.internal.stream.Entity;

import ClassAdminFrontEnd.RapidAssessmentCanvas;

public class testMain
{
	
	public static void main(String[] args) {


		

			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						EntityType j = new EntityType();
						new SuperEntity(j, 0);
						EntityType k =new EntityType("test",j, false, null, 1.0);
						k.populateTreeWithEntities();
						JFrame frame = new RapidAssessmentCanvas("/ClassAdminFrontEnd/sheep.jpg", k);
						
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}


}});
	}}

