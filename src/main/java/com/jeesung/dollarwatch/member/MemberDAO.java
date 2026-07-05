package com.jeesung.dollarwatch.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberDAO {

	@Autowired
	private SqlSession ss;

	public void join(Member m, HttpServletRequest req) {
		try {
			if (!isValidJoinData(m, req)) {
				return;
			}

			Member oldMember = ss.getMapper(MemberMapper.class).getMemberById(m.getM_id());

			if (oldMember != null) {
				req.setAttribute("result", "이미 사용 중인 아이디입니다.");
				return;
			}

			if (ss.getMapper(MemberMapper.class).join(m) == 1) {
				req.setAttribute("result", "회원가입 성공! 로그인해주세요.");
			} else {
				req.setAttribute("result", "회원가입 실패");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "회원가입 실패");
		}
	}

	public void login(Member m, HttpServletRequest req) {
		try {
			if (!isValidLoginData(m, req)) {
				return;
			}

			Member loginMember = ss.getMapper(MemberMapper.class).login(m);

			if (loginMember != null) {
				HttpSession session = req.getSession();
				session.setAttribute("loginMember", loginMember);
				session.setMaxInactiveInterval(60 * 30);

				req.setAttribute("result", loginMember.getM_name() + "님 환영합니다.");
			} else {
				req.setAttribute("result", "아이디 또는 비밀번호가 올바르지 않습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("result", "로그인 실패");
		}
	}

	public void logout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session != null) {
			session.invalidate();
		}

		req.setAttribute("result", "로그아웃 되었습니다.");
	}

	public boolean isLogined(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session == null) {
			return false;
		}

		return session.getAttribute("loginMember") != null;
	}

	public Member getLoginMember(HttpServletRequest req) {
		HttpSession session = req.getSession(false);

		if (session == null) {
			return null;
		}

		return (Member) session.getAttribute("loginMember");
	}

	private boolean isValidJoinData(Member m, HttpServletRequest req) {
		if (m == null) {
			req.setAttribute("result", "회원 정보가 없습니다.");
			return false;
		}

		String id = clean(m.getM_id());
		String pw = clean(m.getM_pw());
		String name = clean(m.getM_name());
		String email = clean(m.getM_email());

		if (!id.matches("^[A-Za-z0-9_]{4,20}$")) {
			req.setAttribute("result", "아이디는 영문, 숫자, 언더바(_)만 사용해서 4~20자로 입력해주세요.");
			return false;
		}

		if (pw.length() < 4 || pw.length() > 20) {
			req.setAttribute("result", "비밀번호는 4~20자로 입력해주세요.");
			return false;
		}

		if (name.length() < 2 || name.length() > 20) {
			req.setAttribute("result", "이름은 2~20자로 입력해주세요.");
			return false;
		}

		if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			req.setAttribute("result", "올바른 이메일 형식으로 입력해주세요.");
			return false;
		}

		m.setM_id(id);
		m.setM_pw(pw);
		m.setM_name(name);
		m.setM_email(email);

		return true;
	}

	private boolean isValidLoginData(Member m, HttpServletRequest req) {
		if (m == null) {
			req.setAttribute("result", "로그인 정보가 없습니다.");
			return false;
		}

		String id = clean(m.getM_id());
		String pw = clean(m.getM_pw());

		if (id.length() == 0 || pw.length() == 0) {
			req.setAttribute("result", "아이디와 비밀번호를 입력해주세요.");
			return false;
		}

		if (id.length() > 20 || pw.length() > 20) {
			req.setAttribute("result", "아이디 또는 비밀번호가 너무 깁니다.");
			return false;
		}

		m.setM_id(id);
		m.setM_pw(pw);

		return true;
	}

	private String clean(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}
}
