package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class RefreshAction extends AbstractAction {

	private static final long serialVersionUID = 1L;


	public RefreshAction() {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/refresh.gif")));
		putValue(SHORT_DESCRIPTION, "Refresh");

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
