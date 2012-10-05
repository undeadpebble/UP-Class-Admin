package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.graphics.ShadowRenderer;
import org.jdesktop.swingx.image.GaussianBlurFilter;

public class ShadowPanel extends JXPanel {

	private BufferedImage shadow;
	private BufferedImage panel;
	private int newPosX;
	private int newPosY;
	private int oldPosX;
	private int oldPosY;
	private boolean shown;

	public ShadowPanel(int oldX, int oldY, int newX, int newY) {
		setOpaque(false);
		newPosX = newX;
		newPosY = newY;
		oldPosX = oldX;
		oldPosY = oldY;
	}

	@Override
	protected void paintComponent(Graphics g) {
		int x = 5;
		int y = 5;
		int w = getWidth() - 20;
		int h = getHeight() - 20;
		int arc = 30;

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// draw shadow first
		if (shadow != null) {
			int xOffset = (shadow.getWidth() - w) / 2;
			int yOffset = (shadow.getHeight() - h) / 2;
			g2.drawImage(shadow, x - xOffset, y - yOffset, null);
		}

		// then draw panel
		g2.setColor(new Color(0, 0, 0, 220));
		g2.fillRoundRect(x, y, w, h, arc, arc);

		g2.setStroke(new BasicStroke(3f));
		g2.setColor(Color.WHITE);

		// g2.setComposite(AlphaComposite.SrcOver.derive(.06f));
		// g2.drawRoundRect(x, y, w, h, arc, arc);

		g2.dispose();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);

		int w = getWidth() - 20;
		int h = getHeight() - 20;
		int arc = 30;
		int shadowSize = 20;

		shadow = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
		Graphics2D g2 = shadow.createGraphics();
		g2.setColor(Color.black);
		g2.fillRoundRect(0, 0, w, h, arc, arc);
		g2.dispose();

		ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f,
				Color.BLACK);
		shadow = renderer.createShadow(shadow);

		g2.drawImage(shadow, 0, 0, null);

		g2 = shadow.createGraphics();
		// The color does not matter, red is used for debugging
		g2.setColor(Color.RED);
		g2.setComposite(AlphaComposite.Clear);
		g2.fillRoundRect(shadowSize, shadowSize, w, h, arc, arc);

		g2.dispose();
	}

	public void moveIn() {

		setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(200,
						ShadowPanel.this, "location", new Point(newPosX,
								newPosY));
				// animator.setAcceleration(0.2f);
				// animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(ShadowPanel.this,
						"location", new Point(newPosX, newPosY)));
				animator.start();
			}
		});
		shown = true;
	}

	public void moveOut() {

		setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(200,
						ShadowPanel.this, "location", new Point(oldPosX,
								oldPosY));
				// animator.setAcceleration(0.0f);
				// animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(ShadowPanel.this,
						"location", new Point(oldPosX, oldPosY)));
				animator.start();
			}

		});
		shown = false;
	}

	public boolean isShown() {
		return shown;
	}

	public void setShown(boolean b) {
		shown = b;
	}

	public void setNewX(int x) {
		newPosX = x;
	}

	public void setNewY(int y) {
		newPosY = y;
	}

	public void setOldX(int x) {
		oldPosX = x;
	}

	public void setOldY(int y) {
		oldPosY = y;
	}

}