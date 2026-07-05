package com.jeesung.dollarwatch.board;

import java.math.BigDecimal;
import java.util.List;

public interface CommentMapper {
	
	public abstract int write(Comment c);
	public abstract List<Comment> getComments(BigDecimal c_b_no);
	public abstract int delete(Comment c);
	public abstract List<Comment> getComments(Board b);

}
