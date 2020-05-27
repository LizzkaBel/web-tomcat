package by.bsu.study.sheetschanger.sheets.handlers;

import by.bsu.study.sheetschanger.google.connection.SheetsServiceUtil;
import com.google.api.services.sheets.v4.Sheets;
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
import java.util.List;

@WebServlet("/SheetView")
public class SheetView extends HttpServlet {
    public static Logger log = LogManager.getLogger(SheetView.class.getSimpleName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cellID1 = request.getParameter("cellID1");
        String cellID2 = request.getParameter("cellID2");
        if(cellID1 == null || cellID2 == null) { //servlet doesn't receive any params
            log.error("GET without parameters!");
            request.setAttribute("viewResult","Request Fail");
            request.getRequestDispatcher("index.jsp").forward(request, response); //back to index with fail
            return;
        }

        try {
            ValueRange rawTable = getRawCellsData(cellID1, cellID2);
            request.setAttribute("cellsContent", buildTable(rawTable));

            log.info("Request success.");
            request.setAttribute("viewResult","Request Success");
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        } catch (GeneralSecurityException e) {
            String error = "Request fail. " + e.getMessage();
            log.error(error);
            request.setAttribute("viewResult", error);
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        } catch (IOException e) {
            String error = "Request fail. Wrong cell ID. Try again. ";
            log.error(error + e.getMessage());
            request.setAttribute("viewResult", error);
            request.getRequestDispatcher("index.jsp").forward(request, response); //return to the main page
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.error("Servlet get POST request that is empty.");
        request.setAttribute("viewResult","Request Fail");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public static String buildTable(ValueRange rawTable) {
        if (rawTable.getValues() == null){
            return "There are no values in this the range.";
        }
        int maxWidth = rawTable.getValues()
                .stream()
                .mapToInt(List::size)
                .max()
                .orElse(0); //getting max size of table line
        //building of resulting table
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("<table>");
        rawTable.getValues().forEach(list -> {
            tableBuilder.append("<tr>");
            list.forEach(item ->
                    tableBuilder.append("<td>")
                            .append(item.toString())
                            .append("</td>")
            );
            if (list.size() < maxWidth){ //filling blank fields in table
                tableBuilder.append("<td></td>".repeat(maxWidth - list.size()));
            }
            tableBuilder.append("</tr>");
        });
        tableBuilder.append("</table>");
        return tableBuilder.toString();
    }

    public static ValueRange getRawCellsData(String startCell, String endCell)
            throws IOException, GeneralSecurityException {
        Sheets googleSheets = SheetsServiceUtil.getSheetsService();

        return googleSheets.spreadsheets()
                .values()
                .get(SPREADSHEET_ID, startCell + ":" + endCell)
                .execute();
    }
}
