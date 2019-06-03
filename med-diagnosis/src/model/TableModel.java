package model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return false;
		return true;
	}
	
	public Vector<Object> getRow(int rowIndex) {
		Vector<Object> vector = new Vector<Object>();
		for(int i=0; i< this.getColumnCount(); i++) {
			vector.add(this.getValueAt(rowIndex, i));
		}
		return vector;
	}
}
