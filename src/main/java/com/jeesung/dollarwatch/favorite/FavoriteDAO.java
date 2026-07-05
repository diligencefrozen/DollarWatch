package com.jeesung.dollarwatch.favorite;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jeesung.dollarwatch.exchange.ExchangeRate;
import com.jeesung.dollarwatch.exchange.ExchangeSearch;
import com.jeesung.dollarwatch.exchange.ExchangeService;
import com.jeesung.dollarwatch.member.Member;

@Repository
public class FavoriteDAO {

    private static final Set<String> SUPPORTED_CODES = new HashSet<String>(Arrays.asList(
        "USD", "KRW", "JPY", "EUR", "GBP", "CAD", "AUD"
    ));

    @Autowired
    private SqlSession ss;

    @Autowired
    private ExchangeService exchangeService;

    public void addFavorite(Favorite f, HttpServletRequest req) {
        try {
            Member loginMember = getLoginMember(req);

            if (loginMember == null) {
                req.setAttribute("result", "로그인 후 관심목록을 사용할 수 있습니다.");
                return;
            }

            if (!isValidFavorite(f, req)) {
                return;
            }

            f.setF_member_id(loginMember.getM_id());

            if (ss.getMapper(FavoriteMapper.class).countFavorite(f) > 0) {
                req.setAttribute("result", "이미 저장된 통화쌍입니다.");
                return;
            }

            if (ss.getMapper(FavoriteMapper.class).addFavorite(f) == 1) {
                req.setAttribute("result", "관심목록에 추가되었습니다.");
            } else {
                req.setAttribute("result", "관심목록 추가 실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("result", "관심목록 추가 실패");
        }
    }

    public void getFavorites(HttpServletRequest req) {
        try {
            Member loginMember = getLoginMember(req);

            if (loginMember == null) {
                req.setAttribute("favorites", null);
                return;
            }

            List<Favorite> favorites = ss.getMapper(FavoriteMapper.class).getFavorites(loginMember.getM_id());

            for (Favorite f : favorites) {
                ExchangeSearch search = new ExchangeSearch();
                search.setBase(f.getF_base());
                search.setTarget(f.getF_target());

                ExchangeRate er = exchangeService.getRate(search);

                if ("success".equals(er.getResult())) {
                    f.setCurrentRate(new BigDecimal(er.getRate()));
                    f.setRateResult(er.getMessage());
                } else {
                    f.setCurrentRate(null);
                    f.setRateResult(er.getMessage());
                }
            }

            req.setAttribute("favorites", favorites);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("result", "관심목록 조회 실패");
        }
    }

    public void deleteFavorite(Favorite f, HttpServletRequest req) {
        try {
            Member loginMember = getLoginMember(req);

            if (loginMember == null) {
                req.setAttribute("result", "로그인 후 삭제할 수 있습니다.");
                return;
            }

            if (f == null || f.getF_no() == null) {
                req.setAttribute("result", "삭제할 관심목록 정보가 없습니다.");
                return;
            }

            f.setF_member_id(loginMember.getM_id());

            if (ss.getMapper(FavoriteMapper.class).deleteFavorite(f) == 1) {
                req.setAttribute("result", "관심목록에서 삭제되었습니다.");
            } else {
                req.setAttribute("result", "본인의 관심목록만 삭제할 수 있습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("result", "관심목록 삭제 실패");
        }
    }

    private boolean isValidFavorite(Favorite f, HttpServletRequest req) {
        if (f == null) {
            req.setAttribute("result", "관심목록 정보가 없습니다.");
            return false;
        }

        String base = cleanCurrency(f.getF_base());
        String target = cleanCurrency(f.getF_target());
        String memo = cleanMemo(f.getF_memo());

        if (!SUPPORTED_CODES.contains(base) || !SUPPORTED_CODES.contains(target)) {
            req.setAttribute("result", "지원하지 않는 통화입니다.");
            return false;
        }

        if (base.equals(target)) {
            req.setAttribute("result", "서로 다른 통화쌍만 저장할 수 있습니다.");
            return false;
        }

        if (memo.length() == 0) {
            memo = "자주 확인하는 환율";
        }

        if (memo.length() > 100) {
            req.setAttribute("result", "메모는 100자 이하로 입력해주세요.");
            return false;
        }

        f.setF_base(base);
        f.setF_target(target);
        f.setF_memo(memo);
        return true;
    }

    private String cleanCurrency(String s) {
        if (s == null) {
            return "";
        }
        return s.trim().toUpperCase();
    }

    private String cleanMemo(String s) {
        if (s == null) {
            return "";
        }
        return s.trim();
    }

    private Member getLoginMember(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session == null) {
            return null;
        }

        return (Member) session.getAttribute("loginMember");
    }
}
