package io.ride.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Created by IDEA
 * User: ride
 * Date: 17-5-26
 * Time: 下午11:07
 * 文本框限制用户输入内容和长度
 */
public class LimitedDocument extends PlainDocument {
    private int maxLength = -1;                 // 限制最大输入数
    private String allowCharAsString = null;    // 限制输入内容

    public LimitedDocument(int maxLength) {
        super();
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }
        // 如果输入非法字符, 不添加, 直接返回
        if (allowCharAsString != null && str.length() == 1) {
            if (!allowCharAsString.contains(str)) {
                return;
            }
        }
        char[] charVal = str.toCharArray();
        String strOldValue = getText(0, getLength());
        char[] temp = strOldValue.toCharArray();
        // 如果长度超出限制, 不添加, 直接返回
        if (maxLength != -1 && (temp.length + charVal.length > maxLength)) return;

        super.insertString(offs, str, a);
    }

    /**
     * 限制输入的字符
     *
     * @param allowCharAsString 限制输入的字符
     */
    public void setAllowCharAsString(String allowCharAsString) {
        this.allowCharAsString = allowCharAsString;
    }
}
