package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;

import org.jdesktop.swingx.JXPanel;

public class BackgroundGradientPanel extends JXPanel {


	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		float[] x = { 0f, .02f, .2f, 1f };
		Color[] c = { new Color(0x333333), new Color(0x080808),
				new Color(0x080808), new Color(0x4F4F4F) };
		LinearGradientPaint p = new LinearGradientPaint(new Point(0, 0),
				new Point(0, getHeight()), x, c);

		Paint oldPaint = g2.getPaint();
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setPaint(oldPaint);

	}
}
