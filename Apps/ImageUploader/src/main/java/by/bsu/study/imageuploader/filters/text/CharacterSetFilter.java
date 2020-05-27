package by.bsu.study.imageuploader.filters.text;

import javax.servlet.*;
import java.io.IOException;

// this servlet applies to all text parts of request and set their encode to UTF-8
public class CharacterSetFilter implements Filter {
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }
}
