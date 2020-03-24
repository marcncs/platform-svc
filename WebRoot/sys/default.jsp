<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ include file="../common/tag.jsp"%>
<%@ page import="com.winsafe.drp.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Main</title>
</head>
<body>
<h3>Logged in Account:</h3>
<br>
<table border="1" class="table">
    <thead>
    <tr>
        <th>Username</th>
        <th>Environment</th>
    </tr>
    </thead>
    <tbody>
        <td>${account.username()}</td>
        <td>${account.environment()}</td>
    </tbody>
</table>

</body>
</html>