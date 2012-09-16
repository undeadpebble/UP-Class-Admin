package ClassAdminFrontEnd;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.JComponent;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.ReflectionRenderer;

import com.jhlabs.image.GainFilter;
import com.jhlabs.image.GlowFilter;

public class ImagePanel extends JXPanel {

	private BufferedImage image = null;

	ImagePanel(BufferedImage _image) {
		image = _image;
		setOpaque(false);
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(image, 0, 0, null);
		super.repaint();

		g2.dispose();
	}

	

}
