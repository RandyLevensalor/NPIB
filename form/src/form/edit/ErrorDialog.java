
/**
 * Pop up a (modal) error dialog and wait for a user to press "continue".
 */

package form.edit;

import java.awt.*;

public class ErrorDialog extends MessageDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorDialog(Frame frame, String message) {
	super(frame, "Error", message);
    }

}
