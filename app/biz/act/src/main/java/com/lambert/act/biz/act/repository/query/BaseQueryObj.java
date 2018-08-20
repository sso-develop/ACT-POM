
package com.lambert.act.biz.act.repository.query;

import java.util.Date;

public class BaseQueryObj extends BaseModel {


    protected Date   startDate;


    protected Date   endDate;

    protected int    pageSize       = 10;

    protected int    currentPage    = 1;

    protected int    startRow       = 0;

    protected int    endRow         = 10;

    /**
     * Getter method for property <tt>startDate</tt>.
     * 
     * @return property value of startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Setter method for property <tt>startDate</tt>.
     * 
     * @param startDate value to be assigned to property startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Getter method for property <tt>endDate</tt>.
     * 
     * @return property value of endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setter method for property <tt>endDate</tt>.
     * 
     * @param endDate value to be assigned to property endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     * 
     * @return property value of pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     * 
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        initStartEnd();
    }

    private void initStartEnd() {
        this.startRow = (this.currentPage - 1) * this.pageSize;
        this.endRow = this.currentPage * this.pageSize;
    }

    /**
     * Getter method for property <tt>currentPage</tt>.
     * 
     * @return property value of currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Setter method for property <tt>currentPage</tt>.
     * 
     * @param currentPage value to be assigned to property currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        initStartEnd();
    }

    /**
     * Getter method for property <tt>startRow</tt>.
     * 
     * @return property value of startRow
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * Setter method for property <tt>startRow</tt>.
     * 
     * @param startRow value to be assigned to property startRow
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * Getter method for property <tt>endRow</tt>.
     * 
     * @return property value of endRow
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * Setter method for property <tt>endRow</tt>.
     * 
     * @param endRow value to be assigned to property endRow
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }


}
