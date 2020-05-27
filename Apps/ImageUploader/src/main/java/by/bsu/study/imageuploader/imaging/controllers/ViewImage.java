package by.bsu.study.imageuploader.imaging.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.bsu.study.imageuploader.config.constants.Paths.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(urlPatterns = {"/viewImage", "/viewImage.do"})
public class ViewImage extends HttpServlet {
    static public Logger log = LogManager.getLogger(ViewImage.class.getSimpleName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        if (fileName == null){
            request.setAttribute("fileContent", "Empty request!");
            log.error("Empty request");
            request.getRequestDispatcher("PAGES/reading.jsp").forward(request, response);
            return;
        }
        request.setAttribute("fileName", fileName);

        File file = new File(UPLOAD_DIR, fileName);
        if(!file.exists()){
            String warn = "File \"" + fileName + "\" doesn't exist!";
            request.setAttribute("fileContent", warn);
            log.warn(warn);
            request.getRequestDispatcher("PAGES/reading.jsp").forward(request, response);
            return;
        }

        /*if text file uploaded in directory manually by admin*/
        String content = "";
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
            content = readFile(file); //file reading
        }
        request.setAttribute("fileContent", content);
        request.getRequestDispatcher("PAGES/reading.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = "Call for empty doPost method.";
        log.error(error);
        request.setAttribute("upload_result", error);
        request.getRequestDispatcher("PAGES/index.jsp").forward(request, response);
    }

    public static String readFile(File file) {
        //file reading via buffered reader
        StringBuilder contentBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        }
        catch (IOException ex) {
           log.info("File reading exception. " + ex.getMessage());
        }
        return contentBuilder.toString();
    }
}
