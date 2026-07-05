package com.jeesung.dollarwatch.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommentController {

    @Autowired
    private CommentDAO cDAO;

    @RequestMapping(value = "/comment.write", method = RequestMethod.POST)
    public String write(Comment c, HttpServletRequest req) {
        cDAO.write(c, req);
        return "redirect:/board.detail?b_no=" + c.getC_b_no();
    }

    @RequestMapping(value = "/comment.delete", method = RequestMethod.POST)
    public String delete(Comment c, HttpServletRequest req) {
        cDAO.delete(c, req);
        return "redirect:/board.detail?b_no=" + c.getC_b_no();
    }
}
