package ru.kuksov.compapi.config.logging;

import java.io.IOException;

import com.github.loki4j.slf4j.marker.LabelMarker;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@WebFilter("/*")
@Slf4j
public class StatsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // empty
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        try {
            chain.doFilter(req, resp);
        } finally {
            time = System.currentTimeMillis() - time;
            LabelMarker markerTime = LabelMarker.of("ComputersDB", () -> "time");
            log.info(markerTime, "Время выполнения запроса по адресу {}: {} ms",
                    ((HttpServletRequest) req).getRequestURI(),  time);
        }
    }

    @Override
    public void destroy() {
        // empty
    }
}