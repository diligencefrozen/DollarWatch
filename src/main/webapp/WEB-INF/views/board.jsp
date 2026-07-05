<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageBox">
    <div class="cardBox">
        <h2>환율 커뮤니티</h2>
        <c:choose>
            <c:when test="${sessionScope.loginMember == null}">
                <p class="emptyText">로그인 후 게시글을 작성할 수 있습니다.</p>
            </c:when>
            <c:otherwise>
                <form action="${pageContext.request.contextPath}/board.write" method="post" enctype="multipart/form-data" class="boardWriteForm" onsubmit="return postCheck(this);">
                    <div class="formLine">
                        <label>제목</label>
                        <input name="b_title" autocomplete="off" maxlength="80" placeholder="제목을 입력하세요">
                    </div>

                    <div class="formLine">
                        <label>내용</label>
                        <textarea name="b_content" maxlength="1000" placeholder="환율, 달러, 해외송금, 경제 이슈 등에 대해 자유롭게 작성하세요."></textarea>
                    </div>

                    <div class="formLine">
                        <label>이미지 첨부</label>
                        <input type="file" name="b_file" accept="image/jpeg,image/png,image/gif,image/webp">
                        <p class="guideText">jpg, jpeg, png, gif, webp / 최대 2MB</p>
                    </div>

                    <button class="mainBtn">글 작성</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="cardBox">
        <h2>게시글 목록</h2>
        <c:choose>
            <c:when test="${empty boards}">
                <p class="emptyText">아직 게시글이 없습니다.</p>
            </c:when>
            <c:otherwise>
                <table class="dataTable">
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                    <c:forEach var="b" items="${boards}">
                        <tr>
                            <td><c:out value="${b.b_no}" /></td>
                            <td>
                                <a href="${pageContext.request.contextPath}/board.detail?b_no=${b.b_no}">
                                    <c:out value="${b.b_title}" />
                                    <c:if test="${not empty b.b_img}"><span class="imageBadge">image</span></c:if>
                                </a>
                            </td>
                            <td><c:out value="${b.b_writer}" /></td>
                            <td><fmt:formatDate value="${b.b_reg_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
