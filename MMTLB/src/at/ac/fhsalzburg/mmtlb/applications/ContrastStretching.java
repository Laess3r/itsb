package at.ac.fhsalzburg.mmtlb.applications;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageReader;
import at.ac.fhsalzburg.mmtlb.mmtimage.FileImageWriter;
import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * This class will applie contrast stretching to an {@link MMTImage} and returns
 * a NEW {@link MMTImage} with stretched contrast
 * 
 * @author Stefan Huber
 */
public class ContrastStretching {
	private static final Logger LOG = Logger.getLogger(ContrastStretching.class.getSimpleName());

	public static MMTImage stretchContrast(MMTImage image) {
		MMTImage stretched = new MMTImage(image.getHeight(), image.getWidth());
		stretched.setName(image.getName());

		// To be faster, we calculate the values only once
		int[] mappingValues = calculateStretchedValues(image);

		for (int i = 0; i < image.getImageData().length; i++) {
			// map all values from old to stretched gray value
			stretched.getImageData()[i] = mappingValues[image.getImageData()[i]];
		}
		return stretched;
	}

	private static int[] calculateStretchedValues(MMTImage image) {
		int[] mapping = new int[256];

		int wMin = 0;
		int wMax = 255;

		int gMin = HistogramTools.getLowestGrayValue(image);
		int gMax = HistogramTools.getHighestGrayValue(image);

		LOG.info(String.format("Original image: minGray: %d, maxGray: %d", gMin, gMax));

		for (int g = 0; g < mapping.length; g++) {
			// Determine gTilde for every contrast value
			mapping[g] = (g - gMin) * ((wMax - wMin) / (gMax - gMin)) + wMin;
		}

		return mapping;
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Contrast stretching");
		System.out.println("Enter the full path to a picture: ");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String path = br.readLine();

		MMTImage image = FileImageReader.read(path);

		MMTImage newImage = stretchContrast(image);

		int splitIndex = path.lastIndexOf('.');
		String newPath = path.substring(0, splitIndex) + "_CS" + path.substring(splitIndex, path.length());
		FileImageWriter.write(newImage, newPath);
		System.out.println("Stretched image saved as: \n" + newPath);
	}

}