package com.myspring.pro27.board.service;

import java.util.List;
import java.util.Map;

import com.myspring.pro27.board.vo.ArticleVO;

public interface BoardService {
	//掲示板の全Listを呼ぶ
	public List listArticles(Map<String, Integer> pagingMap) throws Exception;
	//掲示板の文のTotal数
	public int countBoard() throws Exception;
	//新しい文を投稿
	public int addNewArticle(Map articleMap) throws Exception;
	//クリックした投稿文を見せる＋コメントも見せる
	public ArticleVO viewArticle(int articleNO) throws Exception;
	//文を修正
	public void modArticle(Map articleMap) throws Exception;
	//文を削除
	public void removeArticle(int articleNO) throws Exception;
}