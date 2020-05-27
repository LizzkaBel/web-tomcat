<%--
  Created by IntelliJ IDEA.
  User: timoh
  Date: 24.02.2020
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Google Sheets Changer</title>
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
        h1 {
            font-size: 18pt;
        }

        tr {
            height: 1.25em;
            background: beige;
            border: 1px solid gray;
        }
        tr:nth-child(2n) {
            background: bisque;
        }

        td {
            text-align: center;
            min-width: 3em;
            border: 1px solid gray;
        }
    </style>
</head>
<body>
<main>
    <h1>Google Sheets Changer</h1>
    <hr>

    <b>Enter two cells to view all values between them (A1 - Z999)</b>
    <!-- all forms should use only GET method -->
    <form action="SheetView" enctype="multipart/form-data" method="get">
        <p style="margin-bottom: 5px; margin-top: 0;">
            <label style="display: inline-block; margin-bottom: 5px;">
                Top left cellID
                <input type="text" value="<%
                    String cellID1 = request.getParameter("cellID1");
                    if (cellID1 != null) out.print(cellID1);
                    %>" name="cellID1" height="130"/>
            </label>
            <br>
            <label style="padding-top: 10px;">
                Down right cellID
                <input type="text" value="<%
                    String cellID2 = request.getParameter("cellID2");
                    if (cellID2 != null) out.print(cellID2);
                    %>" name="cellID2" height="130"/>
            </label>
        </p>
        <!-- the var below will be set after this servlet reply -->
        <input type="submit" value="Get Result">
        <b><%
            if (request.getAttribute("viewResult") != null) {
                String str = request.getAttribute("viewResult").toString();
                out.print(str);
            }
        %></b>
    </form>
    <hr>
    <!-- Table with requested cells if request is successful --->
    <%
        if (request.getAttribute("cellsContent") != null) {
            out.print("<b>Cell content:</b><br>");
            out.print(request.getAttribute("cellsContent").toString());
            out.print("<hr>");
        }
    %>

    <!-- set cell form returns only string with success/fail -->
    <b>Enter cellID (A1 - Z999) and new cell value</b>
    <form action="SheetSet" enctype="multipart/form-data" method="get">
        <p style="margin-bottom: 5px; margin-top: 0;">
            <label style="display: inline-block; margin-bottom: 5px;">
                CellID
                <input type="text" value="<%
                    String cellID = request.getParameter("cellID");
                    if (cellID != null) out.print(cellID);
                    %>" name="cellID" height="130"/>
            </label>
            <br>
            <label>
                New value
                <input type="text" name="cellValue" height="130"/>
            </label>
        </p>
        <!-- the var below will be set after this servlet reply -->
        <input type="submit" value="Set new Value">
        <b><%
            if (request.getAttribute("setResult") != null) {
                String str = request.getAttribute("setResult").toString();
                out.print(str);
            }
        %></b>
    </form>
    <hr>

    <b>Enter two cells to copy first to second (A1 - Z999)</b>
    <!-- all forms should use only GET method -->
    <form action="SheetCopy" enctype="multipart/form-data" method="get">
        <p style="margin-bottom: 5px; margin-top: 0;">
            <label style="display: inline-block; margin-bottom: 5px;">
                Origin cellID
                <input type="text" value="<%
                    String originCell = request.getParameter("originCell");
                    if (originCell != null) out.print(originCell);
                    %>" name="originCell" height="130"/>
            </label>
            <br>
            <label style="padding-top: 10px;">
                Destination cellID
                <input type="text" value="<%
                    String destinationCell = request.getParameter("destinationCell");
                    if (destinationCell != null) out.print(destinationCell);
                    %>" name="destinationCell" height="130"/>
            </label>
        </p>
        <!-- the var below will be set after this servlet reply -->
        <input type="submit" value="Copy Cells">
        <b><%
            if (request.getAttribute("copyResult") != null) {
                String str = request.getAttribute("copyResult").toString();
                out.print(str);
            }
        %></b>
    </form>
    <hr>

    <a target="_blank"
       href="https://docs.google.com/spreadsheets/d/1ErXoWZ92gtMC8uO04I5GsNWmwWhtK1p-ftqY5xVq4cY">
        Google Sheets Itself
    </a>

</main>
</body>
</html>