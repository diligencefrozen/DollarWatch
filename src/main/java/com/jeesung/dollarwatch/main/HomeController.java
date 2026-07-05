package com.jeesung.dollarwatch.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jeesung.dollarwatch.exchange.ExchangeDAO;

@Controller
public class HomeController {

    @Autowired
    private ExchangeDAO eDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest req) {
        prepareHome(req);
        return "index";
    }

    @RequestMapping(value = "/home.go", method = RequestMethod.GET)
    public String homeGo(HttpServletRequest req) {
        prepareHome(req);
        return "index";
    }

    private void prepareHome(HttpServletRequest req) {
        eDAO.getRecentSearchLogs(req);
        req.setAttribute("cp", "home.jsp");
    }
}
