package by.bsu.study.sheetschanger.sheets.handlers;

import by.bsu.study.sheetschanger.google.connection.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.bsu.study.sheetschanger.config.Constants.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@WebServlet("/SheetCopy")
public class SheetCopy extends HttpServlet {
    public static Logger log = LogManager.getLogger(SheetCopy.class.getSimpleName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String originCell = request.getParameter("originCell");
        String destinationCell = request.getParameter("destinationCell");
        //if servlet doesn't receive necessary params
        if(originCell == null || destinationCell == null) {
            log.error("GET without parameters!");
            request.setAttribute("copyResult","Request Fail");
            request.getRequestDispatcher("index.jsp").forward(request, response); //back to index with fail
            return;
        }

        try {
            UpdateValuesResponse copyResult = copyCellToAnother(originCell, destinationCell);

            log.info("Updated cells: " + copyResult.getUpdatedCells().toString());
            log.info("Request success.");
            request.setAttribute("copyResult","Request Success");
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        } catch (GeneralSecurityException e) {
            String error = "Request fail. " + e.getMessage();
            log.error(error);
            request.setAttribute("copyResult", error);
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        } catch (IOException e) {
            String error = "Request fail. Wrong cell ID. Try again.";
            log.error(error + e.getMessage());
            request.setAttribute("copyResult", error);
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.error("Servlet get POST request that is empty.");
        request.setAttribute("copyResult","Request Fail");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public static UpdateValuesResponse copyCellToAnother(String originCell, String destinationCell)
            throws IOException, GeneralSecurityException {
        ValueRange originCellRawValue = SheetView.getRawCellsData(originCell, originCell);

        String originCellValue = "";
        if (originCellRawValue.getValues() != null && originCellRawValue.getValues().size() != 0) {
            originCellValue = originCellRawValue.getValues().get(0).get(0).toString();;
        }

        return SheetSet.updateSheetsCellData(destinationCell, originCellValue); //set dest cell value
    }
}
