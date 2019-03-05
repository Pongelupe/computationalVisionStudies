package deteccao;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import nu.pattern.OpenCV;

public class Exemplo3 {

	private static final String RESORCE_FOLDER = "src/main/resources/";

	public static void main(String[] args) {
		OpenCV.loadLocally();

		// It works for all images inside pessoas folder on the resources
		Mat imagemColorida = Imgcodecs.imread(RESORCE_FOLDER + "pessoas/beatles.jpg");
		Mat imagemCinza = new Mat();
		Imgproc.cvtColor(imagemColorida, imagemCinza, Imgproc.COLOR_BGR2GRAY);

		CascadeClassifier classifier = new CascadeClassifier(
				RESORCE_FOLDER + "cascades/haarcascade_frontalface_default.xml");

		MatOfRect facesDetected = new MatOfRect();
		classifier.detectMultiScale(imagemCinza, facesDetected);// default settings

		System.out.println(facesDetected.toList().size() + " faces detected!");
		facesDetected.toList().forEach(rect -> {
			System.out.println(rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
			Imgproc.rectangle(imagemColorida, new Point(rect.x, rect.y),
					new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 2);
		});

		MatOfRect eyesDetected = new MatOfRect();
		classifier = new CascadeClassifier(RESORCE_FOLDER + "cascades/haarcascade_eye.xml");
		classifier.detectMultiScale(imagemCinza, eyesDetected, 1.2, 2, 0, new Size(5, 5), new Size(30, 30));

		System.out.println(eyesDetected.toList().size() + " eyes detected!");
		eyesDetected.toList().forEach(rect -> {
			System.out.println(rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
			Imgproc.rectangle(imagemColorida, new Point(rect.x, rect.y),
					new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 255), 2);
		});

		new MatUtils().mostraImagem(imagemColorida);
	}

}
