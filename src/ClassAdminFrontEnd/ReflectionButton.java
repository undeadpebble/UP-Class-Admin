package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingWorker;

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
	private BufferedImage imagelight = null;
	private BufferedImage reflection;
	private boolean entered;

	public ReflectionButton(BufferedImage _image) {
		image = _image;
		super.setOpaque(false);
		setOpaque(false);
		installUI(this);
		entered = false;
	}

	public void installUI(JComponent c) {

		c.addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {

		super.repaint();
		Graphics2D g2 = (Graphics2D) g;
		
		ReflectionRenderer renderer = new ReflectionRenderer();
		renderer.setBlurEnabled(true);
		renderer.setLength(0.5f);
		
		if (entered)
		{
			//black rectangle glitch
			//g2.setComposite(BlendComposite.Add);
			
			//on hover, use gain filter
			
			BufferedImage highlightImage = image;
			GainFilter apply = new GainFilter();
			apply.setGain(0.3f);
			apply.setBias(0.7f);
			highlightImage = apply.filter(highlightImage, null);
			reflection = renderer.appendReflection(highlightImage); 
			
			
			//using a glow filter
			/*
			BufferedImage highlightImage = image;
			GlowFilter apply = new GlowFilter();
			apply.setAmount(0.1f);
			highlightImage = apply.filter(highlightImage, null);
			reflection = renderer.appendReflection(highlightImage); */
		}
		else {
			//else draw normal image
			reflection = renderer.appendReflection(image);
		}
		//draw image
		g2.drawImage(reflection, 0, 0, null);
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
