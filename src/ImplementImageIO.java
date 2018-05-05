import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.management.ImmutableDescriptor;

import org.omg.CORBA.portable.InputStream;

import com.sun.javafx.sg.prism.web.NGWebView;

import imagereader.IImageIO;

public class ImplementImageIO implements IImageIO {

	@Override
	public Image myRead(String pathname) throws IOException {
		System.out.println(pathname);
		int size = 0 , count = 0;
		Image result = null;
		try {
			FileInputStream inputStream = new FileInputStream(pathname);
			solveByte sByte = new solveByte(inputStream);
			result = sByte.getImage();
			inputStream.close();
		}catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
	@Override
	public Image myWrite(Image image, String path) throws IOException {
		// TODO Auto-generated method stub
		String fileName = path + ".bmp";
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(image, 0, 0, null);
	    bGr.dispose();
	    System.out.println(path);
	    
		boolean temp = ImageIO.write(bimage,"bmp",new File(fileName));
		 
		System.out.println(temp);
		return image;
	}
	
	

}


