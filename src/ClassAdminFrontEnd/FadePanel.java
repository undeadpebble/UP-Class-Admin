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

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;

public class FadePanel extends JXPanel {

	private int x;
	private int y;
	private float alpha = 0.0f;

	public FadePanel(Boolean backPaint) {
		setOpaque(false);
		this.setAlpha(0.0f);
		if (backPaint) {
			GlossPainter gloss = new GlossPainter();

			MattePainter matte = new MattePainter(new Color(0x242424));
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
	}

	public void fadeIn() {

		setVisible(true);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Animator animator = PropertySetter.createAnimator(400,
						FadePanel.this, "alpha", 1.0f);
				animator.setAcceleration(0.2f);
				animator.setDeceleration(0.3f);
				animator.addTarget(new PropertySetter(FadePanel.this, "alpha",
						1.0f));
				animator.start();
			}
		});
	}
}
