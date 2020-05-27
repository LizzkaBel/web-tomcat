package by.bsu.study.imageuploader.imaging.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.bsu.study.imageuploader.config.constants.Paths.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//this servlet will send images
@WebServlet("/images/*")
public class GetImage extends HttpServlet {
    static public Logger log = LogManager.getLogger(GetImage.class.getSimpleName());

    /*This servlet used when some <img> have href like "images/someimage.jpg"
     *In this case this servlet look for this file in upload directory
     *and upload it to response.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = request.getPathInfo().substring(1);
        File file = new File(UPLOAD_DIR, filename);

        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
}