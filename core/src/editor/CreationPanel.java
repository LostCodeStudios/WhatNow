package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.ggj2015.whatnow.states.world.level.GameObject;
import com.ggj2015.whatnow.states.world.level.Level;

public class CreationPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 2315263733853810583L;

	EditorPanel parent;

	// need to have a list of every type of object...

	JComboBox<String> type;
	JCheckBox align;
	JButton load, export, delete, jiggle;

	JTextField title, boundX, boundY, boundW, boundH, spriteSheetFile;

	HashMap<GameObject, PropertyPanel> props =
			new HashMap<GameObject, PropertyPanel>();

	private Set<String> sprite_keys;
	public PropertyPanel properties;

	// also need to have a bunch of property fields that can be edited.

	public CreationPanel(EditorPanel ep) {
		setLayout(null);

		setBorder(new LineBorder(Color.GRAY, 2));

		this.parent = ep;

		properties = new PropertyPanel(parent.selected);

		// we REALLY want to be this size...
		setPreferredSize(new Dimension(300, EditorMain.SCREEN.height));
		setMinimumSize(new Dimension(300, EditorMain.SCREEN.height));
		setMaximumSize(new Dimension(300, EditorMain.SCREEN.height));

		makeLabel(5, 5, 290, 25, "Level Name:");
		title = makeField(105, 5, 190, 25, "TITLE");

		makeLabel(5, 30, 290, 25, "World Bounds: (x,y,width, height)");

		boundX = makeField(10, 55, 70, 25, "BOUND_X");
		boundY = makeField(80, 55, 70, 25, "BOUND_Y");
		boundW = makeField(150, 55, 70, 25, "BOUND_W");
		boundH = makeField(220, 55, 70, 25, "BOUND_H");

		makeLabel(5, 85, 290, 25, "Sprite Sheet File:");
		spriteSheetFile = makeField(20, 110, 200, 25, "SPRITE_SHEET");

		properties.setBounds(20, 150, 250, 300);
		properties.update();
		add(properties);

		align = createCheckBox(25, 560, 100, 25, "Snap to Grid");

		sprite_keys = parent.level.getSpriteSheet().getRegions("").keySet();
		delete = createButton(120, 455, 150, 25, "Delete Selected Objects");
		delete.setBackground(properties.color);
		jiggle = createButton(20, 455, 110, 25, "Separate");
		jiggle.setBackground(properties.color);
		makeLabel(5, 600, 100, 30, "Object types");
		type =
				createBox(105, 600, 190, 30,
						sprite_keys.toArray(new String[sprite_keys.size()]));

		export = createButton(105, EditorMain.SCREEN.height - 40,
				190, 30, "EXPORT");
		load = createButton(5, EditorMain.SCREEN.height - 40,
				100, 30, "IMPORT");

	}

	public void updateFields() {
		boundX.setText(parent.level.getBounds().x + "");
		boundY.setText(parent.level.getBounds().y + "");
		boundW.setText(parent.level.getBounds().width + "");
		boundH.setText(parent.level.getBounds().height + "");

		title.setText(parent.level.getName());
		sprite_keys = parent.level.getSpriteSheet().getRegions("").keySet();
		type.setModel(new DefaultComboBoxModel<String>(sprite_keys
				.toArray(new String[sprite_keys.size()])));

		spriteSheetFile.setText(parent.level.getSpriteSheetFile());
	}

	private JLabel makeLabel(int a, int b, int c, int d, String txt) {
		JLabel label = new JLabel(txt);
		label.setBounds(a, b, c, d);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
		add(label);
		return label;
	}

	private JTextField makeField(int a, int b, int c, int d, String action) {
		JTextField fld = new JTextField();
		fld.setBounds(a, b, c, d);
		fld.setBackground(Color.ORANGE);
		fld.setHorizontalAlignment(JLabel.CENTER);
		fld.setFont(new Font("SANS_SERIF", Font.BOLD, 15));
		add(fld);
		fld.setActionCommand(action);
		fld.addActionListener(this);
		fld.addKeyListener(new ColorTyper(Color.ORANGE));
		fld.setBorder(new LineBorder(Color.BLACK));
		return fld;
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
		if (parent.level.getBounds() == null)
			parent.level.setBounds(new Rectangle());

		if (evt.getSource() == export) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("..\\core\\assets"));

			if (jfc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
				FileHandle fh =
						Gdx.files.getFileHandle(jfc.getSelectedFile()
								.getAbsolutePath(), FileType.Absolute);
				Json jsonObject = new Json();
				fh.writeString(jsonObject.toJson(parent.level), false);
			}
		} else if (evt.getSource() == load) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("..\\core\\assets"));

			if (jfc.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
				FileHandle fh =
						Gdx.files.getFileHandle(jfc.getSelectedFile()
								.getAbsolutePath(), FileType.Absolute);
				Json json = new Json();

				Level lvl = json.fromJson(Level.class, fh);

				// ***************** TODO: GET THIS TO WORK via other thread.
				lvl.setSpriteSheet(lvl.getSpriteSheetFile(), false);

				parent.selected.clear();
				parent.data.clear();
				for (GameObject obj : lvl.getGameObjects())
					parent.data.put(obj, new GObjEditData());
				parent.level = lvl;

				this.updateFields();

				JOptionPane.showMessageDialog(this, "File saved to "
						+ jfc.getSelectedFile().getAbsolutePath());
			}
			else
				JOptionPane.showMessageDialog(this, "Save cancelled...");

		} else if (evt.getSource() == title) {
			parent.level.setName(title.getText());
			title.setBackground(Color.ORANGE);
		}

		else if (evt.getSource() == boundX) {
			parent.level.getBounds().x = Float.parseFloat(boundX.getText());
			boundX.setBackground(Color.ORANGE);
		} else if (evt.getSource() == boundY) {
			parent.level.getBounds().y = Float.parseFloat(boundY.getText());
			boundY.setBackground(Color.ORANGE);
		} else if (evt.getSource() == boundW) {
			parent.level.getBounds().width = Float.parseFloat(boundW.getText());
			boundW.setBackground(Color.ORANGE);
		} else if (evt.getSource() == boundH) {
			parent.level.getBounds().height =
					Float.parseFloat(boundH.getText());
			boundH.setBackground(Color.ORANGE);
		} else if (evt.getSource() == spriteSheetFile) {
			// TODO: get this to work in other file
			parent.level.setSpriteSheet(spriteSheetFile.getText(), false);
			parent.initImage();
			spriteSheetFile.setBackground(Color.ORANGE);
		} else if (evt.getSource() == delete) {
			parent.level.getGameObjects().removeAll(parent.selected);
		} else if (evt.getSource() == jiggle) {
			for (GameObject obj : parent.selected)
				obj.getPosition().add((float) (Math.random() - 0.5),
						(float) (Math.random() - 0.5), 0);
		}
	}

	public class PropertyPanel extends JPanel implements ActionListener,
			ChangeListener {
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
				templateField, scaleField, posXField, posYField, posZField;
		JSpinner layerSpin;

		Collection<? extends GameObject> objects;
		Color color;

		public PropertyPanel(Collection<? extends GameObject> obj) {
			this.objects = obj;
			setLayout(null);

			color = new Color(150, 250, 180);

			this.setBorder(new LineBorder(Color.black, 3));
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

			scaleField = makeField(105, 165, 130, 23, "SCALE");
			posXField = makeField(105, 210, 130, 23, "POS_X");
			posYField = makeField(105, 233, 130, 23, "POS_Y");
			posZField = makeField(105, 256, 130, 23, "POS_Z");

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
			scaleField.setEnabled(b && !objects.isEmpty());
			spriteKField.setEnabled(b && !objects.isEmpty());
			tagField.setEnabled(b && !objects.isEmpty());
			groupField.setEnabled(b && !objects.isEmpty());
			typeField.setEnabled(b && !objects.isEmpty());
			templateField.setEnabled(b && !objects.isEmpty());

			posZField.setEnabled(b && !objects.isEmpty());

			posXField.setEnabled(b && objects.size() == 1);
			posYField.setEnabled(b && objects.size() == 1);

			if (objects == null || objects.isEmpty())
				return;

			// Sprite keys
			String consensus = null;
			for (GameObject o : objects)
				if (consensus == null)
					consensus = o.getSpriteKey();
				else if (!o.getSpriteKey().equals(consensus)) {
					consensus = "<Assorted>";
					break;
				}
			spriteKField.setText(consensus);

			// Tags
			consensus = null;
			for (GameObject o : objects)
				if (consensus == null)
					consensus = o.getTag();
				else if (!o.getTag().equals(consensus)) {
					consensus = "<Assorted>";
					break;
				}
			tagField.setText(consensus);

			// Groups
			consensus = null;
			for (GameObject o : objects)
				if (consensus == null)
					consensus = o.getGroup();
				else if (!o.getGroup().equals(consensus)) {
					consensus = "<Assorted>";
					break;
				}
			groupField.setText(consensus);

			// Types
			consensus = null;
			for (GameObject o : objects)
				if (consensus == null)
					consensus = o.getType();
				else if (!o.getType().equals(consensus)) {
					consensus = "<Assorted>";
					break;
				}
			typeField.setText(consensus);

			// Templates
			consensus = null;
			for (GameObject o : objects)
				if (consensus == null)
					consensus = o.getTemplate();
				else if (!o.getTemplate().equals(consensus)) {
					consensus = "<Assorted>";
					break;
				}
			templateField.setText(consensus);

			// Layer
			int cons = -999;
			for (GameObject o : objects)
				if (cons == -999)
					cons = o.getLayer();
				else if (o.getLayer() != cons) {
					cons = Integer.MIN_VALUE;
					break;
				}
			layerSpin.setValue(((Integer) cons).doubleValue());

			// Scale
			float conse = -999;
			for (GameObject o : objects)
				if (conse == -999)
					conse = o.getScale();
				else if (o.getScale() != cons) {
					conse = Float.NaN;
					break;
				}

			scaleField.setText("" + conse);

			// posX
			conse = -999;
			for (GameObject o : objects)
				if (conse == -999)
					conse = o.getPosition().x;
				else if (o.getPosition().x != cons) {
					conse = Float.NaN;
					break;
				}

			posXField.setText("" + conse);

			// posY
			conse = -999;
			for (GameObject o : objects)
				if (conse == -999)
					conse = o.getPosition().y;
				else if (o.getPosition().y != cons) {
					conse = Float.NaN;
					break;
				}
			posYField.setText("" + conse);

			// posZ
			conse = -999;
			for (GameObject o : objects)
				if (conse == -999)
					conse = o.getPosition().z;
				else if (o.getPosition().z != cons) {
					conse = Float.NaN;
					break;
				}
			posZField.setText("" + conse);

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
			fld.addKeyListener(new ColorTyper(color));
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

			// js.setEditor(makeField(0, 0, 0, 0, "err"));
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
			else if (e.getActionCommand().equals("SCALE"))
				for (GameObject obj : objects)
					obj.setScale(Float.parseFloat(src.getText()));
			else if (e.getActionCommand().equals("POS_X"))
				for (GameObject obj : objects)
					obj.getPosition().x = Float.parseFloat(src.getText());
			else if (e.getActionCommand().equals("POS_Y"))
				for (GameObject obj : objects)
					obj.getPosition().y = Float.parseFloat(src.getText());
			else if (e.getActionCommand().equals("POS_Z"))
				for (GameObject obj : objects)
					obj.getPosition().z = Float.parseFloat(src.getText());

			src.setBackground(color);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() == layerSpin) {
				for (GameObject obj : objects)
					obj.setLayer((int) Float.parseFloat(layerSpin.getValue()
							.toString()));
			}
		}
	}

	private class ColorTyper extends KeyAdapter {
		// Color clr;

		ColorTyper(Color c) {
			// this.clr = c;
		}

		public void keyTyped(KeyEvent e) {
			if (e.getKeyChar() != '\n')
				((JTextField) e.getSource()).setBackground(new Color(250, 150,
						150));

		}

	}
}
