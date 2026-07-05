package com.jeesung.dollarwatch.board;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jeesung.dollarwatch.member.Member;

@Service
public class BoardDAO {

    @Autowired
    private SqlSession ss;

    public void write(Board b, HttpServletRequest req, MultipartFile bFile) {
        String savedFileName = null;

        try {
            Member loginMember = getLoginMember(req);

            if (loginMember == null) {
                req.setAttribute("result", "로그인 후 글을 작성할 수 있습니다.");
                return;
            }

            if (!isValidPostData(b, req)) {
                return;
            }

            savedFileName = saveBoardImage(bFile, req);
            b.setB_writer(loginMember.getM_id());
            b.setB_img(savedFileName);

            if (ss.getMapper(BoardMapper.class).write(b) == 1) {
                req.setAttribute("result", "게시글이 등록되었습니다.");
            } else {
                deleteSavedImage(savedFileName, req);
                req.setAttribute("result", "게시글 등록 실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            deleteSavedImage(savedFileName, req);
            req.setAttribute("result", "게시글 등록 실패");
        }
    }

    public void getBoards(HttpServletRequest req) {
        try {
            req.setAttribute("boards", ss.getMapper(BoardMapper.class).getBoards());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("boards", null);
        }
    }

    public void getBoard(Board b, HttpServletRequest req) {
        try {
            if (b == null || b.getB_no() == null) {
                req.setAttribute("detailBoard", null);
                return;
            }

            req.setAttribute("detailBoard", ss.getMapper(BoardMapper.class).getBoard(b.getB_no()));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("detailBoard", null);
        }
    }

    public void delete(Board b, HttpServletRequest req) {
        try {
            Member loginMember = getLoginMember(req);

            if (loginMember == null) {
                req.setAttribute("result", "로그인 후 삭제할 수 있습니다.");
                return;
            }

            if (b == null || b.getB_no() == null) {
                req.setAttribute("result", "삭제할 게시글 정보가 없습니다.");
                return;
            }

            Board savedBoard = ss.getMapper(BoardMapper.class).getBoard(b.getB_no());
            b.setB_writer(loginMember.getM_id());

            if (ss.getMapper(BoardMapper.class).delete(b) == 1) {
                if (savedBoard != null) {
                    deleteSavedImage(savedBoard.getB_img(), req);
                }
                req.setAttribute("result", "게시글이 삭제되었습니다.");
            } else {
                req.setAttribute("result", "본인이 작성한 글만 삭제할 수 있습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("result", "게시글 삭제 실패");
        }
    }

    private String saveBoardImage(MultipartFile mf, HttpServletRequest req) throws Exception {
        if (mf == null || mf.isEmpty()) {
            return null;
        }

        if (mf.getSize() > 2 * 1024 * 1024) {
            throw new Exception("이미지 용량 초과");
        }

        String originalName = mf.getOriginalFilename();

        if (originalName == null || originalName.trim().length() == 0) {
            return null;
        }

        String lowerName = originalName.toLowerCase();

        if (!(lowerName.endsWith(".jpg")
                || lowerName.endsWith(".jpeg")
                || lowerName.endsWith(".png")
                || lowerName.endsWith(".gif")
                || lowerName.endsWith(".webp"))) {
            throw new Exception("이미지 파일 형식 오류");
        }

        String ext = lowerName.substring(lowerName.lastIndexOf("."));
        String fileName = "board_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replace("-", "") + ext;
        String path = req.getSession().getServletContext().getRealPath("/resources/upload/board");
        File folder = new File(path);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File saveFile = new File(folder, fileName);
        mf.transferTo(saveFile);
        return fileName;
    }

    private void deleteSavedImage(String fileName, HttpServletRequest req) {
        try {
            if (fileName == null || fileName.trim().length() == 0) {
                return;
            }

            String path = req.getSession().getServletContext().getRealPath("/resources/upload/board");
            File f = new File(path, fileName);

            if (f.exists()) {
                f.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidPostData(Board b, HttpServletRequest req) {
        if (b == null) {
            req.setAttribute("result", "게시글 정보가 없습니다.");
            return false;
        }

        String title = clean(b.getB_title());
        String content = clean(b.getB_content());

        if (title.length() < 2 || title.length() > 80) {
            req.setAttribute("result", "제목은 2~80자로 입력해주세요.");
            return false;
        }

        if (content.length() < 5 || content.length() > 1000) {
            req.setAttribute("result", "내용은 5~1000자로 입력해주세요.");
            return false;
        }

        b.setB_title(title);
        b.setB_content(content);
        return true;
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
}
