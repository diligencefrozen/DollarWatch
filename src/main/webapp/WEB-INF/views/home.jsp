<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageBox">
    <div class="heroBox">
        <p class="eyebrow">Spring MVC Portfolio Project</p>
        <h1>DollarWatch</h1>
        <p>실시간 환율을 조회하고, 자주 보는 통화쌍을 저장하고, 환율 흐름을 커뮤니티에서 기록하는 웹앱입니다.</p>
    </div>

    <div class="cardBox">
        <h2>실시간 환율 조회</h2>

        <div class="formGrid twoCol">
            <div class="formLine">
                <label>기준 통화</label>
                <select id="base">
                    <option value="USD">USD - 미국 달러</option>
                    <option value="KRW">KRW - 대한민국 원</option>
                    <option value="JPY">JPY - 일본 엔</option>
                    <option value="EUR">EUR - 유로</option>
                    <option value="GBP">GBP - 영국 파운드</option>
                    <option value="CAD">CAD - 캐나다 달러</option>
                    <option value="AUD">AUD - 호주 달러</option>
                </select>
            </div>

            <div class="formLine">
                <label>대상 통화</label>
                <select id="target">
                    <option value="KRW">KRW - 대한민국 원</option>
                    <option value="USD">USD - 미국 달러</option>
                    <option value="JPY">JPY - 일본 엔</option>
                    <option value="EUR">EUR - 유로</option>
                    <option value="GBP">GBP - 영국 파운드</option>
                    <option value="CAD">CAD - 캐나다 달러</option>
                    <option value="AUD">AUD - 호주 달러</option>
                </select>
            </div>
        </div>

        <div class="buttonRow">
            <button id="rateSearchBtn" class="mainBtn">환율 조회</button>
            <button id="trendSearchBtn" class="subBtn">최근 7일 추이 보기</button>
        </div>

        <div id="rateResult" class="rateResultBox">
            <p class="emptyText">조회할 통화를 선택한 후 환율 조회 버튼을 눌러주세요.</p>
        </div>

        <div id="trendResult" class="rateResultBox">
            <p class="emptyText">최근 환율 흐름을 확인할 수 있습니다.</p>
        </div>
    </div>

    <div class="cardBox">
        <h2>최근 조회 기록</h2>
        <c:choose>
            <c:when test="${empty recentSearchLogs}">
                <p class="emptyText">아직 조회 기록이 없습니다. 환율을 조회하면 여기에 최근 기록이 저장됩니다.</p>
            </c:when>
            <c:otherwise>
                <table class="dataTable">
                    <tr>
                        <th>통화쌍</th>
                        <th>환율</th>
                        <th>조회일</th>
                    </tr>
                    <c:forEach var="log" items="${recentSearchLogs}">
                        <tr>
                            <td><c:out value="${log.base}" /> / <c:out value="${log.target}" /></td>
                            <td>1 <c:out value="${log.base}" /> = <c:out value="${log.rate}" /> <c:out value="${log.target}" /></td>
                            <td><fmt:formatDate value="${log.searchedAt}" pattern="yyyy-MM-dd HH:mm" /></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="cardBox">
        <h2>프로젝트 설명</h2>
        <ul class="infoList">
            <li>현재 환율은 ExchangeRate-API의 open access endpoint에서 가져옵니다.</li>
            <li>최근 7일 추이는 Frankfurter v2 time-series endpoint를 사용합니다.</li>
            <li>회원, 관심목록, 조회 기록, 게시글, 댓글은 Oracle DB에 저장합니다.</li>
            <li>Controller, DAO/Service, MyBatis Mapper, JSP View로 레이어를 분리했습니다.</li>
        </ul>
    </div>
</div>
