package com.myspring.pro27.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro27.board.vo.ArticleVO;


public interface BoardDAO {
	//掲示板の全Listを呼ぶ
	public List selectAllArticlesList(Map pagingMap) throws DataAccessException;
	//新しい文を投稿
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	//クリックした投稿文を見せる＋コメントも見せる
	public ArticleVO selectArticle(int articleNO) throws DataAccessException;
	//文を修正
	public void updateArticle(Map articleMap) throws DataAccessException;
	//文を削除
	public void deleteArticle(int articleNO) throws DataAccessException;
	//掲示板の文のTotal数
	public int selectTotArticles() throws DataAccessException;
	
}