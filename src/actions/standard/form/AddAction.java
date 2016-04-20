package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class AddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	// kada se napravi genericka forma, staviti tu klasu umesto JDialog
	private JDialog standardForm;

	public AddAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/add.gif")));
		putValue(SHORT_DESCRIPTION, "Dodavanje");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (standardForm instanceof DrzavaStandardForm) {
			((DrzavaStandardForm) standardForm).setMode(2);
		} else if (standardForm instanceof NaseljenoMestoStandardForm) {
			((NaseljenoMestoStandardForm) standardForm).setMode(2);
		}
	}
}
