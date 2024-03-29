package at.ac.fhsalzburg.mmtlb.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import at.ac.fhsalzburg.mmtlb.mmtimage.MMTImage;

/**
 * Display the {@link MMTImage} in a panel
 * 
 * @author Stefan Huber
 */
public class MMTImagePanel extends JPanel {
	private static final long serialVersionUID = 8105730431947916489L;
	private static final Color BACKGROUND_COLOR = Color.WHITE;

	private Image image;
	private double scaleFactor = 1d;

	public MMTImagePanel() {
		image = null;
		setBackground(BACKGROUND_COLOR);
	}

	public MMTImagePanel(MMTImage mmtImage) {
		setImage(mmtImage);
		setBackground(BACKGROUND_COLOR);
	}

	public void setImage(MMTImage mmtImage) {
		image = mmtImage.toBufferedImage();
		setScale(scaleFactor);
	}

	public void setScale(final double factor) {

		scaleFactor = factor;
		int tempWidth = (int) (image.getWidth(null) * scaleFactor);
		int tempHeight = (int) (image.getHeight(null) * scaleFactor);

		invalidate();
		setPreferredSize(new Dimension(tempWidth + 10, tempHeight + 10));
		repaint();
	}

	public Image getImage() {
		return image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		int w = 0, h = 0;
		if (image != null) {
			w = (int) (scaleFactor * image.getWidth(null));
			h = (int) (scaleFactor * image.getHeight(null));
		}
		g.drawImage(image, 5, 5, w, h, BACKGROUND_COLOR, null);
	}

}
