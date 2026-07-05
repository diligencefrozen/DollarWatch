<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="pageBox">
    <div class="cardBox smallCard">
        <h2>로그인</h2>

        <form action="${pageContext.request.contextPath}/member.login" method="post" onsubmit="return loginCheck(this);">
            <div class="formLine">
                <label>아이디</label>
                <input name="m_id" autocomplete="off" maxlength="20">
            </div>

            <div class="formLine">
                <label>비밀번호</label>
                <input name="m_pw" type="password" maxlength="20">
            </div>

            <button class="mainBtn">로그인</button>
        </form>

        <p class="guideText">데모 계정: demo / 1234</p>
    </div>
</div>
