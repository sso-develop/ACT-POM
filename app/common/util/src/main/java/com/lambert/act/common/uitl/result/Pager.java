package com.lambert.act.common.uitl.result;

import java.io.Serializable;
import java.util.List;

public class Pager implements Serializable {

	private static final long serialVersionUID = 8488577294959080L;
	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制

	// 排序方式（递增、递减）
	public static enum Order {
		asc, desc
	}

	private int pageNumber = 1;// 当前页码
	private int pageSize = 10;// 每页记录数

	private int totalCount;// 总记录数
	private List<?> result;// 返回结果



	public Pager() {
		super();
	}



	public Pager(int pageNumber) {
		super();
		this.pageNumber = pageNumber;
	}




	public Pager(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}



	// 获取总页数
	public int getPageCount() {
		int pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if (pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	/**
     * Setter method for property <tt>endIndex</tt>.
     *
     * @param endIndex value to be assigned to property endIndex
     */
    public int getEndIndex() {
       return this.pageSize;
    }

	public int getBeginIndex() {
		return (this.pageNumber - 1) * this.pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
	
		result = prime * result + pageNumber;
		result = prime * result + pageSize;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + totalCount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pager other = (Pager) obj;
		
		if (pageNumber != other.pageNumber)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		
		if (totalCount != other.totalCount)
			return false;
		return true;
	}

}