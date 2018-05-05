import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.FileInputStream;

import com.sun.glass.ui.Size;
import com.sun.org.apache.xml.internal.security.Init;

public class solveByte{
		//private int bitMapBlockSize;
		private int bitMapHeight;
		private int bitMapWidth;
		private int pixelsDigist;
		private int originalBitMapSize;
		private int []colorArr;
		public solveByte(FileInputStream inputStream) {
			int headSize = 54 , size;
			byte[] headBuffer=new byte[headSize];
			try {
				if( (size=inputStream.read(headBuffer))!=-1){
		       //     bitMapBlockSize = (headBuffer[14] & 255) + ((headBuffer[15] & 255) << 8) 
				//			+ ((headBuffer[16] & 255) << 16) + ((headBuffer[17] & 255) << 24) ;
		            bitMapWidth = (headBuffer[18] & 255) + ((headBuffer[19] & 255) << 8) 
		            					+ ((headBuffer[20] & 255) << 16) + ((headBuffer[21] & 255) << 24) ;
		            bitMapHeight = (headBuffer[22] & 255) + ((headBuffer[23] & 255) << 8) 
	    								+ ((headBuffer[24] & 255) << 16) + ((headBuffer[25] & 255) << 24) ;
		            pixelsDigist = (headBuffer[28] & 255 )+ ((headBuffer[29] & 255) << 8);
		            originalBitMapSize = (headBuffer[34] & 255) + ((headBuffer[35] & 255) << 8) 
										+ ((headBuffer[36] & 255) << 16) + ((headBuffer[37] & 255) << 24) ;
		            if(pixelsDigist == 24) {
		            	int restSpace = ((bitMapWidth * 3) % 4) * 3 ;
		            	byte []colorByteArr = new byte[originalBitMapSize];
		            	colorArr = new int [bitMapWidth  * bitMapHeight ];
		            	if( (size=inputStream.read(colorByteArr)) != -1) {
		            		int byteLoop = 0;
		            		for(int row = bitMapHeight - 1 ; row >= 0 ; row --) {
		            			for(int col = 0 ; col < bitMapWidth ; col++ ) {
	            					colorArr[row * bitMapWidth + col ] = (255 << 24) +
		            						(colorByteArr[byteLoop + 2] << 16) +
		            						 (colorByteArr[byteLoop + 1] << 8) +  colorByteArr[byteLoop ] ;
		            				byteLoop += 3;
		            			}
		            			byteLoop += restSpace;
		            		}
		            	}
		            	else {
		            		System.out.println("???" );
		            	}
		            }
		            else {
		            	System.out.println("·Ç24Î»£¿" );
		            }
		        }
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		
	
		
		public Image getImage() {
			return Toolkit.getDefaultToolkit().createImage
					(new MemoryImageSource(bitMapWidth, bitMapHeight, colorArr, 0, bitMapWidth));
		}
		
		
	}