package ClassAdminBackEnd;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

public class SaveableImage {
	byte[] imageByteArray;
	
	
	/**
	 * @param image
	 * @throws IOException
	 * Convert the input BufferedImage to a Byte[] and store
	 */
	public void setImage(BufferedImage image) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		imageByteArray = baos.toByteArray();
		baos.close();
	}
	
	/**
	 * @return
	 * @throws IOException
	 * convert the stored byte[] to a bufferedImage and return it
	 */
	public BufferedImage getImage() throws IOException{
		ImageReader reader = ImageIO.getImageReadersByFormatName("jpg").next();
		reader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(imageByteArray)));
		Image image = reader.read(0, reader.getDefaultReadParam());
		BufferedImage im = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		im.createGraphics().drawImage(image, null, null);
		return im;
	}
}
