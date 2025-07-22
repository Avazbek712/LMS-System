package uz.imv.lmssystem.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import uz.imv.lmssystem.dto.ErrorDTO;

import java.io.IOException;

@Component
public class SecurityAccessExceptionHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override

    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException{
        ErrorDTO errorDTO = new ErrorDTO(
                403,
                accessDeniedException.getMessage()
        );

        String json = mapper.writeValueAsString(errorDTO);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(json);

    }
}
