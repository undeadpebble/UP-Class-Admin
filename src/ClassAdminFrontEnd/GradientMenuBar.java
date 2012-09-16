package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JMenuBar;

public class GradientMenuBar extends JMenuBar{

	public GradientMenuBar()
	{
		setOpaque(false);
		//this.setFont(new Font());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
        float[] x ={ 0.0f, 0.45f, 0.5f, 1.0f };
		Color[] c = {new Color(0xADADAD), new Color(0x4A4A4A),
				new Color(0x080808), new Color(0x3B3B3B)};
		LinearGradientPaint p = new LinearGradientPaint(new Point(0, 0),
				new Point(0, getHeight()), x, c);
        
        Paint oldPaint = g2.getPaint();
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight()); 
        g2.setPaint(oldPaint); 
        
       
    }
	
}
