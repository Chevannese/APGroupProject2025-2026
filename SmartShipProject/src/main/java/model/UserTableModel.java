package model;

import javax.swing.table.DefaultTableModel;
import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserTableModel extends DefaultTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> users;
    private List<User> originalUsers; // To track changes
    private boolean[][] modifiedCells;
    
    private final String[] columnNames = {
        "TRN", "First Name", "Last Name", "Email", "Phone", "User Type"
    };

    public UserTableModel(List<User> users) {
        this.users = new ArrayList<>(users);
        this.originalUsers = new ArrayList<>();
        for (User user : users) {
            this.originalUsers.add(new User(user)); // Deep copy for comparison
        }
        this.modifiedCells = new boolean[users.size()][columnNames.length];
    }

    public void setUsers(List<User> users) {
        this.users = new ArrayList<>(users);
        this.originalUsers = new ArrayList<>();
        for (User user : users) {
            this.originalUsers.add(new User(user));
        }
        this.modifiedCells = new boolean[users.size()][columnNames.length];
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users != null ? users.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (row >= users.size()) return null;
        
        User user = users.get(row);
        return switch (column) {
            case 0 -> user.getTrn();
            case 1 -> user.getFirstName();
            case 2 -> user.getLastName();
            case 3 -> user.getEmail();
            case 4 -> user.getContactNum();
            case 5 -> getUserType(user);
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        if (row >= users.size()) return;
        
        User user = users.get(row);
        String stringValue = value != null ? value.toString() : "";
        
        switch (column) {
            case 1 -> user.setFirstName(stringValue);
            case 2 -> user.setLastName(stringValue);
            case 3 -> user.setEmail(stringValue);
            case 4 -> user.setContactNum(stringValue);
            default -> { return; }
        }
        
        modifiedCells[row][column] = true;
        fireTableCellUpdated(row, column);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Only allow editing of name, email, and phone columns
        return column >= 1 && column <= 4;
    }

    public boolean isCellModified(int row, int column) {
        if (row < modifiedCells.length && column < modifiedCells[row].length) {
            return modifiedCells[row][column];
        }
        return false;
    }

    public List<User> getModifiedUsers() {
        List<User> modified = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < columnNames.length; j++) {
                if (modifiedCells[i][j]) {
                    modified.add(users.get(i));
                    break; // Add user once if any cell is modified
                }
            }
        }
        return modified;
    }

    public void clearModifiedMarks() {
        this.modifiedCells = new boolean[users.size()][columnNames.length];
        fireTableDataChanged();
    }

    private String getUserType(User user) {
        if (user instanceof model.Customer) return "Customer";
        if (user instanceof model.Driver) return "Driver";
        if (user instanceof model.Clerk) return "Clerk";
        return "User";
    }
}