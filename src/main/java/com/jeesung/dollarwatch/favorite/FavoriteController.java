package com.jeesung.dollarwatch.favorite;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FavoriteController {

    @Autowired
    private FavoriteDAO fDAO;

    @RequestMapping(value = "/favorite.go", method = RequestMethod.GET)
    public String favoriteGo(HttpServletRequest req) {
        fDAO.getFavorites(req);
        req.setAttribute("cp", "favorite.jsp");
        return "index";
    }

    @RequestMapping(value = "/favorite.add", method = RequestMethod.POST)
    public String addFavorite(Favorite f, HttpServletRequest req) {
        fDAO.addFavorite(f, req);
        fDAO.getFavorites(req);
        req.setAttribute("cp", "favorite.jsp");
        return "index";
    }

    @RequestMapping(value = "/favorite.delete", method = RequestMethod.POST)
    public String deleteFavorite(Favorite f, HttpServletRequest req) {
        fDAO.deleteFavorite(f, req);
        fDAO.getFavorites(req);
        req.setAttribute("cp", "favorite.jsp");
        return "index";
    }
}
