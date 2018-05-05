import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import com.sun.rowset.internal.Row;

public class getImageByChanel {
	public Image getImage(Image image ,int sign) {
		if(sign < 0 || sign > 3) {
			return null;
		}
		else {
			int width = image.getWidth(null) , height = image.getHeight(null);
			
			int []colorArr = new int[width * height];
			BufferedImage bufferedImage = getBufferedImage(image);
			
			for (int i = 0; i < width; i++) {  
	            for (int j = 0; j < height; j++) {  
	                colorArr[i + j * width] = bufferedImage.getRGB(i, j); // 下面三行代码将一个数字转换为RGB数字  
	                int blueNum = bufferedImage.getRGB(i, j) & 255;
	                int greenNum = (bufferedImage.getRGB(i, j) & (255 << 8 ) ) >> 8;
	                int redNum = (bufferedImage.getRGB(i, j) & (255 << 16 ) ) >> 16;
	                switch (sign) {
					case 3:
						colorArr[i + j * width] = (255 << 24) | blueNum;
						break;
					case 2:
						colorArr[i + j * width] = (255 << 24) | (greenNum << 8);
						break;
					case 1:
						colorArr[i + j * width] = (255 << 24) | (redNum << 16);
						break;
						
					default:   // case 0
						int grey = (int)(redNum * 0.299 + greenNum  * 0.587 + blueNum  * 0.114);
						colorArr[i + j * width] = (255 << 24) | ( grey << 16)
											| (  grey << 8) | ( grey  );
						break;
					}
	            }  
	        }  
			return Toolkit.getDefaultToolkit().createImage
					(new MemoryImageSource(width, height, colorArr, 0, width));
		}
	}
		
	private BufferedImage getBufferedImage(Image img) {
		if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
