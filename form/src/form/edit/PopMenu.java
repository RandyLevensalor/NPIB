package form.edit;

    import java.awt.*;

    public class PopMenu extends PopupMenu {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public PopMenu() {        
			super("Edit");

            MenuItem mi;

            mi = new MenuItem("Cut");
            add(mi);

            mi = new MenuItem("Copy");
            add(mi);

            addSeparator();

            mi = new MenuItem("Paste");
            add(mi);

            //enableEvents(AWTEvent.MOUSE_EVENT_MASK); 

        }

    }

