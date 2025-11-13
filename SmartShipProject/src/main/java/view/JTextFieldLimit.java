package view;

import javax.swing.JTextField;
import javax.swing.text.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A JTextField that limits the number of characters that can be in the field at once
 */
public class JTextFieldLimit extends JTextField {
	private static final long serialVersionUID = 1L;
    public JTextFieldLimit(int columns) {
        super(columns);
    }
    
    /**
     * @param limit The maximum number of characters allowed.
     */
    public void setLimit(int limit) {
        PlainDocument doc = (PlainDocument) this.getDocument();
        doc.setDocumentFilter(new MaxLengthDocumentFilter(limit));
    }
}

class MaxLengthDocumentFilter extends DocumentFilter {
    private int maxLength;

    public MaxLengthDocumentFilter(int maxLength) {
        if (maxLength < 0) {
            throw new IllegalArgumentException("Maximum length must be non-negative");
        }
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) {
            return;
        }

        // Check the new total length
        int currentLength = fb.getDocument().getLength();
        int insertLength = string.length();
        if (currentLength + insertLength <= maxLength) {
            // If it's within limits, allow the insertion
            super.insertString(fb, offset, string, attr);
        }
        else {
            // If it would exceed the limit, do nothing (or beep)
            // For a better user experience, you could optionally insert
            // only the part of the string that fits.
            // java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) {
            text = "";
        }

        // Calculate the resulting length after the replace
        int currentLength = fb.getDocument().getLength();
        int newLength = currentLength - length + text.length();

        if (newLength <= maxLength) {
            // If it's within limits, allow the replacement
            super.replace(fb, offset, length, text, attrs);
        }
        else {
            // If it would exceed the limit, do nothing (or beep)
            // java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }
}

