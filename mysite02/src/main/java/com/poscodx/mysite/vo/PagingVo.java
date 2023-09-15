package com.poscodx.mysite.vo;

public class PagingVo {
	private int startPage;
	private int endPage;
	private int currentPage;
	private int beforePage;
	private int nextPage;
	private int totalPage;
	private int pagePerStep;
	private int boardCountPerPage;
	private int totalBoardCount;
	private int boardStartNoPerPage;
	public PagingVo(int totalBoardCount, int currentPage, int pagePerStep, int boardCountPerPage) {
		this.totalBoardCount = totalBoardCount;
		this.currentPage = currentPage;
		this.pagePerStep = pagePerStep;
		this.boardCountPerPage = boardCountPerPage;
		
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
	public int getBoardCountPerPage() {
		return boardCountPerPage;
	}
	public void setBoardCountPerPage(int boardCountPerPage) {
		this.boardCountPerPage = boardCountPerPage;
	}
	public int getTotalBoardCount() {
		return totalBoardCount;
	}
	public void setTotalBoardCount(int totalBoardCount) {
		this.totalBoardCount = totalBoardCount;
	}
	public int getBoardStartNoPerPage() {
		return boardStartNoPerPage;
	}
	public void setBoardStartNoPerPage(int boardStartNoPerPage) {
		this.boardStartNoPerPage = boardStartNoPerPage;
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
		totalPage =(int) Math.ceil((double)totalBoardCount / (double)boardCountPerPage);
		int stepNo = (int)Math.ceil((double)currentPage / (double)pagePerStep);
		startPage = (stepNo - 1) * pagePerStep + 1;
		endPage = (startPage + pagePerStep - 1)> totalPage ? totalPage:(startPage + pagePerStep - 1);
		beforePage = startPage > pagePerStep ? startPage - 1:0;
		nextPage = endPage < totalPage ? endPage + 1: 0;
		boardStartNoPerPage = totalBoardCount - (currentPage - 1)*boardCountPerPage;
		System.out.println(boardStartNoPerPage);
	}
}
