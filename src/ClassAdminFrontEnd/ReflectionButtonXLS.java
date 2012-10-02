package ClassAdminFrontEnd;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.jdesktop.swingx.graphics.ReflectionRenderer;

import com.jhlabs.image.GainFilter;

public class ReflectionButtonXLS extends JButton implements MouseListener {

	private BufferedImage image = null;
	private BufferedImage reflection;
	private boolean entered;

	public ReflectionButtonXLS(BufferedImage _image) {
		image = _image;
		super.setOpaque(false);
		setOpaque(false);
		installUI(this);
		entered = false;
		this.setToolTipText("Import");
	}

	public void installUI(JComponent c) {

		c.addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.repaint();
		Graphics2D g2 = (Graphics2D) g;

		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setLength(0.5f);

		if (entered) {
			BufferedImage highlightImage = image;
			GainFilter apply = new GainFilter();
			apply.setGain(0.3f);
			apply.setBias(0.7f);
			highlightImage = apply.filter(highlightImage, null);
			reflection = renderer.appendReflection(highlightImage);
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			this.setCursor(cursor);
		} else {
			reflection = renderer.appendReflection(image);
		}
		g2.drawImage(reflection, 0, 0, null);
		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
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

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
