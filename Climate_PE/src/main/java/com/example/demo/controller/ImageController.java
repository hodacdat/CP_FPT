package com.example.demo.controller;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageException;
import org.springframework.http.HttpHeaders;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = { "https://clinicmates.io.vn", "https://clinicmates.io.vn:8081", "https://localhost:3000" })
public class ImageController {


    @Value("${firebase.projectId}")
    private String projectId;

    @Value("${firebase.bucketName}")
    private String bucketName;

    @Value("${firebase.credentialsPath}")
    private String credentialsPath;
    
    
    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            StorageOptions options = StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(ServiceAccountCredentials.fromStream(
                            getClass().getResourceAsStream(credentialsPath))
                    )
                    .build();
            GoogleCredentials credentials = ServiceAccountCredentials.fromStream(getClass().getResourceAsStream(credentialsPath));
            Storage storage = options.getService();
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/jpeg")
                    .build();
            storage.create(blobInfo, file.getBytes());
            String accessToken = null;
            
            // Trả về phản hồi với tên tệp tin
            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {

        GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream(credentialsPath));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        
        // Lấy ảnh từ Firebase
        Blob blob = storage.get(bucketName, imageName);
        byte[] imageBytes = blob.getContent();
        
        // Trả về phản hồi chứa ảnh
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

}
