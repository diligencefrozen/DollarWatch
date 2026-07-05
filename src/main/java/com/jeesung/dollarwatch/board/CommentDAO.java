package com.jeesung.dollarwatch.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jeesung.dollarwatch.member.Member;
@Repository
public class CommentDAO {
	@Autowired
	private SqlSession ss;
	public void write(Comment c, HttpServletRequest req) {
		try {
			Member loginMember = getLoginMember(req);
			if (loginMember == null) {
				req.setAttribute("result", "로그인 후 댓글을 작성할 수 있습니다.");
				return;
			}
			if (c == null || c.getC_b_no() == null) {
				req.setAttribute("result", "댓글을 작성할 게시글 정보가 없습니다.");
				return;
			}
			String content = clean(c.getC_content());
			if (content.length() < 1 || content.length() > 500) {
				req.setAttribute("result", "댓글은 1~500자로 입력해주세요.");
				return;
			}
			c.setC_content(content);
			c.setC_writer(loginMember.getM_id());
			if (ss.getMapper(CommentMapper.class).write(c)==1) {
				req.setAttribute("result", "댓글이 등록되었습니다.");
			} else {
				req.setAttribute("result", "댓글 등록 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글 등록 실패");
		}
	}
	public void delete(Comment c, HttpServletRequest req) {
		try {
			Member loginMember = getLoginMember(req);
			if (loginMember == null) {
				req.setAttribute("result", "로그인 후 댓글을 삭제할 수 있습니다.");
				return;
			}
			if (c == null || c.getC_no() == null) {
				req.setAttribute("result", "삭제할 댓글 정보가 없습니다.");
				return;
			}
			c.setC_writer(loginMember.getM_id());
			if (ss.getMapper(CommentMapper.class).delete(c)==1) {
				req.setAttribute("result", "댓글이 삭제되었습니다.");
			} else {
				req.setAttribute("result", "본인이 작성한 댓글만 삭제할 수 있습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "댓글 삭제 실패");
		}
	}
	private Member getLoginMember(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return null;
		}
		return (Member) session.getAttribute("loginMember");
	}
	
	private String clean(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}
	
	public void getComments(Board b, HttpServletRequest req) {
		try {
//			req.setAttribute("comments", ss.getMapper(CommentMapper.class).getComments(b));
			if (b == null || b.getB_no() == null) {
				req.setAttribute("comments", null);
				return;
			}
			req.setAttribute("comments", ss.getMapper(CommentMapper.class).getComments(b.getB_no()));
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("comments", null);
		}
	}
	

}
