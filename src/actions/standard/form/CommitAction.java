package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class CommitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private DrzavaStandardForm standardForm;

	public CommitAction(DrzavaStandardForm standardForm) {
		putValue(SMALL_ICON,
				new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (standardForm.getMode() == 2) {
			standardForm.addRow();
		} else if (standardForm.getMode() == 3) {
			standardForm.search();
		} else {
			//standardForm.edit();
		}
	}
}
