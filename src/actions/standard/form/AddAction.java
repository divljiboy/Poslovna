package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class AddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	// kada se napravi genericka forma, staviti tu klasu umesto JDialog
	private DrzavaStandardForm standardForm;

	public AddAction(DrzavaStandardForm standardForm) {
		putValue(SMALL_ICON,
				new ImageIcon(getClass().getResource("/img/add.gif")));
		putValue(SHORT_DESCRIPTION, "Dodavanje");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		standardForm.setMode(2);
	}
}
