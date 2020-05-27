package by.bsu.study.mailapp.handling.controllers;

import by.bsu.study.mailapp.handling.mailer.MailFormer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.bsu.study.mailapp.config.constants.Constants.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SendMail")
public class SendMail extends HttpServlet {
    public static Logger log = LogManager.getLogger(SendMail.class.getSimpleName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameterMap().size() != 4){ //if servlet doesn't receive correct num of params
            log.error("GET has incorrect num of parameters! Required num is 4. "
                + "Received num = " + request.getParameterMap().size());
            request.setAttribute("getResult","Request Fail. GET request had incorrect parameters.");
            request.getRequestDispatcher("index.jsp").forward(request, response); //back to index with fail
            return;
        }

        //get form fields
        String sender = request.getParameter("sender")
                , receiver = request.getParameter("receiver")
                , theme = request.getParameter("theme")
                , messageContent = request.getParameter("message");
        //if any of the form fields is empty
        if (sender.isEmpty() || receiver.isEmpty() || theme.isEmpty() || messageContent.isEmpty()) {
            request.setAttribute("getResult","Fill up all fields to send the message.");
            request.getRequestDispatcher("index.jsp").forward(request, response); //back to index with fail
            log.warn("Attempt to send message with blank fields.");
            return;
        }

        MailFormer mail = new MailFormer(USERNAME, PASSWORD);

        try {
            mail.sendMessage(sender, receiver, theme, messageContent);
        } catch (MessagingException ex) {
            String errMsg = "Message sending fail. " + ex.getMessage()
                    + "\nCause by: " + ex.getCause();
            log.error(errMsg);
            request.setAttribute("getResult", errMsg);
            request.getRequestDispatcher("index.jsp").forward(request, response); //back to index with fail
            return;
        }

        log.info("SendMail request success.");
        request.setAttribute("getResult","Request Success. Mail was successfully send.");
        request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.error("Request for empty POST method.");
        request.setAttribute("getResult","Send mail error. Incorrect POST method.");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
