package actions.standard.form;


import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import actions.main.form.Column;
import actions.main.form.ColumnList;
import gui.standard.form.DrzavaStandardForm;

public class PickupAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private JDialog standardForm;
	
	public PickupAction(JDialog standardForm) {
		putValue(SMALL_ICON, new ImageIcon(getClass().getResource("/img/zoom-pickup.gif")));
		putValue(SHORT_DESCRIPTION, "Zoom pickup");
		this.standardForm = standardForm;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO implementirati zoom mehanizam - povezati sa formom Drzava
		// Uputstvo:
		// Instancirati DrzavaStandardForm i prikazati je
		// ako je taj dijalog modalan, izvrsavanje ove metode se nece nastaviti
		// dok se forma Drzava ne zatvori
		// U Drzavi implementirati listener kojim se reaguje na pritisak pickup
		// dugmeta
		String name;
		Object value;
		if (standardForm instanceof DrzavaStandardForm) {
			name=((DrzavaStandardForm) standardForm).getTfSifra().getText();
		}
		//Column bla= new Column(name, value);
		
		
		//ColumnList lista=new ColumnList(bla);
		// U okviru njega napraviti po Column objekat za svaku kolonu koju
		// zelimo preneti
		// Preciznije, za selektovani red izdvojiti vrednosti trazenih kolona i
		// napraviti Column objekte
		// naziv Column objekta je naziv kolone bazi (nije bitno za zoom, ali je
		// bitno za next da se poklapaju,
		// pa je najbolje staviti i ovde).
		// vrednost je vrednost pomenute celije tabele
		// Napraviti Column list objekat koji sadrzi sve ove Column objekte.
		// Izdvijiti ga kao atribut klase, napraviti getter
		// Zatvrotiti formu Drzava na kraju implementacije pickup listener-a
		// Time se vracamo na ovu metodu
		// Preuzeti prenete vrednosti za naziv i sifru drzave i time popuniti
		// odgovarajuca tekstualna polja
		// Paziti da se ne desi exception kada se forma zatvori bez klila na
		// pickup dugme (nego na x)
	}
}
