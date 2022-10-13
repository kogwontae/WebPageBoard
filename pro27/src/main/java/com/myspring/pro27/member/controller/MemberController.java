package com.myspring.pro27.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.vo.MemberVO;

//웹에서 처리해야 할 데이터를 받고, 이 데이터를 담당할 service를 선택하여 호출한다. 그리고 처리한 데이터를 다음 페이지에서 볼 수 있게 세팅하며, 이동할 페이지를 리턴한다.

//Member関連Controller
public interface MemberController {
	
	//全会員情報を照会
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception;
	//会員加入
	public ModelAndView addMember(@ModelAttribute("info") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
	//会員削除
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	//会員情報修正
//	public ModelAndView modMember(@ModelAttribute("mod") MemberVO modMemberVO,
//							  RedirectAttributes rAttr,
//							  HttpServletRequest request, HttpServletResponse response) throws Exception;
	//ログイン
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
                              RedirectAttributes rAttr,
                              HttpServletRequest request, HttpServletResponse response) throws Exception;
	//ログアウト
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
}