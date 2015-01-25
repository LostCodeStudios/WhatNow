package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.level.GameObject;

public class CreationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 2315263733853810583L;

	EditorPanel parent;

	// need to have a list of every type of object...

	JComboBox<String> type;
	JCheckBox align;
	JButton add, export;

	HashMap<GameObject, PropertyPanel> props =
			new HashMap<GameObject, PropertyPanel>();

	public PropertyPanel properties;

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
		export =
				createButton(5, EditorMain.SCREEN.height - 40, 290, 30,
						"EXPORT");
		align = createCheckBox(25, 70, 100, 25, "Snap to Grid");

		properties = new PropertyPanel(parent.selected);
		properties.setBounds(20, 300, 250, 310);
		properties.update();
		add(properties);
	}

	private JCheckBox createCheckBox(int a, int b, int c, int d, String s) {
		JCheckBox button = new JCheckBox(s);
		button.setFocusPainted(false);
		// button.setForeground(Color.ORANGE);
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

	class PropertyPanel extends JPanel implements ActionListener,
			KeyListener, ChangeListener {
		private static final long serialVersionUID = -4977325514786287885L;

		/*
		 * Vector3 position;
		 * String spriteKey;
		 * String tag;
		 * String group;
		 * String type;
		 * String template;
		 * int layer = 0;
		 * float scale = 1.0f;
		 */
		JTextField spriteKField, tagField, groupField, typeField,
				templateField;
		JSpinner layerSpin, scaleSpin, posXSpin, posYSpin, posZSpin;

		Collection<? extends GameObject> objects;
		Color color;

		public PropertyPanel(Collection<? extends GameObject> obj) {
			this.objects = obj;
			setLayout(null);

			color = new Color(255, 200, 180);

			this.setBorder(new TitledBorder(new LineBorder(Color.black, 3),
					"Properties"));
			this.setBackground(color);

			this.setPreferredSize(new Dimension(250, 310));
			this.setMinimumSize(new Dimension(250, 310));
			this.setMaximumSize(new Dimension(250, 310));

			spriteKField = makeField(105, 15, 130, 23, "SPRITE_KEY");
			tagField = makeField(105, 40, 130, 23, "TAG");
			groupField = makeField(105, 65, 130, 23, "GROUP");
			typeField = makeField(105, 90, 130, 23, "TYPE");
			templateField = makeField(105, 115, 130, 23, "TEMPLATE");

			layerSpin = makeSpinner(105, 140, 130, 23, true);
			scaleSpin = makeSpinner(105, 165, 130, 23, false);
			posXSpin = makeSpinner(105, 210, 130, 23, false);
			posYSpin = makeSpinner(105, 233, 130, 23, false);
			posZSpin = makeSpinner(105, 256, 130, 23, false);

			makeLabel(5, 15, 90, 23, "Sprite Key:");
			makeLabel(5, 40, 90, 23, "Tag:");
			makeLabel(5, 65, 90, 23, "Group:");
			makeLabel(5, 90, 90, 23, "Type:");
			makeLabel(5, 115, 90, 23, "Template:");
			makeLabel(5, 140, 90, 23, "Layer:");
			makeLabel(5, 165, 90, 23, "Scale:");
			makeLabel(5, 233, 90, 23, "Position (x,y,z):");
		}

		public void update() {
			boolean b = objects != null;

			layerSpin.setEnabled(b && !objects.isEmpty());
			scaleSpin.setEnabled(b && !objects.isEmpty());
			spriteKField.setEnabled(b && !objects.isEmpty());
			tagField.setEnabled(b && !objects.isEmpty());
			groupField.setEnabled(b && !objects.isEmpty());
			typeField.setEnabled(b && !objects.isEmpty());
			templateField.setEnabled(b && !objects.isEmpty());

			posXSpin.setEnabled(b && objects.size() == 1);
			posYSpin.setEnabled(b && objects.size() == 1);
			posZSpin.setEnabled(b && objects.size() == 1);

		}

		private JLabel makeLabel(int a, int b, int c, int d, String s) {
			JLabel lab = new JLabel(s);
			lab.setBounds(a, b, c, d);
			lab.setHorizontalAlignment(JLabel.RIGHT);
			// lab.setHorizontalTextPosition(JLabel.RIGHT);
			add(lab);
			return lab;
		}

		private JTextField makeField(int a, int b, int c, int d, String action) {
			JTextField fld = new JTextField();
			fld.setBounds(a, b, c, d);
			fld.setBackground(color);
			fld.setHorizontalAlignment(JLabel.CENTER);
			fld.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
			add(fld);
			fld.setActionCommand(action);
			fld.addActionListener(this);
			fld.addKeyListener(this);
			return fld;
		}

		private JSpinner makeSpinner(int a, int b, int c, int d, boolean ints) {
			JSpinner js;
			if (ints)
				js = new JSpinner(new SpinnerNumberModel());
			else
				js = new JSpinner(new SpinnerNumberModel(0,
						Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY,
						0.5));

//			js.setEditor(makeField(0, 0, 0, 0, "err"));
			js.setBounds(a, b, c, d);
			add(js);
			js.addChangeListener(this);
			return js;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField src = ((JTextField) e.getSource());
			if (e.getActionCommand().equals("GROUP"))
				for (GameObject obj : objects)
					obj.setGroup(src.getText());
			else if (e.getActionCommand().equals("TYPE"))
				for (GameObject obj : objects)
					obj.setType(src.getText());
			else if (e.getActionCommand().equals("TAG"))
				for (GameObject obj : objects)
					obj.setTag(src.getText());
			else if (e.getActionCommand().equals("SPRITE_KEY"))
				for (GameObject obj : objects)
					obj.setSpriteKey(src.getText());
			else if (e.getActionCommand().equals("TEMPLATE"))
				for (GameObject obj : objects)
					obj.setTemplate(src.getText());

			System.out.println("YAY!");
			src.setBackground(color);
		}

		@Override
		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() != '\n')
				((JTextField) e.getSource()).setBackground(new Color(255, 150,
						150));

		}
		public void keyPressed(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		@Override
		public void stateChanged(ChangeEvent e) {
			// JSpinner spin = (JSpinner) e.getSource();

			if (e.getSource() == posXSpin) {
				for (GameObject obj : objects)
					obj.getPosition().x =
							((Double) posXSpin.getValue()).floatValue();
			} else if (e.getSource() == posYSpin) {
				for (GameObject obj : objects)
					obj.getPosition().y =
							((Double) posXSpin.getValue()).floatValue();
			} else if (e.getSource() == posZSpin) {
				for (GameObject obj : objects)
					obj.getPosition().z =
							((Double) posXSpin.getValue()).floatValue();
			} else if (e.getSource() == layerSpin) {
				for (GameObject obj : objects)
					obj.setLayer(((Double) posXSpin.getValue()).intValue());
			} else if (e.getSource() == scaleSpin) {
				for (GameObject obj : objects)
					obj.setScale(((Double) scaleSpin.getValue()).floatValue());
			}
		}
	}
}
