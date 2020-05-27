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
    <title>Mail App</title>
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

        #shadowing {
            background: rgba(102, 102, 102, 0.5);
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
            display: none;
        }
        #shadowing:target {display: block;}
        .close {
            display: inline-block;
            border: 1px solid black;
            color: gray;
            padding: 0 3px;
            margin: 3px;
            text-decoration: none;
            background: #f2f2f2;
            font-size: 12pt;
            cursor:pointer;
        }
        .close:hover {background: #e6e6ff;}
        #resultWindow {
            width: max-content;
            max-height: max-content;
            max-width: 25%;
            height: auto;
            text-align: center;
            padding: 5px;
            border: 1px solid black;
            border-radius: 5px;
            color: black;
            position: absolute;
            top: 25%;
            right: 0;
            bottom: 0;
            left: 0;
            margin: auto;
            background: #fff;
        }
    </style>
</head>
<body>
<div id="shadowing">
    <div id="resultWindow">
        <span style="color: gray">Response:</span><br>
        <!-- the var below will be set after SendMail servlet reply -->
        <%
            if (request.getAttribute("getResult") != null) {
                String sendResult = request.getAttribute("getResult").toString();
                out.print(sendResult);
            }
            else {
                out.print("Send GET request to get response");
            }
        %>
        <br>
        <a href="#" class="close">OK</a>
    </div>
</div>
<main>
    <h1>Mail Sending Application</h1>
    <hr>
    <b>Message sending form</b>
    <hr>
    <!-- SendMail should receive only GET method -->
    <form action="SendMail" enctype="multipart/form-data" method="get">
        <p style="margin-bottom: 5px; margin-top: 0;">
            Sender:
            <label>
                <input type="email" value="" name="sender" height="130" width="20"/>
            </label>
            Receiver:
            <label>
                <input type="email" value="" name="receiver" height="130" width="20"/>
            </label>
            <br>
        </p>
        <p>
            Theme
            <label>
                <input type="text" value="" name="theme" height="130" width="20"/>
            </label>
        </p>
        Message
        <p style="margin-bottom: 5px; margin-top: 0;">
            <label>
                <textarea name="message" style="resize: none; width: 50%; height: 200px;"></textarea>
            </label>
            <br>
        </p>
        <input type="submit" value="Send Mail">
        <%
            if ((request.getAttribute("getResult") != null)) {
                out.print("<a href=\"#shadowing\">Read Result</a>");
            }
        %>
    </form>
    <hr>
    <!--<a href="#shadowing">Вызвать всплывающее окно</a>-->
</main>
</body>
</html>