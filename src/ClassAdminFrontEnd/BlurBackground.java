package ClassAdminFrontEnd;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;


import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.image.GaussianBlurFilter;

public class BlurBackground extends JXPanel{
    private BufferedImage blurBuffer;
    private BufferedImage backBuffer;
    private float alpha = 0.0f;

    public BlurBackground(JFrame frame) {
        setLayout(null);
        
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        alpha = 0.0f;
        // Should also disable key events...
        addMouseListener(new MouseAdapter() { });
    }
    
    public void createBlur() {
        JRootPane root = SwingUtilities.getRootPane(this);
        blurBuffer = GraphicsUtilities.createCompatibleImage(
            getWidth(), getHeight());
        Graphics2D g2 = blurBuffer.createGraphics();
        root.paint(g2);
        g2.dispose();

        backBuffer = blurBuffer;

        blurBuffer = GraphicsUtilities.createThumbnailFast(
            blurBuffer, getWidth() / 2);
        blurBuffer = new GaussianBlurFilter(3).filter(blurBuffer, null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        if (isVisible() && blurBuffer != null) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(backBuffer, 0, 0, null);

            g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g2.drawImage(blurBuffer, 0, 0, getWidth(), getHeight(), null);
            
            g2.dispose();
        }
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
        createBlur();
        		
        setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Animator animator = PropertySetter.createAnimator(
                    400, BlurBackground.this, "alpha", 1.0f);
                animator.setAcceleration(0.2f);
                animator.setDeceleration(0.3f);
                animator.addTarget(
                    new PropertySetter(BlurBackground.this, "alpha", 1.0f));
                animator.start();
            }
        });
    }
    
    public void fadeOut() {
         		
        setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Animator animator = PropertySetter.createAnimator(
                    400, BlurBackground.this, "alpha", 0.0f);
                animator.setAcceleration(0.2f);
                animator.setDeceleration(0.3f);
                animator.addTarget(
                    new PropertySetter(BlurBackground.this, "alpha", 0.0f));
                animator.start();
            }
        });
        
       
    }
    

}
