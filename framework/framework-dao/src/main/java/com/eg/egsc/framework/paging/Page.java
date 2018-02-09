/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.framework.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 分页JOPO
 * 
 * @author douguoqiang
 * @since 2018年1月19日
 * @param <T> 实体类
 */
public class Page<T> extends ArrayList<T> implements Serializable {

	private static final long serialVersionUID = -8123039538589235324L;

	/** 当前页 */
	protected int currentPage = 1;

	/** 每页记录数，默认10条 */
	protected int pageSize = 10;

	/** 记录总数 */
	protected long total;

	/** 页数 */
	protected int pageCount;

	/** 当前页的起始索引,从1开始 */
	protected int start = 1;

	/** 当前页的结尾索引 */
	protected int end;

	/**
	 * 获取当前页
	 * 
	 * @return 当前页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 *            当前页
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return 每页记录数
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页记录数
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取记录总数
	 * 
	 * @return 记录总数
	 */
	public long getCount() {
		return total;
	}

	/**
	 * 设置记录总数
	 * 
	 * @param count
	 *            记录总数
	 */
	public void setCount(long count) {
		this.total = count;

		if (count > 0) {
			// 计算页数
			this.pageCount = (int) (this.total / this.pageSize);

			if (this.total % this.pageSize > 0) {
				this.pageCount++;
			}

			// 调整当前页
			if (this.currentPage > this.pageCount) {
				this.currentPage = this.pageCount;
			}

			// 计算当前页的索引
			this.start = (this.currentPage - 1) * this.pageSize + 1;
			this.end = this.start + this.pageSize - 1;
		}
	}

	/**
	 * 获取页数
	 * 
	 * @return 页数
	 */
	public int getPages() {
		return pageCount;
	}

	/**
	 * @Return the int start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @Param int start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @Return the int end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @Param int end to set
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return this;
	}

	public void setRows(List<T> rows) {
		this.clear();
		this.addAll(rows);
	}

}
