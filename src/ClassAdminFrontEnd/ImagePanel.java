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

public class ImagePanel extends JXPanel implements MouseListener{

	private BufferedImage image = null;
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
	
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if ((entered) && (highlight))
		{
			float[] factors = new float[]  {1.2f, 1.2f, 1.2f, 1.2f};
			float[] offsets = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
			RescaleOp op = new RescaleOp(factors,offsets,null);
			BufferedImage brighter = op.filter(image, null);
			g2.drawImage(brighter, 0, 0, null);
			
			Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
			this.setCursor(cursor);
			/*
			BufferedImage highlightImage = image;
			GainFilter apply = new GainFilter();
			apply.setGain(0.6f);
			apply.setBias(0.8f);
			highlightImage = apply.filter(highlightImage, null);
			g2.drawImage(highlightImage, 0, 0, null); */
		}
		else
			g2.drawImage(image, 0, 0, null);
		super.repaint();

		g2.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		entered = true;
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		entered = false;
		
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
