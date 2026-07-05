/** DollarWatch frontend helpers */

$(function() {
    $("#rateSearchBtn").click(function() {
        var base = $("#base").val();
        var target = $("#target").val();

        $("#rateResult").html("<p class='emptyText'>환율을 조회하는 중입니다...</p>");

        $.ajax({
            url: ctx + "/exchange.rate.getJSON",
            type: "GET",
            data: {
                base: base,
                target: target
            },
            success: function(rate) {
                if (rate.result == "success") {
                    var html = "";
                    html += "<div class='rateCard'>";
                    html += "<h3>" + dwEscape(rate.base) + " / " + dwEscape(rate.target) + "</h3>";
                    html += "<p class='rateText'>1 " + dwEscape(rate.base) + " = " + dwEscape(rate.rate) + " " + dwEscape(rate.target) + "</p>";
                    html += "<p class='guideText'>" + dwEscape(rate.message) + "</p>";
                    html += "<form action='" + ctx + "/favorite.add' method='post' class='inlineFavoriteForm' onsubmit='return favoriteCheck(this);'>";
                    html += "<input type='hidden' name='f_base' value='" + dwEscapeAttr(rate.base) + "'>";
                    html += "<input type='hidden' name='f_target' value='" + dwEscapeAttr(rate.target) + "'>";
                    html += "<input name='f_memo' maxlength='100' placeholder='메모: 예) 매일 확인'>";
                    html += "<button type='submit' class='mainBtn'>관심목록 추가</button>";
                    html += "</form>";
                    html += "</div>";
                    $("#rateResult").html(html);
                } else {
                    $("#rateResult").html("<div class='failBox'>" + dwEscape(rate.message) + "</div>");
                }
            },
            error: function() {
                $("#rateResult").html("<div class='failBox'>서버 통신에 실패했습니다.</div>");
            }
        });
    });

    $("#trendSearchBtn").click(function() {
        var base = $("#base").val();
        var target = $("#target").val();

        $("#trendResult").html("<p class='emptyText'>최근 7일 추이를 조회하는 중입니다...</p>");

        $.ajax({
            url: ctx + "/exchange.trend.getJSON",
            type: "GET",
            data: {
                base: base,
                target: target
            },
            success: function(data) {
                if (data.result == "success") {
                    var html = "";
                    html += "<h3>" + dwEscape(data.base) + " → " + dwEscape(data.target) + " 최근 7일 추이</h3>";
                    html += "<table class='dataTable'>";
                    html += "<tr>";
                    html += "<th>날짜</th>";
                    html += "<th>환율</th>";
                    html += "</tr>";

                    for (var i = 0; i < data.trends.length; i++) {
                        html += "<tr>";
                        html += "<td>" + dwEscape(data.trends[i].date) + "</td>";
                        html += "<td>" + dwEscape(data.trends[i].rate) + "</td>";
                        html += "</tr>";
                    }

                    html += "</table>";
                    $("#trendResult").html(html);
                } else {
                    $("#trendResult").html("<p class='emptyText'>" + dwEscape(data.message) + "</p>");
                }
            },
            error: function() {
                $("#trendResult").html("<p class='emptyText'>환율 추이 조회 중 오류가 발생했습니다.</p>");
            }
        });
    });
});

function dwEscape(v) {
    if (v == null) {
        return "";
    }

    return (v + "")
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}

function dwEscapeAttr(v) {
    return dwEscape(v);
}

function dwTrim(v) {
    if (v == null) {
        return "";
    }
    return (v + "").replace(/^\s+|\s+$/g, "");
}

function dwInput(form, name) {
    return $(form).find("[name='" + name + "']");
}

function dwValue(form, name) {
    return dwTrim(dwInput(form, name).val());
}

function dwFail(input, msg) {
    alert(msg);
    input.addClass("inputError");
    input.focus();
    return false;
}

function dwClear(form) {
    $(form).find(".inputError").removeClass("inputError");
}

function joinCheck(form) {
    dwClear(form);

    var idInput = dwInput(form, "m_id");
    var pwInput = dwInput(form, "m_pw");
    var nameInput = dwInput(form, "m_name");
    var emailInput = dwInput(form, "m_email");

    var id = dwValue(form, "m_id");
    var pw = dwValue(form, "m_pw");
    var name = dwValue(form, "m_name");
    var email = dwValue(form, "m_email");

    var idReg = /^[A-Za-z0-9_]{4,20}$/;
    var emailReg = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    if (id.length == 0) {
        return dwFail(idInput, "아이디를 입력해주세요.");
    }

    if (!idReg.test(id)) {
        return dwFail(idInput, "아이디는 영문, 숫자, 언더바(_)만 사용해서 4~20자로 입력해주세요.");
    }

    if (pw.length < 4 || pw.length > 20) {
        return dwFail(pwInput, "비밀번호는 4~20자로 입력해주세요.");
    }

    if (name.length < 2 || name.length > 20) {
        return dwFail(nameInput, "이름은 2~20자로 입력해주세요.");
    }

    if (!emailReg.test(email)) {
        return dwFail(emailInput, "올바른 이메일 형식으로 입력해주세요.");
    }

    idInput.val(id);
    pwInput.val(pw);
    nameInput.val(name);
    emailInput.val(email);
    return true;
}

function loginCheck(form) {
    dwClear(form);

    var idInput = dwInput(form, "m_id");
    var pwInput = dwInput(form, "m_pw");
    var id = dwValue(form, "m_id");
    var pw = dwValue(form, "m_pw");

    if (id.length == 0) {
        return dwFail(idInput, "아이디를 입력해주세요.");
    }

    if (pw.length == 0) {
        return dwFail(pwInput, "비밀번호를 입력해주세요.");
    }

    idInput.val(id);
    pwInput.val(pw);
    return true;
}

function favoriteCheck(form) {
    dwClear(form);

    var base = dwValue(form, "f_base").toUpperCase();
    var target = dwValue(form, "f_target").toUpperCase();
    var memoInput = dwInput(form, "f_memo");
    var memo = dwValue(form, "f_memo");

    if (base.length == 0 || target.length == 0) {
        alert("통화쌍을 선택해주세요.");
        return false;
    }

    if (base == target) {
        alert("서로 다른 통화쌍만 저장할 수 있습니다.");
        return false;
    }

    if (memo.length > 100) {
        return dwFail(memoInput, "메모는 100자 이하로 입력해주세요.");
    }

    dwInput(form, "f_base").val(base);
    dwInput(form, "f_target").val(target);
    memoInput.val(memo);
    return true;
}

function postCheck(form) {
    dwClear(form);

    var titleInput = dwInput(form, "b_title");
    var contentInput = dwInput(form, "b_content");
    var title = dwValue(form, "b_title");
    var content = dwValue(form, "b_content");
    var fileInput = $(form).find("[name='b_file']")[0];

    if (fileInput && fileInput.files && fileInput.files.length > 0) {
        var file = fileInput.files[0];
        var fileName = file.name.toLowerCase();

        if (file.size > 2 * 1024 * 1024) {
            alert("이미지는 최대 2MB까지 첨부할 수 있습니다.");
            fileInput.focus();
            return false;
        }

        if (
            !fileName.endsWith(".jpg") &&
            !fileName.endsWith(".jpeg") &&
            !fileName.endsWith(".png") &&
            !fileName.endsWith(".gif") &&
            !fileName.endsWith(".webp")
        ) {
            alert("이미지 파일만 첨부할 수 있습니다.");
            fileInput.focus();
            return false;
        }
    }

    if (title.length < 2 || title.length > 80) {
        return dwFail(titleInput, "제목은 2~80자로 입력해주세요.");
    }

    if (content.length < 5 || content.length > 1000) {
        return dwFail(contentInput, "내용은 5~1000자로 입력해주세요.");
    }

    titleInput.val(title);
    contentInput.val(content);
    return true;
}

function commentCheck(form) {
    var contentInput = $(form).find("[name='c_content']");
    var content = $.trim(contentInput.val());

    if (content.length == 0) {
        alert("댓글을 입력해주세요.");
        contentInput.focus();
        return false;
    }

    if (content.length > 500) {
        alert("댓글은 500자 이하로 입력해주세요.");
        contentInput.focus();
        return false;
    }

    contentInput.val(content);
    return true;
}
