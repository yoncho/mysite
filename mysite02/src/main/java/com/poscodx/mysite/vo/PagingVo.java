package com.poscodx.mysite.vo;

public class PagingVo {
	private int startPage;
	private int endPage;
	private int currentPage;
	private int beforePage;
	private int nextPage;
	private int totalPage;
	private int pagePerStep;
	
	public PagingVo(int totalPage, int currentPage, int pagePerStep) {
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.pagePerStep = pagePerStep;
		settingPagingInfo();
	}
	public int getPagePerStep() {
		return pagePerStep;
	}

	public void setPagePerStep(int pagePerStep) {
		this.pagePerStep = pagePerStep;
	}

	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		
	}
	public int getBeforePage() {
		return beforePage;
	}
	public void setBeforePage(int beforePage) {
		this.beforePage = beforePage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	
	public void settingPagingInfo() {
		int stepNo = (int)Math.ceil((double)currentPage / (double)pagePerStep);
		this.startPage = (stepNo - 1) * pagePerStep + 1;
		this.endPage = (startPage + pagePerStep - 1)> totalPage ? totalPage:(startPage + pagePerStep - 1);
		this.beforePage = startPage > pagePerStep ? startPage - 1:0;
		this.nextPage = endPage < totalPage ? endPage + 1: 0;
	}
}
