<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="pageBox">
    <div class="cardBox">
        <h2>관심목록</h2>

        <c:choose>
            <c:when test="${sessionScope.loginMember == null}">
                <p class="emptyText">로그인 후 관심목록을 사용할 수 있습니다.</p>
            </c:when>

            <c:otherwise>
                <form action="${pageContext.request.contextPath}/favorite.add" method="post" class="favoriteQuickForm" onsubmit="return favoriteCheck(this);">
                    <div class="formGrid threeCol">
                        <div class="formLine">
                            <label>기준 통화</label>
                            <select name="f_base">
                                <option value="USD">USD</option>
                                <option value="KRW">KRW</option>
                                <option value="JPY">JPY</option>
                                <option value="EUR">EUR</option>
                                <option value="GBP">GBP</option>
                                <option value="CAD">CAD</option>
                                <option value="AUD">AUD</option>
                            </select>
                        </div>
                        <div class="formLine">
                            <label>대상 통화</label>
                            <select name="f_target">
                                <option value="KRW">KRW</option>
                                <option value="USD">USD</option>
                                <option value="JPY">JPY</option>
                                <option value="EUR">EUR</option>
                                <option value="GBP">GBP</option>
                                <option value="CAD">CAD</option>
                                <option value="AUD">AUD</option>
                            </select>
                        </div>
                        <div class="formLine">
                            <label>메모</label>
                            <input name="f_memo" maxlength="100" placeholder="예: 매일 확인">
                        </div>
                    </div>
                    <button class="mainBtn">관심 통화쌍 추가</button>
                </form>

                <c:choose>
                    <c:when test="${empty favorites}">
                        <p class="emptyText">아직 저장된 관심 환율이 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <table class="dataTable">
                            <tr>
                                <th>번호</th>
                                <th>기준 통화</th>
                                <th>대상 통화</th>
                                <th>현재 환율</th>
                                <th>메모</th>
                                <th>삭제</th>
                            </tr>
                            <c:forEach var="f" items="${favorites}">
                                <tr>
                                    <td><c:out value="${f.f_no}" /></td>
                                    <td><c:out value="${f.f_base}" /></td>
                                    <td><c:out value="${f.f_target}" /></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty f.currentRate}">
                                                1 <c:out value="${f.f_base}" /> = <c:out value="${f.currentRate}" /> <c:out value="${f.f_target}" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${f.rateResult}" />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><c:out value="${f.f_memo}" /></td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/favorite.delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                            <input type="hidden" name="f_no" value="${f.f_no}">
                                            <button type="submit" class="deleteBtn">삭제</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</div>
