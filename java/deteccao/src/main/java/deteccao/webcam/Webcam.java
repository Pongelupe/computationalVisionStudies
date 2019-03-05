package deteccao.webcam;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import deteccao.MatUtils;
import nu.pattern.OpenCV;

public class Webcam extends JFrame {

	private static final String RESORCE_FOLDER = "src/main/resources/";
	private static final long serialVersionUID = 8095549155776594549L;
	private JPanel jPanel;

	public Webcam() {
		initComponents();
	}

	private void initComponents() {
		jPanel = new JPanel(true);
		this.add(jPanel);
	}

	public static void main(String[] args) {
		OpenCV.loadLocally();
		Webcam webcam = new Webcam();
		webcam.setDefaultCloseOperation(EXIT_ON_CLOSE);
		webcam.setSize(600, 500);
		webcam.setVisible(true);
		webcam.playVideo();
	}

	private void playVideo() {
		Mat video = new Mat();
		VideoCapture capture = new VideoCapture(0);
		if (capture.isOpened()) {
			while (true) {
				capture.read(video);
				if (!video.empty()) {
					setSize(video.width() + 50, video.height() + 70);

					Mat grayImage = new Mat();
					Imgproc.cvtColor(video, grayImage, Imgproc.COLOR_BGR2GRAY);
					CascadeClassifier classifier = new CascadeClassifier(
							RESORCE_FOLDER + "cascades/haarcascade_frontalface_default.xml");

					MatOfRect facesDetected = new MatOfRect();
					classifier.detectMultiScale(grayImage, facesDetected, 1.1, 1, 0, new Size(50, 50),
							new Size(500, 500));
					facesDetected.toList().forEach(rect -> {
						System.out.println(rect.x + " " + rect.y + " " + rect.width + " " + rect.height);
						Imgproc.rectangle(video, new Point(rect.x, rect.y),
								new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 2);
					});

					BufferedImage image = new MatUtils().convertMatToImage(video);
					Graphics g = jPanel.getGraphics();
					g.drawImage(image, 10, 10, image.getWidth(), image.getHeight(), null);
				}
			}
		}
	}
}
