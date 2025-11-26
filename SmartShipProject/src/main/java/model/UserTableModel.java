package model;

import javax.swing.table.AbstractTableModel;
import java.util.*;
import java.util.stream.Collectors;

public class UserTableModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    private final List<User> users;                 // current data
    private final List<User> originalSnapshot;      // original copy to compare

    // row index -> set of modified column indices
    private final Map<Integer, Set<Integer>> modifiedByRow = new HashMap<>();

    private final String[] columns = {
        "TRN", "First Name", "Last Name", "Password", "Contact Number", "Email"
    };

    public UserTableModel(List<User> users) {
        this.users = Objects.requireNonNull(users, "users cannot be null");
        this.originalSnapshot = users.stream().map(UserTableModel::copyUser).collect(Collectors.toList());
    }

    @Override public int getRowCount() { return users.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int col) { return columns[col]; }
    @Override public Class<?> getColumnClass(int col) { return String.class; } // all are strings

    @Override
    public Object getValueAt(int row, int col) {
        User u = users.get(row);
        return switch (col) {
            case 0 -> u.getTrn();
            case 1 -> u.getFirstName();
            case 2 -> u.getLastName();
            case 3 -> u.getPassword();
            case 4 -> u.getContactNum();
            case 5 -> u.getEmail();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        // TRN (col 0) is never editable
        return col != 0;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        String newVal = value == null ? "" : value.toString();
        User u = users.get(row);

        switch (col) {
            case 1 -> u.setFirstName(newVal);
            case 2 -> u.setLastName(newVal);
            case 3 -> u.setPassword(newVal);
            case 4 -> u.setContactNum(newVal);
            case 5 -> u.setEmail(newVal);
            default -> { /* no-op for TRN */ }
        }

        // Update modified flags based on comparison with original snapshot
        updateModifiedFlag(row, col);

        // Tell the table to repaint this cell immediately → renderer will set green
        fireTableCellUpdated(row, col);
    }

    /** Cell modified? Used by renderer */
    public boolean isCellModified(int row, int col) {
        return modifiedByRow.getOrDefault(row, Collections.emptySet()).contains(col);
    }

    /** Rows that have any modified cells */
    public List<User> getModifiedUsers() {
        List<User> modified = new ArrayList<>();
        for (int row = 0; row < users.size(); row++) {
            Set<Integer> cols = modifiedByRow.get(row);
            if (cols != null && !cols.isEmpty()) {
                modified.add(users.get(row));
            }
        }
        return modified;
    }

    /** Clear all modified marks after successful save */
    public void clearModifiedMarks() {
        modifiedByRow.clear();
        // refresh all cells
        fireTableDataChanged();
        // also refresh snapshot to new "original"
        for (int i = 0; i < users.size(); i++) {
            originalSnapshot.set(i, copyUser(users.get(i)));
        }
    }

    
    
    
    // --- helpers ---

    private void updateModifiedFlag(int row, int col) {
        boolean changed = !Objects.equals(getValueFrom(originalSnapshot.get(row), col),
                                          getValueFrom(users.get(row), col));
        Set<Integer> cols = modifiedByRow.computeIfAbsent(row, r -> new HashSet<>());
        if (changed) {
            cols.add(col);
        } else {
            cols.remove(col);
            if (cols.isEmpty()) {
                modifiedByRow.remove(row);
            }
        }
    }

    private static User copyUser(User src) {
        User u = new User();
        u.setTrn(src.getTrn());
        u.setFirstName(src.getFirstName());
        u.setLastName(src.getLastName());
        u.setPassword(src.getPassword());
        u.setContactNum(src.getContactNum());
        u.setEmail(src.getEmail());
        return u;
    }

    private static String getValueFrom(User u, int col) {
        return switch (col) {
            case 0 -> u.getTrn();
            case 1 -> u.getFirstName();
            case 2 -> u.getLastName();
            case 3 -> u.getPassword();
            case 4 -> u.getContactNum();
            case 5 -> u.getEmail();
            default -> null;
        };
    }
}

