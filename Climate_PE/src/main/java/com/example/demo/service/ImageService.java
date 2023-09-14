package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

@Service
public class ImageService {
	@Value("${firebase.projectId}")
	private String projectId;

	@Value("${firebase.bucketName}")
	private String bucketName;

	@Value("${firebase.credentialsPath}")
	private String credentialsPath;

	public String uploadImage(MultipartFile file) {
		try {
			StorageOptions options = StorageOptions.newBuilder().setProjectId(projectId)
					.setCredentials(
							ServiceAccountCredentials.fromStream(getClass().getResourceAsStream(credentialsPath)))
					.build();
			GoogleCredentials credentials = ServiceAccountCredentials
					.fromStream(getClass().getResourceAsStream(credentialsPath));
			Storage storage = options.getService();
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			BlobId blobId = BlobId.of(bucketName, fileName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpg").build();
			storage.create(blobInfo, file.getBytes());
			return fileName;
		} catch (IOException e) {
			return "Cannot upload file";
		}
	}

	public void deleteImage(String imageName) {
		try {
			// Khởi tạo Firebase Storage
			GoogleCredentials credentials = GoogleCredentials
					.fromStream(getClass().getResourceAsStream(credentialsPath));
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

			// Thay thế "your-bucket-name" bằng tên bucket của bạn trong Firebase Storage
			Bucket bucket = storage.get(bucketName);

			// Xóa ảnh từ Firebase Storage
			bucket.get(imageName).delete();
		} catch (IOException e) {

		}
	}
}
