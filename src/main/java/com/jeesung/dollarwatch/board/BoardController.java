package com.jeesung.dollarwatch.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardDAO bDAO;

    @Autowired
    private CommentDAO cDAO;

    @RequestMapping(value = "/board.go", method = RequestMethod.GET)
    public String boardGo(HttpServletRequest req) {
        bDAO.getBoards(req);
        req.setAttribute("cp", "board.jsp");
        return "index";
    }

    @RequestMapping(value = "/board.write", method = RequestMethod.POST)
    public String write(
            @RequestParam(value = "b_file", required = false) MultipartFile bFile,
            Board b,
            HttpServletRequest req) {
        bDAO.write(b, req, bFile);
        return "redirect:/board.go";
    }

    @RequestMapping(value = "/board.detail", method = RequestMethod.GET)
    public String detail(Board b, HttpServletRequest req) {
        bDAO.getBoard(b, req);
        cDAO.getComments(b, req);
        req.setAttribute("cp", "boardDetail.jsp");
        return "index";
    }

    @RequestMapping(value = "/board.delete", method = RequestMethod.POST)
    public String delete(Board b, HttpServletRequest req) {
        bDAO.delete(b, req);
        return "redirect:/board.go";
    }
}
