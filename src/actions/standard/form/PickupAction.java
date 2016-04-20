package actions.standard.form;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.Column;
import gui.standard.form.DrzavaStandardForm;

public class PickupAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	private String naziv;
	private String sifra;
	public List<Column> lista = new ArrayList<Column>();

	public List<Column> getLista() {
		return lista;
	}

	public void setLista(List<Column> lista) {
		this.lista = lista;
	}

	public PickupAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/zoom-pickup.gif")));
		putValue(SHORT_DESCRIPTION, "Zoom pickup");
		this.standardForm = standardForm;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	

		if (standardForm instanceof DrzavaStandardForm) {
			naziv = ((DrzavaStandardForm) standardForm).getTfNaziv().getText();
			sifra = ((DrzavaStandardForm) standardForm).getTfSifra().getText();
			Column sifra_drzava = new Column("DR_SIFRA", sifra);
			Column naziv_drzava = new Column("DR_NAZIV", naziv);

			lista.add(sifra_drzava);
			lista.add(naziv_drzava);
			
			((DrzavaStandardForm) standardForm).setLista(lista);
			((DrzavaStandardForm) standardForm).dispose();
			setLista(lista);

		}
		
	
	}
}
