<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DollarWatch</title>
<link rel="stylesheet" href="${ctx}/resources/css/dollarwatch.css">
<script type="text/javascript">
    var ctx = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/resources/js/jQuery.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/dollarwatch.js"></script>
</head>
<body>
    <div id="topMenu">
        <div id="logoArea">
            <a href="${ctx}/home.go" id="logo">DollarWatch</a>
            <span id="subTitle">Exchange-rate tracking and favorites</span>
        </div>

        <div id="mainNav">
            <a href="${ctx}/home.go">홈</a>

            <c:choose>
                <c:when test="${sessionScope.loginMember == null}">
                    <a href="${ctx}/member.login.go">로그인</a>
                    <a href="${ctx}/member.join.go">회원가입</a>
                </c:when>

                <c:otherwise>
                    <span class="loginInfo"><c:out value="${sessionScope.loginMember.m_name}" />님</span>
                    <a href="${ctx}/member.logout">로그아웃</a>
                </c:otherwise>
            </c:choose>

            <a href="${ctx}/board.go">커뮤니티</a>
            <a href="${ctx}/favorite.go">관심목록</a>
        </div>
    </div>

    <c:if test="${empty cp}">
        <c:set var="cp" value="home.jsp" />
    </c:if>

    <c:if test="${not empty result}">
        <div id="resultMessage">
            <c:out value="${result}" />
        </div>
    </c:if>

    <div id="contentArea">
        <jsp:include page="${cp}" />
    </div>

    <div id="footerArea">
        <div class="footerInner">
            <p class="footerLogo">DollarWatch</p>
            <p class="footerText">Built with Spring MVC, MyBatis, Oracle, JSP, jQuery, and external exchange-rate APIs.</p>
            <p class="footerCopy"><a href="https://www.exchangerate-api.com" target="_blank" rel="noopener noreferrer">Rates By Exchange Rate API</a> · Frankfurter v2</p>
        </div>
    </div>
</body>
</html>
