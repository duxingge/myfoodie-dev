package com.example.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:file-upload-dev.properties")
@ConfigurationProperties(prefix = "file")
@Data
public class FileUpload {
    private String imageUserFaceLocation;
    private String imageServerUrl;
}
