package at.ac.fhsalzburg.mmtlb.gui.panels.accordion;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * Abstract base class for the {@link HorizontalScrollPane} and
 * {@link VerticalScrollPane}
 * 
 * @author Manfred Hantschel
 */
public abstract class AbstractLimitedScrollPane extends JScrollPane {

	private static final long serialVersionUID = -6785608117387588377L;

	private class Viewport extends JViewport {

		private static final long serialVersionUID = 3025239163141101382L;

		public Viewport() {
			super();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setViewSize(Dimension newSize) {
			Dimension maxSize = getMaximumSize();

			newSize.width = Math.min(newSize.width, maxSize.width);
			newSize.height = Math.min(newSize.height, maxSize.height);

			final JScrollPane scrollPane = (JScrollPane) getParent();
			Dimension currentPreferredSize = scrollPane.getPreferredSize();

			fixViewSize(newSize);

			final Dimension expectedPreferredSize = getExpectedPreferredSize();

			if (isUpdatePreferredSizeNeeded(currentPreferredSize, expectedPreferredSize)) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scrollPane.setPreferredSize(expectedPreferredSize);
						scrollPane.validate();
					}
				});
			}

			super.setViewSize(newSize);
		}

	}

	public AbstractLimitedScrollPane() {
		super();
	}

	public AbstractLimitedScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
	}

	public AbstractLimitedScrollPane(Component view) {
		super(view);
	}

	public AbstractLimitedScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected JViewport createViewport() {
		return new Viewport();
	}

	protected abstract boolean isUpdatePreferredSizeNeeded(Dimension currentPreferredSize, Dimension expectedPreferredSize);

	protected abstract void fixViewSize(Dimension newSize);

	public Dimension getExpectedPreferredSize() {
		final Dimension expectedPreferredSize = getViewport().getLayout().preferredLayoutSize(getViewport());

		if (getHorizontalScrollBar().isVisible()) {
			expectedPreferredSize.height += getHorizontalScrollBar().getHeight();
		}

		if (getVerticalScrollBar().isVisible()) {
			expectedPreferredSize.width += getVerticalScrollBar().getWidth();
		}
		return expectedPreferredSize;
	}

}