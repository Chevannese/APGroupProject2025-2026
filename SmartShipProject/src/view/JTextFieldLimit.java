package smartshipurl;

import javax.swing.JTextField;
import javax.swing.text.*;

public class JTextFieldLimit extends JTextField {
    private LimitDocumentFilter filter;
    
    public JTextFieldLimit(int columns) {
        super(columns);
    }

    public JTextFieldLimit(String text) {
        super(text);
    }

    public JTextFieldLimit(String text, int columns) {
        super(text, columns);
    }
    
    public JTextFieldLimit(Document doc, String text, int columns) {
        super(doc, text, columns);
    }
    
    public void setLimit(int limit) {
        AbstractDocument doc = (AbstractDocument) this.getDocument();
        filter = new LimitDocumentFilter(limit);
        doc.setDocumentFilter(filter);
    }
}

class LimitDocumentFilter extends DocumentFilter {
    private int limit;
        
    public LimitDocumentFilter(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit can not be less than 0");
        }
        this.limit = limit;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        int currentLength = fb.getDocument().getLength();
        int overLimit = (currentLength + text.length()) - limit - length;
        
        if (overLimit > 0) {
            text = text.substring(0, text.length() - overLimit);
        }
        if (text.length() > 0) {
            super.replace(fb, offset, length, text, attrs); 
        }
    }
}
