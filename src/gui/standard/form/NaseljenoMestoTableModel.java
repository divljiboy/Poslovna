package gui.standard.form;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import database.DBConnection;

public class NaseljenoMestoTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String basicQuery = "SELECT nm_sifra, nm_naziv, naseljeno_mesto.dr_sifra, dr_naziv FROM naseljeno_mesto JOIN drzava on naseljeno_mesto.dr_sifra = drzava.dr_sifra";
	private String orderBy = " ORDER BY nm_sifra";
	private String whereStmt = " WHERE naseljeno_mesto.dr_sifra=";
	  

	public NaseljenoMestoTableModel(Object[] colNames, int rowCount) {
		super(colNames, rowCount);
	}

	//Otvaranje upita
	public void open() throws SQLException {
	    fillData(basicQuery + orderBy);
	}

	
	private void fillData(String sql) throws SQLException {
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
		ResultSet rset = stmt.executeQuery(sql);
		while (rset.next()) {
			String sifra = rset.getString("NM_SIFRA");
			String naziv = rset.getString("NM_NAZIV");
			String sifraDrzave = rset.getString("DR_SIFRA");
			String nazivDrzave = rset.getString("DR_NAZIV");
			addRow(new String[]{sifra, naziv, sifraDrzave, nazivDrzave});
		}
		rset.close();
		stmt.close();
		fireTableDataChanged();
	}
	

	
	private int sortedInsert(String sifra, String naziv) {
		int left = 0;
		int right = getRowCount() - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			mid = (left + right) / 2;
			String aSifra = (String) getValueAt(mid, 0);
			if (SortUtils.getLatCyrCollator().compare(sifra, aSifra) > 0)
				left = mid + 1;
			else if (SortUtils.getLatCyrCollator().compare(sifra, aSifra) < 0)
				right = mid - 1;
			else
				// ako su jednaki: to ne moze da se desi ako tabela ima primarni
				// kljuc
				break;
		}
		insertRow(left, new String[] { sifra, naziv });
		return left;
	}

	


	/**
	 * Inicijalno popunjavanje forme kada se otvori iz forme Drzave preko next mehanizma
	 * @param where
	 * @throws SQLException
	 */
	public void openAsChildForm(String where) throws SQLException{
		
		String sql = basicQuery + whereStmt+where+ orderBy; //upotrebiti where parametar
		fillData(sql);
	}

	public int insertRow(String sifra, String naziv, String drzava)throws SQLException {
		int retVal = 0;
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"INSERT INTO naseljeno_mesto (nm_sifra, nm_naziv,naseljeno_mesto.dr_sifra) VALUES (?,?,?)");
		       
		stmt.setString(1, sifra);
		stmt.setString(2, naziv);
		stmt.setString(3, drzava);
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		// Unos sloga u bazu
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i unos u TableModel
			retVal = sortedInsert(sifra, naziv);
			fireTableDataChanged();
		}
		return retVal;
	}

	public void deleteRow(int index) throws SQLException {
		PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				"DELETE FROM naseljeno_mesto WHERE nm_sifra=?");
		String sifra = (String) getValueAt(index, 0);
		stmt.setString(1, sifra);
		// Brisanje iz baze
		int rowsAffected = stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if (rowsAffected > 0) {
			// i brisanje iz TableModel-a
			removeRow(index);
			fireTableDataChanged();
		}
	}

	

}



