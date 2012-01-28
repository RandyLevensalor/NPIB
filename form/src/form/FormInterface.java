package form;

import java.awt.GridBagConstraints;
import java.io.Serializable;
import java.awt.*;
import java.awt.event.*;

public interface FormInterface extends Serializable {

	public void save(Save save);

	public void setSelect(boolean bl);

	public Component getComponent();

	public Container getParent();

	public void addMouseMotionListener(MouseMotionListener ml);

	public void addMouseListener(MouseListener ml);

	public void addActionListener(ActionListener listen);

	public String getText();

	public void setText(String str);

	public String getName();

	public void setName(String str);

	public GridBagConstraints getGridBagConstraints();

	public void initialize(Open open, boolean edit);

}
