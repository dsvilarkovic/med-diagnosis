package view;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JTable;

import model.TableModel;

public class TableHandler {
	private TableModel tableModel;
	private JTable tableView;
	
	public TableHandler(int width, int height) {
		this.tableModel = new TableModel();
		this.tableView = new JTable(tableModel);
		this.tableView.setFillsViewportHeight(true);
		this.tableView.setPreferredScrollableViewportSize(new Dimension(width, height));
	}
	
	public TableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}

	public JTable getTableView() {
		return tableView;
	}

	public void setTableView(JTable tableView) {
		this.tableView = tableView;
	}

	public List<String> getContent() {
		List<String> resultList = new ArrayList<String>();
		for(Object o : (List)tableModel.getDataVector()) {
			Vector v = (Vector) o;
			resultList.add((String)v.get(0));
		}
		return resultList;
	}
	
	public void insertRow(Vector<Object> row) {
		this.tableModel.addRow(row);
	}
	
	public void insertContent(List<String> objects)
	{
		this.tableModel.setRowCount(0);
		for(String object: objects) {
			Vector row = new Vector();
			row.add(object);
			
			insertRow(row);
		}		
	}
	
	public void insertContent(List<String> objects, String text)
	{
		this.tableModel.setRowCount(0);
		for(String object: objects) {
			Vector row = new Vector();
			row.add(object);
			row.add(text);
			insertRow(row);
		}
	
	}
	public void addColumn(String name) {
		this.tableModel.addColumn(name);
	}
}
