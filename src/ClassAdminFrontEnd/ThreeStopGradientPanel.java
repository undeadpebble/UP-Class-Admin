package ClassAdminFrontEnd;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import org.jdesktop.swingx.JXPanel;

public class ThreeStopGradientPanel extends JXPanel {

	private Color c1;
	private Color c2;
	private Color c3;

	public ThreeStopGradientPanel(Color _c1, Color _c2, Color _c3) {
		c1 = _c1;
		c2 = _c2;
		c3 = _c3;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		float[] x = { 0.0f, .2f, 1.0f};
		Color[] c = { c1, c2, c3 };
		LinearGradientPaint p = new LinearGradientPaint(new Point(0, 0),
				new Point(0, getHeight()), x, c);
		Paint oldPaint = g2.getPaint();
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setPaint(oldPaint);

	}
}
