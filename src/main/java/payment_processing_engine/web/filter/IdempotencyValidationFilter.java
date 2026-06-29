package payment_processing_engine.web.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class IdempotencyValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod()) &&
                httpRequest.getRequestURI().startsWith("/api/v1/transfers")) {

            String idempotencyKey = httpRequest.getHeader("X-Idempotency-Key");
            if (idempotencyKey == null || idempotencyKey.isBlank()) {
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required X-Idempotency-Key header.");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}