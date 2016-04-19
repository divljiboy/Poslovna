package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class CommitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public CommitAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/commit.gif")));
		putValue(SHORT_DESCRIPTION, "Commit");
		this.standardForm = standardForm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (standardForm instanceof NaseljenoMestoStandardForm) {
			if (((NaseljenoMestoStandardForm) standardForm).getMode() == 2) {
				((NaseljenoMestoStandardForm) standardForm).addRow();
			} else if (((NaseljenoMestoStandardForm) standardForm).getMode() == 3) {
				((NaseljenoMestoStandardForm) standardForm).search();
			} else if (((NaseljenoMestoStandardForm) standardForm).getMode() == 1) {
				((NaseljenoMestoStandardForm) standardForm).removeRow();
			}
		} else if (standardForm instanceof DrzavaStandardForm) {
			if (((DrzavaStandardForm) standardForm).getMode() == 2) {
				((DrzavaStandardForm) standardForm).addRow();
			} else if (((DrzavaStandardForm) standardForm).getMode() == 3) {
				((DrzavaStandardForm) standardForm).search();
			} else if (((NaseljenoMestoStandardForm) standardForm).getMode() == 1) {
				((NaseljenoMestoStandardForm) standardForm).removeRow();
			}
		}

	}
}
