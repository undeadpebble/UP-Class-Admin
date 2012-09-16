package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXPanel;

public class BackgroundGradientPanel extends JLayeredPane {

	Image intermediateImage;
	JPanel contentPane;

	public BackgroundGradientPanel(JPanel c) {
		contentPane = c;
	}

	public void renderGradient(Graphics g) {
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

	@Override
	public void paintComponent(Graphics g) {
		if ((intermediateImage == null)
				|| (intermediateImage.getWidth(null) != contentPane.getWidth())
				|| (intermediateImage.getWidth(null) != contentPane.getWidth())) {
			GraphicsConfiguration gc = getGraphicsConfiguration();
			intermediateImage = gc.createCompatibleImage(
					contentPane.getWidth(), contentPane.getHeight(),
					Transparency.BITMASK);
			Graphics2D gImg = (Graphics2D) intermediateImage.getGraphics();
			gImg.setComposite(AlphaComposite.Src);
			gImg.setColor(new Color(0, 0, 0, 0));
			gImg.fillRect(0, 0, contentPane.getWidth(), contentPane.getHeight());
			// long startTime = System.nanoTime();
			renderGradient(gImg);
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
