package com.jeesung.dollarwatch.exchange;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class ExchangeController {
	@Autowired
	private ExchangeService eService;
	@Autowired
	private ExchangeDAO eDAO;
	@Autowired
	private ExchangeTrendService etService;
	@RequestMapping(value = "/exchange.go", method=RequestMethod.GET)
	public String exchangeGo(HttpServletRequest req) {
		req.setAttribute("cp", "home.jsp");
		return "index";
	}
	@RequestMapping(value="/exchange.rate.getJSON",method=RequestMethod.GET)
	@ResponseBody
	public ExchangeRate getRate(ExchangeSearch search, HttpServletRequest req) {
		ExchangeRate er = eService.getRate(search);
		if ("success".equals(er.getResult())) {
			eDAO.saveSearchLog(er, req);
		}
		return er;
	}
	@RequestMapping(value="/exchange.trend.getJSON", method=RequestMethod.GET)
	@ResponseBody
	public ExchangeTrendResult getTrend(ExchangeSearch search) {
		return etService.getTrend(search);
	}

}
