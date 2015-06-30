package com.qq.frame;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class PwdDoc extends PlainDocument{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MAX_LENGTH=16;
	@Override
	public void insertString(int offs, String str, AttributeSet a)
			throws BadLocationException {
		if(this.getLength()>MAX_LENGTH)
			return ;
		else
			super.insertString(offs, str, a);
	}
	
	
}
