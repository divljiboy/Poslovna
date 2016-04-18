package actions.standard.form;

import gui.standard.form.DrzavaStandardForm;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class DeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private DrzavaStandardForm standardForm;
	
	public DeleteAction(DrzavaStandardForm standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/remove.gif")));
		putValue(SHORT_DESCRIPTION, "Brisanje");
		this.standardForm=standardForm;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		standardForm.removeRow();
	}
}
