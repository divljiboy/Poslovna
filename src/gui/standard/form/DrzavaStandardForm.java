package gui.standard.form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import actions.main.form.Column;
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

public class DrzavaStandardForm extends JDialog {
	private static final long serialVersionUID = 1L;

	private JToolBar toolBar;
	private JButton btnAdd, btnCommit, btnDelete, btnFirst, btnLast, btnHelp, btnNext, btnNextForm, btnPickup,
			btnRefresh, btnRollback, btnSearch, btnPrevious;
	private JTextField tfSifra = new JTextField(5);
	private JTextField tfNaziv = new JTextField(20);
	private JTable tblGrid = new JTable();
	DrzaveTableModel tableModel = new DrzaveTableModel(new String[] { "Šifra", "Naziv" }, 0);
	public List<Column> lista = new ArrayList<Column>();

	public List<Column> getLista() {
		return lista;
	}

	public void setLista(List<Column> lista) {
		this.lista = lista;
	}

	private static final int MODE_EDIT = 1;
	private static final int MODE_ADD = 2;
	private static final int MODE_SEARCH = 3;
	private int mode;

	public DrzavaStandardForm() {

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Države");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);
		setMode(MODE_EDIT);

		// Kreiranje TableModel-a, parametri: header-i kolona i broj redova

		tblGrid.setModel(tableModel);

		try {
			tableModel.open();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

		initToolbar();
		initTable();
		initGui();
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tableModel.open();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnNextForm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("usao sam u next");
				lista.clear();

				Column sifra_drzava = new Column("DR_SIFRA", tfSifra.getText());
				Column naziv_drzava = new Column("DR_NAZIV", tfNaziv.getText());
				System.out.println(tfSifra.getText() + tfNaziv.getText());

				lista.add(sifra_drzava);
				lista.add(naziv_drzava);

				NaseljenoMestoStandardForm bla = new NaseljenoMestoStandardForm(lista);
				bla.setVisible(true);
				bla.dispose();

			}
		});

	}

	public DrzaveTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(DrzaveTableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTable getTblGrid() {
		return tblGrid;
	}

	public void setTblGrid(JTable tblGrid) {
		this.tblGrid = tblGrid;
	}

	private void initTable() {
		JScrollPane scrollPane = new JScrollPane(tblGrid);
		add(scrollPane, "grow, wrap");

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

		add(toolBar, "dock north");
	}

	private void initGui() {

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("fillx"));
		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new MigLayout("gapx 15px"));

		JPanel buttonsPanel = new JPanel();
		btnCommit = new JButton(new CommitAction(this));
		btnRollback = new JButton(new RollbackAction(this));

		JLabel lblSifra = new JLabel("Šifra države:");
		JLabel lblNaziv = new JLabel("Naziv države:");

		dataPanel.add(lblSifra);
		dataPanel.add(tfSifra, "wrap");
		dataPanel.add(lblNaziv);
		dataPanel.add(tfNaziv);
		bottomPanel.add(dataPanel);

		buttonsPanel.setLayout(new MigLayout("wrap"));
		buttonsPanel.add(btnCommit);
		buttonsPanel.add(btnRollback);
		bottomPanel.add(buttonsPanel, "dock east");

		add(bottomPanel, "grow, wrap");
	}

	public void goLast() {
		int rowCount = tblGrid.getModel().getRowCount();
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(rowCount - 1, rowCount - 1);
	}

	public void goFirst() {
		int rowCount = tblGrid.getModel().getRowCount();
		if (rowCount > 0)
			tblGrid.setRowSelectionInterval(0, 0);
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

	private void sync() {
		int index = tblGrid.getSelectedRow();
		if (index < 0) {
			tfSifra.setText("");
			tfNaziv.setText("");
			return;
		}
		String sifra = (String) tableModel.getValueAt(index, 0);
		String naziv = (String) tableModel.getValueAt(index, 1);
		tfSifra.setText(sifra);
		tfNaziv.setText(naziv);
		setMode(MODE_EDIT);
	}

	public void removeRow() {
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
			DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
			dtm.deleteRow(index);
			if (tableModel.getRowCount() > 0)
				tblGrid.setRowSelectionInterval(newIndex, newIndex);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void addRow() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		try {
			DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
			int index = dtm.insertRow(sifra, naziv);
			tblGrid.setRowSelectionInterval(index, index);
			setMode(MODE_ADD);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public JTextField getTfSifra() {
		return tfSifra;
	}

	public void setTfSifra(JTextField tfSifra) {
		this.tfSifra = tfSifra;
	}

	public JTextField getTfNaziv() {
		return tfNaziv;
	}

	public void setTfNaziv(JTextField tfNaziv) {
		this.tfNaziv = tfNaziv;
	}

	public void search() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		tableModel.setRowCount(0);
		try {
			tableModel.search(sifra, naziv);
			
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void setMode(int mode) {
		this.mode = mode;
		if (mode == MODE_ADD) {

			tfSifra.setText("");
			tfNaziv.setText("");

		} else if (mode == MODE_SEARCH) {

			tfSifra.setText("");
			tfNaziv.setText("");

		} else {
			this.mode = MODE_EDIT;
		}

	}

	public int getMode() {
		return mode;
	}

	public void edit() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();

		try {
			DrzaveTableModel dtm = (DrzaveTableModel) tblGrid.getModel();
			int index = dtm.editRow(sifra, naziv);
			tblGrid.setRowSelectionInterval(index, index);
			try {
				tableModel.open();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setMode(MODE_EDIT);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}

	}
}
