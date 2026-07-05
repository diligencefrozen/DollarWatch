package com.jeesung.dollarwatch.member;

public interface MemberMapper {
    public abstract int join(Member m);
    public abstract Member login(Member m);
    public abstract Member getMemberById(String m_id);
}
