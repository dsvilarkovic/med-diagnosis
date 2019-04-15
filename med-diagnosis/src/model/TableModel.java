package model;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0)
			return false;
		return true;
	}
}
