<%@ page import="diary.bean.DiaryBeans" %>
<%@ page import="diary.commmon.ErrorCheck" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    DiaryBeans diary_beans = (DiaryBeans) session.getAttribute("diary_beans");
    ErrorCheck error_check = new ErrorCheck();
    String good_point      = error_check.inputEscape(diary_beans.getGood_point());
    String bad_point       = error_check.inputEscape(diary_beans.getBad_point());
    String student_comment = error_check.inputEscape(diary_beans.getStudent_comment());
    String teacher_comment = error_check.inputEscape(diary_beans.getTeacher_comment());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>コメント更新確認画面</title>
    <%--MDB--%>
    <%@include file="/WEB-INF/jsp/bootstrap.jsp" %>
    <%--Original--%>
    <link rel="stylesheet" href="css/insert.css">
</head>

<body class="p-0">
<%@include file="/WEB-INF/jsp/teacherTop.jsp" %>

<div class="container-fluid vh-100 animated bounceInUp faster">
    <div class="col-12 col-sm-10 col-md-8 col-lg-6 m-auto p-5 bg-white z-depth-1">
        <h1 class="text-center border-bottom border-dark">コメント更新確認</h1>

        <p class="text-center">以下の情報で更新しますか？</p>

        <table class="table table-striped table-borderless mr-auto ml-auto mb-0 col-12 mt-5">
            <tr class="row animated bounceInLeft faster">
                <th class="col-4">日付</th>
                <td class="col-8"><%=diary_beans.getInsert_date()%></td>
            </tr>
            <tr class="row animated bounceInLeft fast">
                <th class="col-4">良い点</th>
                <td class="col-8"><%=good_point%></td>
            </tr>
            <tr class="row animated bounceInLeft">
                <th class="col-4">悪い点</th>
                <td class="col-8"><%=bad_point%></td>
            </tr>
            <tr class="row animated bounceInLeft slow">
                <th class="col-4">学生コメント</th>
                <td class="col-8"><%=student_comment%></td>
            </tr>
            <tr class="row animated bounceInLeft slower">
                <th class="col-4">教員コメント</th>
                <td class="col-8"><%=teacher_comment%></td>
            </tr>
        </table>

        <%--コメント登録完了画面へ--%>
        <form action="commentupdate" method="get">
            <div class="text-center mt-5">
                <button type="submit" class="btn btn-primary btn-lg">更新する</button>
            </div>
        </form>

        <%--コメント操作選択画面へ--%>
        <form action="commentupdateinput" method="post" class="text-right back">
            <button type="submit" class="btn btn-outline-dark">戻る</button>
        </form>
    </div>
</div>

<%@include file="/WEB-INF/jsp/script.jsp" %>

</body>
</html>
