package com.connectbundle.connect.controller;

import com.connectbundle.connect.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Test", description = "Shows Server Health Report")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Value("${aws.access-key}")
    private String accessKey;

    @Value("${aws.secret-access-key}")
    private String secretKey;

    @Value("${aws.region:eu-north-1}")
    private String region;

    @Value("${aws.bucket-name}")
    private String bucketName;

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

    @GetMapping("/s3-connection")
    public ResponseEntity<BaseResponse<Map<String, Object>>> testS3Connection() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            S3Client s3Client = S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create(accessKey, secretKey)))
                    .build();
            
            // Try to list buckets as a connection test
            ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
            ListBucketsResponse listBucketsResponse = s3Client.listBuckets(listBucketsRequest);
            
            result.put("status", "success");
            result.put("message", "S3 connection successful");
            result.put("region", region);
            result.put("bucket", bucketName);
            result.put("bucketCount", listBucketsResponse.buckets().size());
            result.put("buckets", listBucketsResponse.buckets().stream()
                    .map(bucket -> bucket.name())
                    .toArray());
            
            return BaseResponse.success(result, "AWS S3 connection test successful", 1);
            
        } catch (S3Exception e) {
            logger.error("S3 Exception: {}", e.getMessage(), e);
            result.put("status", "error");
            result.put("message", "S3 Error: " + e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            result.put("errorCode", e.awsErrorDetails().errorCode());
            result.put("region", region);
            
            return BaseResponse.error( "AWS S3 connection test failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            
        } catch (Exception e) {
            logger.error("General Exception: {}", e.getMessage(), e);
            result.put("status", "error");
            result.put("message", "Error: " + e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            
            return BaseResponse.error( "AWS S3 connection test failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
