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

public class ImagePanel extends JXPanel implements MouseListener {

	private BufferedImage image = null;
	private BufferedImage highlightimage;
	private Boolean highlight = false;
	private Boolean entered = false;

	ImagePanel(BufferedImage _image, Boolean _highlight) {
		image = _image;
		highlight = _highlight;
		setOpaque(false);
		installUI(this);
	}

	public void installUI(JComponent c) {

		c.addMouseListener(this);
	}

	public void createHighlight(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	
		BufferedImage helpimage = image;
		GainFilter apply = new GainFilter();
		apply.setGain(0.6f);
		apply.setBias(0.7f);
		highlightimage = apply.filter(helpimage, null);
		
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(cursor);
	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (highlight) {
			if (entered) {
				if (highlightimage == null) {
					createHighlight(g2);
					g2.drawImage(highlightimage, 0, 0, null);
				} else {
					g2.drawImage(highlightimage, 0, 0, null);
				}
			}
			else {
				g2.drawImage(image, 0, 0, null);
			}
		}
		else {
			g2.drawImage(image, 0, 0, null);
		}
		
		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JComponent c = (JComponent) arg0.getComponent();
		entered = true;
		c.repaint();

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JComponent c = (JComponent) arg0.getComponent();
		entered = false;
		c.repaint();

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
