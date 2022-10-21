package com.myspring.pro27.board.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

	@Component("replyVO")
	public class ReplyVO {
		private int replyNO;
		private int articleNO;
		private String writerID;
		private String r_content;
		private Date r_date;
		
		public ReplyVO() {
			System.out.println("ReplyVO 생성");
		}
		
		public int getReplyNO() {
			return replyNO;
		}

		public void setReplyNO(int replyNO) {
			this.replyNO = replyNO;
		}
		
		public int getArticleNO() {
			return articleNO;
		}

		public void setArticleNO(int articleNO) {
			this.articleNO = articleNO;
		}
		
		public String getWriterID() {
			return writerID;
		}

		public void setWriterID(String writerID) {
			this.writerID = writerID;
		}
		
		public String getR_content() {
			return r_content;
		}

		public void setR_content(String r_content) {
			this.r_content = r_content;
		}
		
		public Date getR_date() {
			return r_date;
		}

		public void setR_date(Date r_date) {
			this.r_date = r_date;
		}

	}
	
	