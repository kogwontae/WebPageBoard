package com.myspring.pro27.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro27.board.dao.ReplyDAO;
import com.myspring.pro27.board.vo.ReplyVO;

@Service("replyService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReplyService {
	@Autowired
	ReplyDAO replyDAO;
	
	public List<ReplyVO> listReply(int articleNO) throws Exception{
		List<ReplyVO> replyList = replyDAO.selectReplyList(articleNO);
		return replyList;
	}
	
	public int addNewReply(Map replyMap, int articleNO) throws Exception{
		return replyDAO.insertNewReply(replyMap, articleNO);
	}
	
	public void removeReply(int replyNO) throws Exception{
		replyDAO.deleteReply(replyNO);
	}

}
