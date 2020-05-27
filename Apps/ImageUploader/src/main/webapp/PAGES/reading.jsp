<jsp:useBean id="fileName" scope="request" type="java.lang.String"/>
<jsp:useBean id="fileContent" scope="request" type="java.lang.String"/>

<%--
  Created by IntelliJ IDEA.
  User: timoh
  Date: 24.02.2020
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%out.print(fileName);%></title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <style type="text/css">
        body{
            margin: 0;
            padding: 0;
        }
        main {
            margin: 0 auto;
            padding: 1% 2%;
            width: 75%;
            background: whitesmoke;
            min-height: 95vh;
        }
    </style>
</head>
<body>
<main>
    <a href="/ImageUploader">Home</a><br>
    <h2>File - "<%
        out.print(fileName);
    %>"</h2>
    <%
        out.print("<h3>Content: </h3>");
        out.print("<hr>");
        String expansion = fileName.substring(fileName.lastIndexOf("."));
        if (expansion.compareTo(".jpg") == 0 || expansion.compareTo(".png") == 0) {
            //call GetImage servlet to view image
            out.print("<p align=center><img style=\"max-width: 90%;height: auto;\" src=\"images/"
                    + fileName + "\" alt=\"" + fileName + "\" /></p>");
        }
        else {
            out.print("<p>" + fileContent + "</p>");
        }
        out.print("<hr>");
    %>
</main>
</body>
</html>
