<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageBox">
    <div class="cardBox">
        <h2>게시글 본문</h2>

        <c:choose>
            <c:when test="${requestScope.detailBoard == null}">
                <p class="emptyText">게시글을 찾을 수 없습니다.</p>
            </c:when>
            <c:otherwise>
                <div class="detailBox">
                    <h3><c:out value="${requestScope.detailBoard.b_title}" /></h3>
                    <p class="writerText">
                        작성자: <c:out value="${requestScope.detailBoard.b_writer}" /> ·
                        <fmt:formatDate value="${requestScope.detailBoard.b_reg_date}" pattern="yyyy-MM-dd HH:mm" />
                    </p>
                    <hr>
                    <p class="contentText"><c:out value="${requestScope.detailBoard.b_content}" /></p>

                    <c:if test="${not empty detailBoard.b_img}">
                        <div class="boardImageBox">
                            <img src="${pageContext.request.contextPath}/resources/upload/board/${detailBoard.b_img}" alt="게시글 이미지">
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.loginMember != null && sessionScope.loginMember.m_id eq detailBoard.b_writer}">
                        <form action="${pageContext.request.contextPath}/board.delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                            <input type="hidden" name="b_no" value="${detailBoard.b_no}">
                            <button type="submit" class="deleteBtn">게시글 삭제</button>
                        </form>
                    </c:if>
                </div>

                <div class="commentBox">
                    <h3>댓글</h3>

                    <c:choose>
                        <c:when test="${sessionScope.loginMember != null}">
                            <form action="${pageContext.request.contextPath}/comment.write" method="post" class="commentForm" onsubmit="return commentCheck(this);">
                                <input type="hidden" name="c_b_no" value="${detailBoard.b_no}">
                                <textarea name="c_content" maxlength="500" placeholder="댓글을 입력하세요."></textarea>
                                <button class="mainBtn">댓글 작성</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <p class="emptyText">로그인 후 댓글을 작성할 수 있습니다.</p>
                        </c:otherwise>
                    </c:choose>

                    <div class="commentList">
                        <c:forEach var="c" items="${comments}">
                            <div class="commentItem">
                                <div class="commentMeta">
                                    <strong><c:out value="${c.c_writer}" /></strong>
                                    <span><fmt:formatDate value="${c.c_date}" pattern="yyyy-MM-dd HH:mm" /></span>
                                </div>
                                <p class="commentContent"><c:out value="${c.c_content}" /></p>

                                <c:if test="${sessionScope.loginMember != null && sessionScope.loginMember.m_id eq c.c_writer}">
                                    <form action="${pageContext.request.contextPath}/comment.delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                        <input type="hidden" name="c_no" value="${c.c_no}">
                                        <input type="hidden" name="c_b_no" value="${detailBoard.b_no}">
                                        <button type="submit" class="deleteBtn smallDeleteBtn">삭제</button>
                                    </form>
                                </c:if>
                            </div>
                        </c:forEach>

                        <c:if test="${empty comments}">
                            <p class="emptyText">아직 댓글이 없습니다.</p>
                        </c:if>
                    </div>
                </div>

                <a class="mainBtn linkBtn" href="${pageContext.request.contextPath}/board.go">목록으로</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
