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

@WebServlet("/SheetSet")
public class SheetSet extends HttpServlet {
    public static Logger log = LogManager.getLogger(SheetSet.class.getSimpleName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cellID = request.getParameter("cellID");
        String cellValue = request.getParameter("cellValue");
        //if servlet doesn't receive necessary params
        if(cellID == null || cellValue == null || cellValue.isEmpty()) {
            log.error("GET without parameters!");
            request.setAttribute("setResult","Request Fail");
            request.getRequestDispatcher("index.jsp").forward(request, response); //back to main page with fail
            return;
        }

        try {
            UpdateValuesResponse setResult = updateSheetsCellData(cellID, cellValue);
            log.info("Updated cells: " + setResult.getUpdatedCells().toString());

            log.info("Request success.");
            request.setAttribute("setResult","Request Success");
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        } catch (GeneralSecurityException e) {
            String error = "Request fail. " + e.getMessage();
            log.error(error);
            request.setAttribute("setResult",error);
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        } catch (IOException e) {
            String error = "Request fail. Wrong cell ID. Try again. ";
            log.error(error + e.getMessage());
            request.setAttribute("setResult", error);
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.error("Servlet get POST request that is empty.");
        request.setAttribute("setResult","Request Fail");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public static UpdateValuesResponse updateSheetsCellData(String cellID, String value)
            throws IOException, GeneralSecurityException {
        Sheets googleSheets = SheetsServiceUtil.getSheetsService();
        List<List<Object>> values = Collections.singletonList( //single value
                Collections.singletonList(
                        value
                )
        );
        ValueRange body = new ValueRange().setValues(values);

        return googleSheets.spreadsheets()
                .values()
                .update(SPREADSHEET_ID, cellID, body)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }
}
