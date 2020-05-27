package by.bsu.study.imageuploader.imaging.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.bsu.study.imageuploader.config.constants.Paths.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet(urlPatterns = {"/uploadImage"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024
        , maxFileSize = 1024 * 1024 * 5
        , maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadImage extends HttpServlet {
    static public Logger log = LogManager.getLogger(UploadImage.class.getSimpleName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File fileSaveDir = new File(UPLOAD_DIR); //creation of directory
        log.debug(fileSaveDir.getAbsolutePath());
        if(!fileSaveDir.exists()) { //check if upload directory exist
            fileSaveDir.mkdirs();
        }

        AtomicReference<String> randFileName = new AtomicReference<>();
        AtomicBoolean haveTroubles = new AtomicBoolean(false)
            , nameTroubles = new AtomicBoolean(false);
        request.getParts().forEach(part -> {
            try {
                String name = part.getSubmittedFileName();
                if (!name.endsWith(".jpg") && !name.endsWith(".png")) {
                    throw new IOException("filename");
                }
                //generating new file name
                randFileName.set(name.substring(0, name.lastIndexOf("."))
                        + "-" + UUID.randomUUID() + name.substring(name.lastIndexOf(".")));
                part.write(fileSaveDir + File.separator + randFileName); //writing file on disk
            } catch (IOException e) {
                if (e.getMessage().equals("filename")) {
                    nameTroubles.set(true);
                }
                haveTroubles.set(true);
            }
        });

        if (haveTroubles.get()) {
            String error = "Upload Failed." + ((nameTroubles.get())? " Incorrect file." : "");
            request.setAttribute("upload_result", error);
            log.error(error);
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        log.info("Upload success");
        response.sendRedirect("viewImage?fileName=" + randFileName);
    }
}

