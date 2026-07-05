package com.jeesung.dollarwatch.exchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jeesung.dollarwatch.member.Member;

@Repository
public class ExchangeDAO {

    @Autowired
    private SqlSession ss;

    public void saveSearchLog(ExchangeRate er, HttpServletRequest req) {
        try {
            if (er == null) {
                return;
            }

            er.setMemberId(resolveMemberId(req));
            ss.getMapper(ExchangeMapper.class).saveSearchLog(er);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getRecentSearchLogs(HttpServletRequest req) {
        try {
            String memberId = resolveMemberId(req);
            req.setAttribute(
                "recentSearchLogs",
                ss.getMapper(ExchangeMapper.class).getRecentSearchLogs(memberId)
            );
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("recentSearchLogs", null);
        }
    }

    private String resolveMemberId(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);

            if (session != null) {
                Member loginMember = (Member) session.getAttribute("loginMember");

                if (loginMember != null) {
                    return loginMember.getM_id();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "guest";
    }
}
