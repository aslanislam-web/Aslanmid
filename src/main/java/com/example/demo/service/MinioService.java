package com.example.demo.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public String uploadTxtFile(MultipartFile file) {
        try {
            ensureBucketExists();

            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("text/plain")) {
                throw new IllegalArgumentException("Файл должен быть типа text/plain (TXT)");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".txt")) {
                throw new IllegalArgumentException("Файл должен иметь расширение .txt");
            }

            String fileName = generateFileName(originalFilename, "txt");

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(contentType)
                                .build()
                );
            }

            log.info("TXT файл успешно загружен: {}", fileName);
            return fileName;

        } catch (Exception e) {
            log.error("Ошибка при загрузке TXT файла", e);
            throw new RuntimeException("Не удалось загрузить TXT файл: " + e.getMessage(), e);
        }
    }

    public String uploadPngFile(MultipartFile file) {
        try {
            ensureBucketExists();

            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("image/png")) {
                throw new IllegalArgumentException("Файл должен быть типа image/png (PNG)");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".png")) {
                throw new IllegalArgumentException("Файл должен иметь расширение .png");
            }

            String fileName = generateFileName(originalFilename, "png");

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(contentType)
                                .build()
                );
            }

            log.info("PNG файл успешно загружен: {}", fileName);
            return fileName;

        } catch (Exception e) {
            log.error("Ошибка при загрузке PNG файла", e);
            throw new RuntimeException("Не удалось загрузить PNG файл: " + e.getMessage(), e);
        }
    }

    public String uploadJsonFile(MultipartFile file) {
        try {
            ensureBucketExists();

            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/json")) {
                throw new IllegalArgumentException("Файл должен быть типа application/json (JSON)");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".json")) {
                throw new IllegalArgumentException("Файл должен иметь расширение .json");
            }

            String fileName = generateFileName(originalFilename, "json");

            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName)
                                .object(fileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(contentType)
                                .build()
                );
            }

            log.info("JSON файл успешно загружен: {}", fileName);
            return fileName;

        } catch (Exception e) {
            log.error("Ошибка при загрузке JSON файла", e);
            throw new RuntimeException("Не удалось загрузить JSON файл: " + e.getMessage(), e);
        }
    }

    private void ensureBucketExists() {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!exists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                log.info("Бакет создан: {}", bucketName);
            }
        } catch (Exception e) {
            log.error("Ошибка при проверке/создании бакета", e);
            throw new RuntimeException("Не удалось создать бакет: " + e.getMessage(), e);
        }
    }

    private String generateFileName(String originalFilename, String folder) {
        String uuid = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return folder + "/" + uuid + extension;
    }
}

