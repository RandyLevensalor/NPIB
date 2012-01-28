package form.comp;

import form.edit.*;
import java.awt.*;
import java.awt.event.*;

public class AskDialog extends Dialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField tf = null;
	private String answer = null;

	public String answer() {
		return answer;
	}

	public AskDialog(String title, String question) {
		super(new Frame(), title, true);
		new WindowCloser(this);

		GridBagLayout gridBag = new GridBagLayout();
		setLayout(gridBag);
		GridBagConstraints cons = new GridBagConstraints();
		cons.gridwidth = GridBagConstraints.REMAINDER;

		int height = 5;
		while (question.length() > 0) {
			int ix = question.indexOf('\n');
			String line;
			if (ix >= 0) {
				line = question.substring(0, ix);
				question = question.substring(ix + 1);
			} else {
				line = question;
				question = "";
			}
			Label l = new Label(line);
			gridBag.setConstraints(l, cons);
			add(l);
			height += 20;
		}

		tf = new TextField(30);
		gridBag.setConstraints(tf, cons);
		add(tf);
		height += 25;
		height += 35;

		cons.anchor = GridBagConstraints.CENTER;
		cons.gridwidth = 1;
		Button ok = new Button("OK");
		ok.setActionCommand("ok");
		ok.addActionListener(this);
		gridBag.setConstraints(ok, cons);
		add(ok);
		height += 25;
		height += 35;

		Button cancel = new Button("Cancel");
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		gridBag.setConstraints(cancel, cons);
		add(cancel);
		height += 25;
		height += 35;

		setBounds(30, 20, 400, height + 5);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("ok")) {
			answer = tf.getText();
		} else if (e.getActionCommand().equals("cancel")) {
			answer = null;
		}
		// our button got pushed.
		dispose();
	}

}
