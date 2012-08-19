package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;

public class FadePanel extends JXPanel {

	private float alpha = 0.0f;
	private int acceleration;
	private int deceleration;

	public FadePanel(Boolean backPaint, int acc, int dec) {
		setOpaque(false);
		this.setAlpha(0.0f);
		acceleration = acc;
		deceleration = dec;
		if (backPaint) {
			GlossPainter gloss = new GlossPainter();
			//  gloss.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.5f));
			MattePainter matte = new MattePainter(new Color(0x171717));
			setBackgroundPainter(new CompoundPainter(matte, gloss));
		}
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
		Paint oldPaint = g2.getPaint();
		g2.setPaint(oldPaint);

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

	public void fadeIn() {

		setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(acceleration,
						FadePanel.this, "alpha", 1.0f);
				animator.setAcceleration(0.2f);
				animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(FadePanel.this, "alpha",
						1.0f));
				animator.start();
			}
		});
	}
	
	public void fadeOut() {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(deceleration,
						FadePanel.this, "alpha", 0.0f);
				animator.setAcceleration(0.2f);
				animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(FadePanel.this,
						"alpha", 0.0f));
				animator.start();
			}
		});
	}
}
