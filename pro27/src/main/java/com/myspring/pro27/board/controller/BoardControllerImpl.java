package com.myspring.pro27.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro27.board.service.BoardService;
import com.myspring.pro27.board.service.ReplyService;
import com.myspring.pro27.board.vo.ArticleVO;
import com.myspring.pro27.board.vo.ReplyVO;
import com.myspring.pro27.member.vo.MemberVO;


@Controller("boardController")
public class BoardControllerImpl  implements BoardController{
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ReplyVO replyVO;
	
	String strURI = "";
	
	//掲示板の全Listを呼ぶ
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response,
									@RequestParam(value="nowPage", required=false)String nowPage) throws Exception {
									//現在のページ
		
		if (nowPage == null) {
			nowPage = "1";
		}
		int cntPage = 5; //下に数字で並ばれるページの数(1 2 3 4 5) 
		int cntPerPage = 10; //一つのページにある文の数
		int total = boardService.countBoard(); //掲示板の文の総計
		
		int lastPage = calcLastPage(total, cntPerPage); //最後のページ
		
		//掲示文の下に数字で表示される初めのページと最後のページ
		int endPage = calcEndPage(Integer.parseInt(nowPage), cntPage, lastPage); 
		int startPage = calcStartPage(endPage, cntPage);
		
		//SQLクエリに送られるStart, End
		int end = calcEnd(Integer.parseInt(nowPage), cntPerPage); 
		int start = calcStart(end, cntPerPage);
		
//		DBに送るStart, EndをMapping
		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("start", start);
		pagingMap.put("end", end);
		
//		掲示板の全投稿文をListに設定、ModelAndViewにMappingする。
		Map articlesMap = boardService.listArticles(pagingMap);
		
		String viewName = (String)request.getAttribute("viewName"); //		Interceptorを使ってreturnするViewの名前をもらう
		ModelAndView mav = new ModelAndView(viewName);
		
//		必要な情報をModelAndViewのオブジェクトにMappingする
		mav.addObject("articlesMap", articlesMap);
		mav.addObject("nowPage", Integer.parseInt(nowPage));
		mav.addObject("cntPerPage", cntPerPage);
		mav.addObject("total", total);
		mav.addObject("lastPage", lastPage);
		mav.addObject("endPage", endPage);
		mav.addObject("startPage", startPage);
		
		return mav;
		
	}
	
	//　最後のページを求める
	public int calcLastPage(int total, int cntPerPage) {
		int lastPage = (int) Math.ceil((double)total / (double)cntPerPage);
		return lastPage;
	}
	
	//	掲示文の下に数字で表示される初めのページと最後のページ
	public int calcEndPage(int nowPage, int cntPage, int lastPage) {
		int endPage = ((int)Math.ceil((double)nowPage / (double)cntPage)) * cntPage;
		if (lastPage < endPage) {
			endPage = lastPage;
		}
		return endPage;
	}
	public int calcStartPage(int endPage, int cntPage) {
		int startPage = endPage - cntPage + 1;
		if(startPage < 1) {
			startPage = 1;
		}
		return startPage;
	}
	
	//	DBに送られるStart, End
	public int calcEnd(int nowPage, int cntPerPage) {
		int end = nowPage * cntPerPage; 
		return end;
	}
	public int calcStart(int end, int cntPerPage) {
		int start = end - cntPerPage + 1; 
		return start;
	}
	
	//新しい文を投稿
	@Override
	@RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
	public ResponseEntity addNewArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		Map<String,Object> articleMap = new HashMap<String, Object>();
//　　　requestもらった name達を articleMapにMapping
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=request.getParameter(name);
			articleMap.put(name,value);
		}
		
//		SessionのID情報をMapping
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String id = memberVO.getId();
		articleMap.put("id", id);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
//			articleMapを送って、DBに新しい文を登録
			int articleNO = boardService.addNewArticle(articleMap);
			
//			文の登録が成功した時
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='"+request.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			
//			エラーの時
			message = " <script>";
			message +=" alert('오류가 발생했습니다. 다시 시도해 주세요.');";
			message +=" location.href='"+request.getContextPath()+"/board/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	//クリックした投稿文を見せる＋コメントも見せる
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		this.strURI = request.getRequestURI();
		
//		FormからもらったarticleNOを使い、クリックした投稿文とその文のコメントをModelAndViewにMapping
		articleVO = boardService.viewArticle(articleNO);
		List replyList = replyService.listReply(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("article", articleVO);
		mav.addObject("replyList", replyList);
		return mav;
	}
	
//	投稿文にあたる全コメントを見せる
	@RequestMapping(value="/board/addReply.do" ,method = RequestMethod.GET)
	public ModelAndView addReply(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		Map<String,Object> replyMap = new HashMap<String, Object>();
		
		//コメントの内容のMapping
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=request.getParameter(name);
			replyMap.put(name,value);
		}
		replyMap.remove("articleNO");
		
		//writerIDのMapping
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String writerID = memberVO.getId();
		replyMap.put("writerID", writerID);
		
		//articleNOのMapping
		replyMap.put("articleNO", articleNO);
		
//		コメントのHashマップを送って、/board/viewArticle.doに戻る
		int replyNO = replyService.addNewReply(replyMap);
		
		ModelAndView mav = new ModelAndView("redirect:/board/listArticles.do");
		return mav;
	}
	
//	入力Formに移動する
	@RequestMapping(value = "/board/*Form.do", method = {RequestMethod.GET, RequestMethod.POST})
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		各Formに移動できるよう、viewNameを使う
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
//	投稿文を修正する
	 @RequestMapping(value="/board/modArticle.do" ,method = RequestMethod.POST)
	 @ResponseBody
	  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
			  							HttpServletResponse response) throws Exception{
		 
//		 addNewArticle.doと同じプロセス
	    multipartRequest.setCharacterEncoding("utf-8");
		Map<String,Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name,value);
		}
		
		String articleNO=(String)articleMap.get("articleNO");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    try {
	       boardService.modArticle(articleMap);

	       message = "<script>";
		   message += " alert('글을 수정했습니다.');";
		   message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
		   message +=" </script>";
	       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    }catch(Exception e) {
	      
	      message = "<script>";
		  message += " alert('오류가 발생했습니다.');";
		  message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
		  message +=" </script>";
	      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    }
	    return resEnt;
	  }
	 
	//文を削除
	  @Override
	  @RequestMapping(value="/board/removeArticle.do" ,method = RequestMethod.POST)
	  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
			  								HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			
//			FormからもらったarticleNOでDBの投稿文のFieldを削除
			boardService.removeArticle(articleNO);
			
			message = "<script>";
			message += " alert('글이 삭제되었습니다.');";
			message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		       
		}catch(Exception e) {
			message = "<script>";
			message += " alert('오류가 발생하였습니다.');";
			message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		    e.printStackTrace();
		}
		return resEnt;
	  }  
	  
//	  コメントの削除
	  @RequestMapping(value="/board/removeReply.do" ,method = RequestMethod.POST)
	  public ModelAndView  removeReply(@RequestParam("replyNO") int replyNO,
			  							HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html; charset=UTF-8");
		
//		コメントのPrimary KeyであるreplyNOを使い、それにあたるコメントを削除
		replyService.removeReply(replyNO);
		ModelAndView mav = new ModelAndView("redirect:/board/listArticles.do");
		return mav;
	  }	
}