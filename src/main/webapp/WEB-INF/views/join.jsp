<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="pageBox">
    <div class="cardBox smallCard">
        <h2>회원가입</h2>

        <form action="${pageContext.request.contextPath}/member.join" method="post" onsubmit="return joinCheck(this);">
            <div class="formLine">
                <label>아이디</label>
                <input name="m_id" autocomplete="off" maxlength="20" placeholder="4~20자 영문/숫자/_">
            </div>

            <div class="formLine">
                <label>비밀번호</label>
                <input name="m_pw" type="password" maxlength="20" placeholder="4~20자">
            </div>

            <div class="formLine">
                <label>이름</label>
                <input name="m_name" autocomplete="off" maxlength="20">
            </div>

            <div class="formLine">
                <label>이메일</label>
                <input name="m_email" autocomplete="off" maxlength="100" placeholder="you@example.com">
            </div>

            <button class="mainBtn">가입하기</button>
        </form>
    </div>
</div>
