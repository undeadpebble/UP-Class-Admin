package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.graphics.BlendComposite;
import org.jdesktop.swingx.graphics.ReflectionRenderer;
import org.jdesktop.swingx.painter.GlossPainter;

import com.jhlabs.image.ApplyMaskFilter;
import com.jhlabs.image.AverageFilter;
import com.jhlabs.image.ContrastFilter;
import com.jhlabs.image.GainFilter;
import com.jhlabs.image.GlowFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.UnsharpFilter;

public class ReflectionButton extends JButton implements MouseListener {

	private BufferedImage image = null;

	private BufferedImage reflection;
	private BufferedImage highlightreflection;
	private boolean entered;
	Image intermediateImage;

	public ReflectionButton(BufferedImage _image) {
		image = _image;
		super.setOpaque(false);
		setOpaque(false);
		installUI(this);
		entered = false;
		this.setBorder(new EmptyBorder(0, 0, 0, 0));
	}

	public void createReflectionButton(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setBlurEnabled(true);
		renderer.setLength(0.5f);

		reflection = renderer.appendReflection(image);

		g2.dispose();
	}

	public void createReflectionHighlight(Graphics g) {
		BufferedImage highlightImage = image;
		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setBlurEnabled(true);
		renderer.setLength(0.5f);
		GainFilter apply = new GainFilter();
		apply.setGain(0.3f);
		apply.setBias(0.7f);
		highlightImage = apply.filter(highlightImage, null);
		highlightreflection = renderer.appendReflection(highlightImage);

		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(cursor);
	}

	public void installUI(JComponent c) {

		c.addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (!entered) {
			if (reflection == null) {
				long startTime = System.nanoTime();
				createReflectionButton(g2);
				g2.drawImage(reflection, 0, 0, null);
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Direct: " + ((float) totalTime / 100));
			} else {
				long startTime = System.nanoTime();
				g2.drawImage(reflection, 0, 0, null);
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out
						.println("Intermediate: " + ((float) totalTime / 100));
			}
		} else {
			if (highlightreflection == null) {
				long startTime = System.nanoTime();
				createReflectionHighlight(g2);
				g2.drawImage(highlightreflection, 0, 0, null);
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out.println("Direct: " + ((float) totalTime / 100));
			} else {
				long startTime = System.nanoTime();
				g2.drawImage(highlightreflection, 0, 0, null);
				long endTime = System.nanoTime();
				long totalTime = (endTime - startTime) / 1000000;
				System.out
						.println("Intermediate: " + ((float) totalTime / 100));
			}
		}

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
