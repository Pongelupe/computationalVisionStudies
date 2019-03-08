package reconhecimento;

import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class Captura {

	private static final String RESORCE_FOLDER = "src/main/resources/";

	public static void main(String[] args) throws Exception {
		OpenCVFrameConverter.ToMat converter = new ToMat();
		OpenCVFrameGrabber webcam = new OpenCVFrameGrabber(0);
		webcam.start();

		CascadeClassifier faceClassifier = new CascadeClassifier(RESORCE_FOLDER + "haarcascade-frontalface-alt.xml");

		CanvasFrame canvasFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / webcam.getGamma());
		canvasFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame frameCaptured;
		Mat corlorfulImage = new Mat();

		while ((frameCaptured = webcam.grab()) != null) {
			corlorfulImage = converter.convert(frameCaptured);
			Mat grayImage = new Mat();
			cvtColor(corlorfulImage, grayImage, opencv_imgproc.COLOR_BGR2GRAY);
			RectVector facesDetected = new RectVector();
			faceClassifier.detectMultiScale(grayImage, facesDetected, 1.1, 1, 0, new Size(150, 150),
					new Size(500, 500));
			for (int i = 0; i < facesDetected.size(); i++) {
				Rect faceDetected = facesDetected.get(0);
				rectangle(corlorfulImage, faceDetected, new Scalar(0, 255, 255, 0));
			}
			if (canvasFrame.isVisible()) {
				canvasFrame.showImage(frameCaptured);
			}
		}
		faceClassifier.close();
		corlorfulImage.close();
		webcam.close();

	}

}
