/**
 * The class responsible for loading the form
 * This may be initiated from the command line
 * For example:
 * <pre>
 * java form.FormLoader [url]
 * where url is the url of the form description file
 * </pre>
 * @author Randy Levensalor
 * @version 2.0b1
 * todo make applet
 */

package form;

import form.edit.*;
import java.applet.*;
import java.awt.*;

public class FormLoader extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Object containing all data and is responsible for the
	 */
	private Viewer viewer = null;

	public boolean edit = false;
	public boolean isApplet = false;

	/**
	 * To initialize the applet
	 */
	@Override
	public void init() {
		isApplet = true;
		String fileLocation = getParameter("file");

		init(fileLocation, false);
	}

	public void init(String fileLocation, boolean editable) {

		System.out.println("Creating new FormLoader");
		AppletContext ac = null;

		edit = editable;
		Panel p = null;

		Frame frame = null;
		if (!isApplet) {
			frame = new Frame("NPIB Creator");
			frame.setSize(600, 600);
			p = new Panel();
			new WindowCloser(frame, true);
			frame.add(p);
			frame.setVisible(true);
			p.setVisible(true);
		} else {
			p = this;
			ac = getAppletContext();
		}
		p.setLayout(new GridLayout(1, 1));
		viewer = new Viewer(edit, p, this);

		if (fileLocation != null) {
			Open open = new Open(fileLocation);

			viewer.initialize(open);

		}

		if (!isApplet && edit) {
			frame.setMenuBar(viewer.getMenuBar());
			frame.validate();
		}
		viewer.setViewable("root");

	}

	public static void main(String[] args) {
		boolean editable = true;

		String fileLocation = null;
		if (args.length > 0) {
			fileLocation = args[0];
			if (fileLocation.equals("-e")) {
				editable = true;
				if (args.length > 1)
					fileLocation = args[1];
				else
					fileLocation = null;
			}
		}
		new FormLoader(fileLocation, editable);
	}

	/**
	 * @param Sting
	 *            formDescriptor Either describes the form or the location of
	 *            the descriptor file. Currently only url's are accepted should
	 *            be easy to add more methods. Possible add a different
	 *            constructor to take BUffered Reader.
	 **/
	public FormLoader(String fileLocation, boolean editable) {
		System.out.println("Creating new FormLoader");

		init(fileLocation, editable);
	}

	/**
	 * Null constructor so the applet works not really used
	 */
	public FormLoader() {
	}

	public String getCommandArgs() {
		// this may need to be changed
		return "hi?";// viewer.submit();
	}

}
