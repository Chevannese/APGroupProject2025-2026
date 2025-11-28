package model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ShipmentTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final String[] columns = 
        {"Package No", "Customer ID", "Name", "Receiver Addr", "Destination", "Weight", "Status"};


    private List<Shipment> shipments;

    public ShipmentTableModel(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
        fireTableDataChanged();
    }

    public Shipment getShipmentAt(int row) {
        return shipments.get(row);
    }

    @Override
    public int getRowCount() {
        return shipments == null ? 0 : shipments.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Shipment s = shipments.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> s.getPackageNo();
            case 1 -> s.getCustID();
            case 2 -> s.getPackageName();
            case 3 -> s.getReceiverAddr();      // <--- NEW
            case 4 -> s.getDestination();
            case 5 -> s.getWeight();
            case 6 -> s.getStatus();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // manager only selects, no edit here
    }
}
