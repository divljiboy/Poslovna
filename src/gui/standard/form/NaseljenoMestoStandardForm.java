package gui.standard.form;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
import actions.main.form.Lookup;
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
	
	public NaseljenoMestoStandardForm(java.util.List<Column> lista )
	{
		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Naseljena mesta");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);

		mode = MODE_EDIT;
		
		
		try {
			tableModel.openAsChildForm((String) lista.get(0).getValue());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		initToolbar();
		initTable();
		initGui();
	}

	public NaseljenoMestoStandardForm() {

		setLayout(new MigLayout("fill"));

		setSize(new Dimension(800, 600));
		setTitle("Naseljena mesta");
		setLocationRelativeTo(MainFrame.getInstance());
		setModal(true);

		mode = MODE_EDIT;
		
		try {
			tableModel.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}

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

			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
	
				DrzavaStandardForm bla = new DrzavaStandardForm();
				bla.setVisible(true);
				
				ArrayList<Column> lista= (ArrayList<Column>) bla.getLista();
				
				
				tfSifraDrzave.setText((String) lista.get(0).getValue());
				tfNazivDrzave.setText((String) lista.get(1).getValue());
				bla.dispose();
				
				

			}
		});

	}

	public NaseljenoMestoTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(NaseljenoMestoTableModel tableModel) {
		this.tableModel = tableModel;
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
		tfSifraDrzave.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String sifraDrzave = tfSifraDrzave.getText().trim();
				try {
					tfNazivDrzave.setText(Lookup.getDrzava(sifraDrzave));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		

	}

	

	public JTextField getTfNazivDrzave() {
		return tfNazivDrzave;
	}

	public void setTfNazivDrzave(JTextField tfNazivDrzave) {
		this.tfNazivDrzave = tfNazivDrzave;
	}

	public JTextField getTfSifraDrzave() {
		return tfSifraDrzave;
	}

	public void setTfSifraDrzave(JTextField tfSifraDrzave) {
		this.tfSifraDrzave = tfSifraDrzave;
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
		setMode(MODE_EDIT);
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
		String id_drzava = tfSifraDrzave.getText().trim();
		String naziv_drzava =tfNazivDrzave.getText().trim();
		
		System.out.println(sifra + naziv + id_drzava);
		try {
			NaseljenoMestoTableModel dtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			int index = dtm.insertRow(sifra, naziv, id_drzava);
			tblGrid.setRowSelectionInterval(index, index);
			setMode(MODE_ADD);
			try {
				tableModel.open();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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

	

	public void edit() {
		String sifra = tfSifra.getText().trim();
		String naziv = tfNaziv.getText().trim();
		String id_drzava = tfSifraDrzave.getText().trim();
		String naziv_drzava =tfNazivDrzave.getText().trim();
		
		System.out.println(sifra + naziv + id_drzava);
		try {
			NaseljenoMestoTableModel dtm = (NaseljenoMestoTableModel) tblGrid.getModel();
			int index = dtm.edit(sifra, naziv, id_drzava);
			tblGrid.setRowSelectionInterval(index, index);
			setMode(MODE_EDIT);
			try {
				tableModel.open();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}

		
	}

}
