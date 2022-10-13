package com.myspring.pro27.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro27.member.vo.MemberVO;

//Database의 data에 접근을 위한 객체. Database에 접근을 하기 위한 로직과 비즈니스 로직을 분리하기 위해 사용. DB를 통해 데이터를 조회하거나 수정 삭제하는 역할
public interface MemberDAO {
	
	 //全会員情報を照会
	 public List selectAllMemberList() throws DataAccessException;
	 //会員加入
	 public int insertMember(MemberVO memberVO) throws DataAccessException ;
	 //会員削除
	 public int deleteMember(String id) throws DataAccessException;
	 //ログイン
	 public MemberVO loginById(MemberVO memberVO) throws DataAccessException;

}
