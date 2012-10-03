package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.GraphicsUtilities;

public class HomeGradientPanel extends JXPanel {

	private Color c1;
	private Color c2;
	private int x;
	private int y;
	private float alpha = 1.0f;

	private BufferedImage gradientimage = null;

	public HomeGradientPanel() {
		this.setAlpha(1.0f);
		setOpaque(false);
	}

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

		g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
		Paint oldPaint = g2.getPaint();
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setPaint(oldPaint);

	}

	public void fadeOut() {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(800,
						HomeGradientPanel.this, "alpha", 0.0f);
				animator.setAcceleration(0.2f);
				animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(HomeGradientPanel.this,
						"alpha", 0.0f));
				animator.start();
			}
		});
	}

	public void fadeIn() {
		setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(800,
						HomeGradientPanel.this, "alpha", 1.0f);
				animator.setAcceleration(0.3f);
				animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(HomeGradientPanel.this,
						"alpha", 1.0f));
				animator.start();
			}
		});
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint();
		 if (alpha == 0.0f)
	        	setVisible(false);
	}

	
}
