package actions.standard.form;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import gui.standard.form.DrzavaStandardForm;
import gui.standard.form.NaseljenoMestoStandardForm;

public class DeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;

	public DeleteAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Brisanje");
		this.standardForm = standardForm;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (standardForm instanceof DrzavaStandardForm){
			if (JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Pitanje",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				((DrzavaStandardForm) standardForm).removeRow();}
			} else if (standardForm instanceof NaseljenoMestoStandardForm) {
				if (JOptionPane.showConfirmDialog(null, "Da li ste sigurni?", "Pitanje",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					((NaseljenoMestoStandardForm) standardForm).removeRow();
				}

			}
	}
}
