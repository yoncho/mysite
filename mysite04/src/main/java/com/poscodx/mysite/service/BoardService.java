package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public List<BoardVo> getContentsList() {
		return boardRepository.findAll();
	}

	public int findLastNoOfGroup() {
		return boardRepository.findLastNoOfGroup();
	}

	public BoardVo findByNo(int boardNo) {
		return boardRepository.findByNo(boardNo);
	}

	public void insert(BoardVo vo) {
		boardRepository.insert(vo);
	}

	public void updateHitByNo(int boardNo) {
		boardRepository.updateHitByNo(boardNo);
	}

	public void updateStateByNo(int boardNo) {
		boardRepository.updateStateByNo(boardNo);
	}

	public void update(BoardVo boardVo) {
		boardRepository.update(boardVo);
	}

	public int findTotalCount(String keyword) {
		if (keyword == null)
		{
			return boardRepository.findTotalCount();
		}else {
			return boardRepository.findTotalCountByKeyword(keyword); 
		}
	}

	public List<BoardVo> findByRange(int boardCountPerPage, int currentPage, String keyword){
		if (keyword == null) {
			return boardRepository.findByRange(boardCountPerPage, currentPage);
		}else {
			return boardRepository.findByRangeAndKeyword(boardCountPerPage, currentPage, keyword);
		}
	}
}
