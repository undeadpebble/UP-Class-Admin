package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXPanel;

public class ThreeStopGradientPanel extends JXPanel {

	private Color c1;
	private Color c2;
	private Color c3;
	private JPanel contentPane;
	private Image intermediateImage;

	public ThreeStopGradientPanel(Color _c1, Color _c2, Color _c3, JPanel c) {
		c1 = _c1;
		c2 = _c2;
		c3 = _c3;
		contentPane = c;
	}

	public void createGradient(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		float[] x = { 0.0f, .2f, 1.0f };
		Color[] c = { c1, c2, c3 };
		LinearGradientPaint p = new LinearGradientPaint(new Point(0, 0),
				new Point(0, getHeight()), x, c);
		Paint oldPaint = g2.getPaint();
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setPaint(oldPaint);
	}

	@Override
	public void paintComponent(Graphics g) {
		if ((intermediateImage == null)
				|| (intermediateImage.getWidth(null) != contentPane.getWidth())) {
			GraphicsConfiguration gc = getGraphicsConfiguration();
			intermediateImage = gc.createCompatibleImage(
					contentPane.getWidth(), this.getHeight(),
					Transparency.BITMASK);
			Graphics2D gImg = (Graphics2D) intermediateImage.getGraphics();
			gImg.setComposite(AlphaComposite.Src);
			gImg.setColor(new Color(0, 0, 0, 0));
			gImg.fillRect(0, 0, contentPane.getWidth(), this.getHeight());
			// long startTime = System.nanoTime();
			createGradient(gImg);
			g.drawImage(intermediateImage, 0, 0, null);
			// long endTime = System.nanoTime();
			// long totalTime = (endTime - startTime) / 1000000;
			// System.out.println("Direct: " + ((float)totalTime/100));

			gImg.dispose();
		} else {
			// long startTime = System.nanoTime();
			g.drawImage(intermediateImage, 0, 0, null);
			// long endTime = System.nanoTime();
			// long totalTime = (endTime - startTime) / 1000000;
			// System.out.println("Intermediate: " + ((float)totalTime/100));
		}
	}

	public void rerenderBackground() {
		intermediateImage = null;
		repaint();
	}
}
