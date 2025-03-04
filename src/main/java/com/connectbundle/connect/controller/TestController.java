package com.connectbundle.connect.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectbundle.connect.dto.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Shows Server Health Report")
public class TestController {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public TestController(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    @Operation(summary = "Server Health Report", description = "Check if the server is running and system health")
    public ResponseEntity<BaseResponse<Map<String, Object>>> testServer() {
        Map<String, Object> response = new HashMap<>();

        // Get system-related metrics
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        response.put("uptime", formatUptime(ManagementFactory.getRuntimeMXBean().getUptime()));
        response.put("freeMemoryMB", runtime.freeMemory() / (1024 * 1024));
        response.put("totalMemoryMB", runtime.totalMemory() / (1024 * 1024));
        response.put("maxMemoryMB", runtime.maxMemory() / (1024 * 1024));
        response.put("cpuLoad", formatCpuLoad(osBean.getSystemLoadAverage()));
        response.put("availableProcessors", osBean.getAvailableProcessors());

        // Improved DB Health Check
        response.put("database", checkDatabaseHealth());

        return BaseResponse.success(response, "Server health report",null);
    }

    private String checkDatabaseHealth() {
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            jdbcTemplate.queryForObject("SELECT COUNT(*) FROM connect.users", Integer.class); // Executes a simple query
            return "Connected";
        } catch (Exception e) {
            return "Not Connected";
        }
    }

    private String formatUptime(long millis) {
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private String formatCpuLoad(double load) {
        return (load < 0) ? "Unavailable" : String.format("%.2f%%", load * 100);
    }
}
