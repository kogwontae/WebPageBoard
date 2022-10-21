package com.myspring.pro27.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro27.board.vo.ReplyVO;

@Repository("replyDAO")
public class ReplyDAO {
	@Autowired
	private SqlSession sqlSession;
	
//	コメントの内容
	public List selectReplyList(int articleNO) throws DataAccessException{
		List<ReplyVO> replyList = sqlSession.selectList("mapper.reply.selectReply", articleNO);
		return replyList;
	}
	
//	コメント作成
	public int insertNewReply(Map replyMap, int articleNO) throws DataAccessException{
		int replyNO = sqlSession.insert("mapper.reply.selectNewReplyNO", articleNO);
		replyMap.put("replyNO", replyNO);
		sqlSession.insert("mapper.reply.insertNewReply",replyMap);
		return replyNO;
	}
	
//	コメント削除
	public void deleteReply(int replyNO) throws DataAccessException{
		sqlSession.delete("mapper.reply.deleteReply", replyNO);
	}

}
