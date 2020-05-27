<%@ page import="java.io.File" %>
<%@ page import="by.bsu.study.imageuploader.config.constants.Paths" %>
<%--
  Created by IntelliJ IDEA.
  User: timoh
  Date: 24.02.2020
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Read file</title>
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
    <b>Upload image:</b>
    <form action="uploadImage" enctype="multipart/form-data" method="post">
        <p style="margin-bottom: -5px; margin-top: 0;">Upload file to the list below:</p><br>
        <p style="margin-bottom: 5px; margin-top: 0;">
            <input type="file" accept="image/jpeg,image/png" value="Choose File" name="getFile" height="130"/><br>
        </p>
        <input type="submit" value="Upload Image">
    </form>
    <!-- var below will be set after request -->
    <b>
        <%
            Object uploadResult = request.getAttribute("upload_result");
            if (uploadResult != null) {
                out.print(uploadResult.toString());
            }
        %>
    </b>
    <hr>

    <b>Available files:</b><br>
        <%
            File file = new File(Paths.UPLOAD_DIR); //open directory
            String[] availableFiles = file.list();
            if (availableFiles == null) {
                availableFiles = new String[0];
            }

            for (int i = 0; i < availableFiles.length; i++) {
                String fileItem = "<form action=\"viewImage\" method=\"get\" accept-charset=\"UTF-8\">" +
                        (i + 1) + ". " +
                        availableFiles[i] + " " +
                        "<input name=\"fileName\" type=\"text\" value=\"" +
                        availableFiles[i] +
                        "\"style=\"display: none;\">" +
                        "<input type=\"submit\" value=\"View\"/>" +
                        "</form>";
                out.print(fileItem);
            }
            if (availableFiles.length == 0) {
                out.print("There are no available files.<br>");
            }
        %>
    <hr>

</main>
</body>
</html>
