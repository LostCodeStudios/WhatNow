package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class CreationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 2315263733853810583L;

	EditorPanel parent;

	// need to have a list of every type of object...

	JComboBox<String> type;
	JCheckBox align;
	JButton add, export;

	// also need to have a bunch of property fields that can be edited.

	public CreationPanel(EditorPanel ep) {
		setLayout(null);

		this.parent = ep;

		// we REALLY want to be this size...
		setPreferredSize(new Dimension(300, EditorMain.SCREEN.height));
		setMinimumSize(new Dimension(300, EditorMain.SCREEN.height));
		setMaximumSize(new Dimension(300, EditorMain.SCREEN.height));

		add = createButton(200, 36, 95, 30, "Add");

		Set<String> set = parent.level.getSpriteSheet().getRegions("")
				.keySet();

		type = createBox(5, 36, 190, 30, set.toArray(new String[set.size()]));
		export = createButton(5, 5, 290, 30, "EXPORT");
		align = createCheckBox(25, 70, 100, 25, "Snap to Grid");
	}

	private JCheckBox createCheckBox(int a, int b, int c, int d, String s) {
		JCheckBox button = new JCheckBox(s);
		button.setFocusPainted(false);
//		button.setForeground(Color.ORANGE);
		button.setOpaque(false);
		button.setBounds(a, b, c, d);
		button.addActionListener(this);
		add(button);
		return button;
	}

	private JButton createButton(int a, int b, int c, int d, String s) {
		JButton button = new JButton(s);
		button.setFocusPainted(false);
		button.setBackground(Color.ORANGE);
		button.setBorder(new LineBorder(Color.BLACK));
		button.setBounds(a, b, c, d);
		button.addActionListener(this);
		add(button);
		return button;
	}

	private JComboBox<String> createBox(int a, int b, int c, int d,
			String... opt) {
		JComboBox<String> box = new JComboBox<String>(opt);
		// box.setFocusable(false);
		box.setEditable(true);
		box.setBackground(Color.ORANGE);
		box.setBorder(new LineBorder(Color.BLACK));
		box.setBounds(a, b, c, d);
		box.addActionListener(this);
		add(box);
		return box;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == add) {
			parent.buildQueue.offer((String) type.getSelectedItem());
		}
		else if (evt.getSource() == export) {
			Json lol = new Json();
			FileHandle fh = Gdx.files.local("first.level");
			fh.writeString(lol.toJson(parent.level), false);
		}
	}
}
