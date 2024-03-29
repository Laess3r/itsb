package at.ac.fhsalzburg.mmtlb.gui.datapanels;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Panel with no data, jus a "go" button
 * 
 * @author Stefan Huber
 */
public class NoAdditionalDataPanel extends JPanel {
	private static final long serialVersionUID = 8019287308335341062L;

	private JButton go;

	public NoAdditionalDataPanel() {
		setOpaque(false);
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		go = new JButton("Go!", new ImageIcon(NoAdditionalDataPanel.class.getResource("go.png")));
		add(go);
	}

	public JButton getGo() {
		return go;
	}
}
