import java.awt.Image;


import imagereader.IImageProcessor;

public class ImplementImageProcessor implements IImageProcessor {

	@Override
	public Image showChanelB(Image imgae) {
		getImageByChanel gImageByChanel = new getImageByChanel();
		return gImageByChanel.getImage(imgae , 3);
	}

	@Override
	public Image showChanelG(Image imgae) {
		getImageByChanel gImageByChanel = new getImageByChanel();
		return gImageByChanel.getImage(imgae , 2);
	}

	@Override
	public Image showChanelR(Image imgae) {
		getImageByChanel gImageByChanel = new getImageByChanel();
		return gImageByChanel.getImage(imgae , 1);
	}

	@Override
	public Image showGray(Image imgae) {
		getImageByChanel gImageByChanel = new getImageByChanel();
		return gImageByChanel.getImage(imgae , 0);
	}

}
