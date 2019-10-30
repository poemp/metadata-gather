package org.poem.code.api.vo.page;


/**
 * @author Administrator
 */
public class Page {

    /**
     * 起始记录下标
     */
    private int beginIndex;
    /**
     * 截止记录下标
     */
    private int endIndex;

    public Page(int beginIndex, int endIndex) {
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
