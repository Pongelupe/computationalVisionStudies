package deteccao;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import nu.pattern.OpenCV;

public class TesteOpenCV {

	private static final String RESORCE_FOLDER = "src/main/resources/";

	public static void main(String... args) {
		OpenCV.loadLocally();
		System.out.println(Core.VERSION);

		Mat imagemColorida = Imgcodecs.imread(RESORCE_FOLDER + "opencv-java.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
		MatUtils matUtils = new MatUtils();
		BufferedImage img = matUtils.convertMatToImage(imagemColorida);
		matUtils.mostraImagem(img);
	}

}
