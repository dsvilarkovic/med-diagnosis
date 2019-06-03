package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.TableModel;

public class TableHandler {

	private TableModel tableModel;

	private JTable tableView;
	
	/**
	 * @param width sirina tabele
	 * @param height visina tabele
	 */
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
	/**
	 * Metoda za preuzimanje prve kolone u vidu liste
	 * @return lista vrednosti iz prve kolone
	 */
	public List<String> getContentList() {
		List<String> resultList = new ArrayList<String>();
		for (Object o : (List) tableModel.getDataVector()) {
			Vector v = (Vector) o;
			resultList.add((String) v.get(0));
		}
		return resultList;
	}

	public Map<String, Integer> getContentMap() {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		for (Object o : (List) tableModel.getDataVector()) {
			Vector v = (Vector) o;
			System.out.println(v.get(0).getClass());
			System.out.println(v.get(1).getClass());
			resultMap.put((String) v.get(0),(Integer)v.get(1));
		}
		return resultMap;
	}

	public void insertRow(Vector<Object> row) {
		this.tableModel.addRow(row);
	}

	public void insertContent(List<String> objects) {
		this.tableModel.setRowCount(0);
		for (String object : objects) {
			Vector row = new Vector();
			row.add(object);

			insertRow(row);
		}
	}

	public void insertContent(List<String> objects, String text) {
		this.tableModel.setRowCount(0);
		for (String object : objects) {
			Vector row = new Vector();
			row.add(object);
			row.add(text);
			insertRow(row);
		}

	}

	public void addColumn(String name) {
		this.tableModel.addColumn(name);
	}
	
	public void clearTable() {
		tableModel.setRowCount(0);
	}
}
