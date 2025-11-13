package view;

import javax.swing.JTextField;
import javax.swing.text.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class JTextFieldLimit extends JTextField {
	private static final long serialVersionUID = 1L;
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
	private static final Logger logger = LogManager.getLogger(LimitDocumentFilter.class);

	private int limit;
        
    public LimitDocumentFilter(int limit) {
    	super();
        if (limit <= 0) {
            throw new IllegalArgumentException("Limit can not be less than 0");
        }
        this.limit = limit;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
      	if (text == null) {
      		return;
      	}
    	
        try {
        	int currentLength = fb.getDocument().getLength();
            int overLimit = (currentLength + text.length()) - limit - length;
            
            if (overLimit > 0) {
                text = text.substring(0, text.length() - overLimit);
            }
            if (text.length() > 0) {
                super.replace(fb, offset, length, text, attrs);
            }
        }catch(NullPointerException e)
        {
        	logger.error(e.getMessage());
        }
        catch(Exception e)
        {
        	logger.error(e.getMessage());
        }
    	
    }
}

