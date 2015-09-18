package com.builder.overlapView;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitedDocument extends PlainDocument {

	private static final long serialVersionUID = 1L;
	private int limit;
	
	public LimitedDocument() {
		limit = -1;
	}
	
	public LimitedDocument(int limit) {
		this.limit = limit;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
 
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		
		if (getLimit() < 0 || getLength() < getLimit()) {
			super.insertString(offs, str, a);
		}
	}
}
