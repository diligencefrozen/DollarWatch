package com.jeesung.dollarwatch.favorite;

import java.util.List;

public interface FavoriteMapper {
    public abstract int addFavorite(Favorite f);
    public abstract List<Favorite> getFavorites(String f_member_id);
    public abstract int deleteFavorite(Favorite f);
    public abstract int countFavorite(Favorite f);
}
