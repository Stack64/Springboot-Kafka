package com.example.producer.model;
//
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class PostResponse {
//	public PostResponse(int status, String message) {
//		this.status = status;
//		this.message = message;
//		ZonedDateTime nowIst=ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Kolkata"));
//		this.timestamp=nowIst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
//	}
//	private int status;
//	private String message;
//	private String timestamp;
//	public int getStatus() {
//		return status;
//	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
//	public String getMessage() {
//		return message;
//	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
//	public String getTimestamp() {
//		return timestamp;
//	}
//	public void setTimestamp(String timestamp) {
//		this.timestamp = timestamp;
//	}
//}
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;

public class PostResponse {
    private final int status;
    private final String message;
    private final String timestamp;

    // Constructor to initialize values
    public PostResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Kolkata"))
                                      .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    }

    // Getter methods (since we're not using Lombok)
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // toString() for logging/debugging
    @Override
    public String toString() {
        return "PostResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
