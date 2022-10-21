package com.myspring.pro27.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


public interface BoardController {
	
//掲示板の全Listを呼ぶ	
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception;
//新しい文を投稿
	public ResponseEntity addNewArticle(HttpServletRequest request, HttpServletResponse response) throws Exception;
//クリックした投稿文を見せる＋コメントも見せる
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
		                     		HttpServletRequest request, HttpServletResponse response) throws Exception;
//文を修正
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  HttpServletResponse response) throws Exception;
//文を削除
	public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
                              		HttpServletRequest request, HttpServletResponse response) throws Exception;

}