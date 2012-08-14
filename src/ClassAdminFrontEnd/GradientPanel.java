package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.*;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;

public class GradientPanel extends JPanel{
	
	private Color c1;
	private Color c2;
	private int x;
	private int y;
	
	public GradientPanel(Color _c1, Color _c2, int _x, int _y) {
		c1 = _c1;
		c2 = _c2;
		this.setSize(_x, _y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
        GradientPaint p;
        p = new GradientPaint(0, 0, c2, 0, getHeight(), c1, true);
        
        Paint oldPaint = g2.getPaint();
        g2.setPaint(p);
        g2.fillRect(0, 0, getWidth(), getHeight()); 
        g2.setPaint(oldPaint); 
       
    }
}
