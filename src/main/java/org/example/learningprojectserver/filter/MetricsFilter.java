package org.example.learningprojectserver.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static org.example.learningprojectserver.constants.SecurityConstants.MetricsFilterConstants.PRINT_FORMAT;


public class MetricsFilter extends OncePerRequestFilter {

    private final ConcurrentHashMap<String, AtomicLong> requestCountMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, AtomicLong> totalResponseTimeMap = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            requestCountMap.computeIfAbsent(path, k -> new AtomicLong()).incrementAndGet();
            totalResponseTimeMap.computeIfAbsent(path, k -> new AtomicLong()).addAndGet(duration);

            long total = totalResponseTimeMap.get(path).get();
            long count = requestCountMap.get(path).get();
            long avg = total / count;

//            System.out.printf("Path: %s | Time: %dms | Total Requests: %d | Avg Response Time: %dms%n",
//                    path, duration, count, avg);
            System.out.printf(PRINT_FORMAT,
                    path, duration, count, avg);
        }
    }
}
