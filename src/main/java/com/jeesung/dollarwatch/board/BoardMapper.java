package com.jeesung.dollarwatch.board;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BoardMapper {
	public abstract int write(Board b);
	public abstract List<Board> getBoards();
//	public abstract Board getBoard(Board b);
	public abstract Board getBoard(@Param("b_no") BigDecimal b_no);
	public abstract int delete(Board b);

}
