package ClassAdminFrontEnd;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.ReflectionRenderer;

public class ReflectionImagePanel extends JXPanel {

	private BufferedImage image = null;

	public ReflectionImagePanel(BufferedImage _image) {
		image = _image;
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage reflection;
		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setBlurEnabled(true);
		renderer.setLength(0.6f);
		reflection = renderer.appendReflection(image);
		// draw image
		g2.drawImage(reflection, 0, 0, null);
		super.repaint();

		g2.dispose();
	}

}
