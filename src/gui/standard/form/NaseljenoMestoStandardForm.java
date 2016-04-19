package gui.standard.form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import actions.standard.form.AddAction;
import actions.standard.form.CommitAction;
import actions.standard.form.DeleteAction;
import actions.standard.form.FirstAction;
import actions.standard.form.HelpAction;
import actions.standard.form.LastAction;
import actions.standard.form.NextAction;
import actions.standard.form.NextFormAction;
import actions.standard.form.PickupAction;
import actions.standard.form.PreviousAction;
import actions.standard.form.RefreshAction;
import actions.standard.form.RollbackAction;
import actions.standard.form.SearchAction;
import gui.main.form.MainFrame;
import net.miginfocom.swing.MigLayout;

public class NaseljenoMestoStandardForm extends JDialog {
	private static final long serialVersionUID = 1L;

	public static final int MODE_EDIT = 1;
	public static final int MODE_ADD = 2;
	public static final int MODE_SEARCH = 3;
	private int mode;

	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm, btnPickup,
			btnRefresh, btnRollback, btnSearch, btnPrevious;
	NaseljenoMestoTableModel tableModel = new NaseljenoMestoTableModel(
			new String[] { "Šifra", "Naziv", "Šifra države", "Naziv države" }, 0);
	private JTable tblGrid = new JTable();
	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);

	private JTextField tfNazivDrzave = new JTextField(20);
	private JTextField tfSifraDrzave = new JTextField(5);

	private JButton btnZoom = new JButton("...");

	public NaseljenoMestoStandardForm() {

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Naseljena mesta");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);

		mode = MODE_EDIT;

		initToolbar();
		initTable();
		initGui();

	}

	private void initGui() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout());

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));

		JLabel lblSifra = new JLabel("Šifra mesta:");
		JLabel lblNaziv = new JLabel("Naziv mesta:");
		JLabel lblSifraDrzave = new JLabel("Šifra države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra, "wrap, gapx 15px");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv, "wrap,gapx 15px, span 3");
		dataPanel.add(lblSifraDrzave);
		dataPanel.add(tfSifraDrzave, "gapx 15px");
		dataPanel.add(btnZoom);

		dataPanel.add(tfNazivDrzave, "pushx");
		tfNazivDrzave.setEditable(false);
		bottomPanel.add(dataPanel);

		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel, "dock east");

		add(bottomPanel, "grow, wrap");
		btnZoom.addActionListener(new ActionListener() {
			
		
				//TODO implementirati zoom mehanizam - povezati sa formom Drzava
				//Uputstvo:
				//Instancirati DrzavaStandardForm i prikazati je
				//ako je taj dijalog modalan, izvrsavanje ove metode se nece nastaviti dok se forma Drzava ne zatvori
				//U Drzavi implementirati listener kojim se reaguje na pritisak pickup dugmeta
				//U okviru njega napraviti po Column objekat za svaku kolonu koju zelimo preneti
				//Preciznije, za selektovani red izdvojiti vrednosti trazenih kolona i napraviti Column objekte
				//naziv Column objekta je naziv kolone  bazi (nije bitno za zoom, ali je bitno za next da se poklapaju, 
				//pa je najbolje staviti i ovde).
				//vrednost je vrednost pomenute celije tabele
				//Napraviti Column list objekat koji sadrzi sve ove Column objekte. Izdvijiti ga kao atribut klase, napraviti getter
				//Zatvrotiti formu Drzava na kraju implementacije pickup listener-a
				//Time se vracamo na ovu metodu
				//Preuzeti prenete vrednosti za naziv i sifru drzave i time popuniti odgovarajuca tekstualna polja
				//Paziti da se ne desi exception kada se forma zatvori bez klila na pickup dugme (nego na x)	
				

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DrzavaStandardForm bla= new DrzavaStandardForm();
				bla.setVisible(true);
				
			}
			});


	}

	private void initToolbar() {
		toolBar = new JToolBar();
		btnSearch = new JButton(new SearchAction(this));
		toolBar.add(btnSearch);

		btnRefresh = new JButton(new RefreshAction());
		toolBar.add(btnRefresh);

		btnPickup = new JButton(new PickupAction(this));
		toolBar.add(btnPickup);

		btnHelp = new JButton(new HelpAction());
		toolBar.add(btnHelp);

		toolBar.addSeparator();

		btnFirst = new JButton(new FirstAction(this));
		toolBar.add(btnFirst);

		btnPrevious = new JButton(new PreviousAction(this));
		toolBar.add(btnPrevious);

		btnNext = new JButton(new NextAction(this));
		toolBar.add(btnNext);

		btnLast = new JButton(new LastAction(this));
		toolBar.add(btnLast);

		toolBar.addSeparator();

		btnAdd = new JButton(new AddAction(this));
		toolBar.add(btnAdd);

		btnDelete = new JButton(new DeleteAction(this));
		toolBar.add(btnDelete);

		toolBar.addSeparator();

		btnNextForm = new JButton(new NextFormAction(this));
		toolBar.add(btnNextForm);

		btnPickup.setEnabled(false);
		add(toolBar, "dock north");
	}

	private void initTable() {

		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");
		
		tblGrid.setModel(tableModel);

		try {
			tableModel.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Dozvoljeno selektovanje redova
		tblGrid.setRowSelectionAllowed(true);
		// Ali ne i selektovanje kolona
		tblGrid.setColumnSelectionAllowed(false);

		// Dozvoljeno selektovanje samo jednog reda u jedinici vremena
		tblGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblGrid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting())
					return;
				sync();
			}
		});

	}

	private void sync() {
		int index = tblGrid.getSelectedRow();
		if (index < 0) {
			tfSifra.setText("");
			tfNaziv.setText("");
			return;
		}
		String sifra = (String) tblGrid.getModel().getValueAt(index, 0);
		String naziv = (String) tblGrid.getModel().getValueAt(index, 1);
		String sifraDrzave = (String) tblGrid.getModel().getValueAt(index, 2);
		String nazivDrzave = (String) tblGrid.getModel().getValueAt(index, 3);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
		tfSifraDrzave.setText(sifraDrzave);
		tfNazivDrzave.setText(nazivDrzave);
	}

	public void setMode(int i) {
		this.mode = i;
		if (mode == MODE_ADD) {

			tfSifra.setText("");
			tfNaziv.setText("");
			tfSifraDrzave.setText("");
			tfNazivDrzave.setText("");

		}
		if (mode == MODE_SEARCH) {

			tfSifra.setText("");
			tfNaziv.setText("");
			tfSifraDrzave.setText("");
			tfNazivDrzave.setText("");
		}

	}

	public int getMode() {
		return mode;
	}

	public void addRow() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		String drzava=tfSifraDrzave.getText().trim();
		System.out.println(sifra+naziv+drzava);
		try {
			NaseljenoMestoTableModel dtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			int index = dtm.insertRow(sifra, naziv,drzava,"bla");
			tblGrid.setRowSelectionInterval(index, index);
			setMode(MODE_ADD);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void search() {
		// TODO Auto-generated method stub
		
	}

	

	public void removeRow() {
		System.out.println("usao u remove row");
		int index = tblGrid.getSelectedRow();
		if (index == -1)
			// Ako nema selektovanog reda (tabela prazna)
			return;
		// izlazak
		// kada obrisemo tekuci red, selektovacemo sledeci (newindex):
		int newIndex = index;
		// sem ako se obrise poslednji red, tada selektujemo prethodni
		if (index == tableModel.getRowCount() - 1)
			newIndex--;
		try {
			NaseljenoMestoTableModel dtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			dtm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void goFirst() {
		int rowCount = tblGrid.getModel().getRowCount();
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(0, 0);
		
	}

	public void goLast() {
		int rowCount = tblGrid.getModel().getRowCount();
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
		
	}

	public void goNext() {
		int rowCount = tblGrid.getModel().getRowCount();
		int current = tblGrid.getSelectedRow();
		System.out.println(current + " " + rowCount);
		if (current < rowCount - 1) {
			tblGrid.setRowSelectionInterval(current + 1, current + 1);
		} else {
			tblGrid.setRowSelectionInterval(0, 0);
		}
		
	}

	public void goPrevious() {
		int rowCount = tblGrid.getModel().getRowCount();
		int current = tblGrid.getSelectedRow();
		if (current > 0) {
			tblGrid.setRowSelectionInterval(current - 1, current - 1);
		} else {
			tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
		}
		
	}

	

}
