package by.bsu.study.sheetschanger.config;

import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Collections;
import java.util.List;

public final class Constants {
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final String APPLICATION_NAME = "WebSheets";
    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    public static final String SPREADSHEET_ID = "1ErXoWZ92gtMC8uO04I5GsNWmwWhtK1p-ftqY5xVq4cY";
    /**
     * Global instance of the scopes required by google quick start.
     * To modifying these scopes, delete your previously saved tokens/ folder.
     * (also saved on server bin)
     */
    public static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);


}
