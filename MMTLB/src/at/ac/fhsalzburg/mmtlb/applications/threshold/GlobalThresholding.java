package at.ac.fhsalzburg.mmtlb.applications.threshold;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import at.ac.fhsalzburg.mmtlb.applications.AbstractImageModificationWorker;
import at.ac.fhsalzburg.mmtlb.interfaces.IFImageController;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Applies global thresholding to a image with given threshold
 * 
 * @author Stefan Huber
 */
public class GlobalThresholding extends AbstractImageModificationWorker {
	private static final int MIN_GRAY = 0;
	private static final int MAX_GRAY = 255;

	private int threshold = 0;

	public GlobalThresholding() {
		super(null, null);
	}

	public GlobalThresholding(IFImageController controller, MMTImage sourceImage, int threshold) {
		super(controller, sourceImage);
		this.threshold = threshold;
	}

	/**
	 * Apply global thresholding to a image
	 * 
	 * @param sourceImage source image
	 * @param threshold the threshold gray value
	 * @return binary image separated by threshold value
	 * @throws InterruptedException
	 */
	public MMTImage performThresholding(MMTImage sourceImage, int threshold) throws InterruptedException {
		MMTImage result = new MMTImage(sourceImage.getHeight(), sourceImage.getWidth());
		result.setName(sourceImage.getName());

		for (int i = 0; i < sourceImage.getImageData().length; i++) {
			publishProgress(sourceImage, i);
			checkIfInterrupted();
			int value = sourceImage.getImageData()[i]; //get the pixels gray value
			result.getImageData()[i] = value > threshold ? MAX_GRAY : MIN_GRAY; // set to MIN or MAX if under or over the threshold
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MMTImage modifyImage(MMTImage sourceImage) throws InterruptedException {
		return performThresholding(sourceImage, threshold);
	}

	public static void main(String[] args) throws Exception {

		System.out.println("Global thresholding");
		System.out.println("Enter the full path to a picture: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		System.out.println("Enter a threshold value between 0 and 255: ");
		String thresholdString = br.readLine();
		Integer threshold = Integer.valueOf(thresholdString);

		if (threshold > 255 || threshold < 0) {
			System.err.println("Invalid value entered (has to be 0-25).. " + thresholdString);
			return;
		}

		MMTImage newImage = new GlobalThresholding().performThresholding(image, threshold);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_Gthreshold" + path.substring(splitIndex, path.length());
		FileImageWriter.write(newImage, newPath);
		System.out.println("Global thresholded image saved as: \n" + newPath);
	}

}
