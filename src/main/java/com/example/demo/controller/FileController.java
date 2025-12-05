package com.example.demo.controller;

import com.example.demo.service.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final MinioService minioService;

    @PostMapping("/upload/txt")
    public ResponseEntity<?> uploadTxtFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Файл не может быть пустым"));
            }

            String fileName = minioService.uploadTxtFile(file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "TXT файл успешно загружен");
            response.put("fileName", fileName);
            response.put("originalFileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
            response.put("contentType", file.getContentType());

            log.info("TXT файл загружен: {} -> {}", file.getOriginalFilename(), fileName);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Неверный тип файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));

        } catch (Exception e) {
            log.error("Ошибка при загрузке TXT файла", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Ошибка при загрузке файла: " + e.getMessage()));
        }
    }

    @PostMapping("/upload/png")
    public ResponseEntity<?> uploadPngFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Файл не может быть пустым"));
            }

            String fileName = minioService.uploadPngFile(file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "PNG файл успешно загружен");
            response.put("fileName", fileName);
            response.put("originalFileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
            response.put("contentType", file.getContentType());

            log.info("PNG файл загружен: {} -> {}", file.getOriginalFilename(), fileName);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Неверный тип файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));

        } catch (Exception e) {
            log.error("Ошибка при загрузке PNG файла", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Ошибка при загрузке файла: " + e.getMessage()));
        }
    }

    @PostMapping("/upload/json")
    public ResponseEntity<?> uploadJsonFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Файл не может быть пустым"));
            }

            String fileName = minioService.uploadJsonFile(file);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "JSON файл успешно загружен");
            response.put("fileName", fileName);
            response.put("originalFileName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());
            response.put("contentType", file.getContentType());

            log.info("JSON файл загружен: {} -> {}", file.getOriginalFilename(), fileName);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.warn("Неверный тип файла: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(createErrorResponse(e.getMessage()));

        } catch (Exception e) {
            log.error("Ошибка при загрузке JSON файла", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse("Ошибка при загрузке файла: " + e.getMessage()));
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", message);
        return response;
    }
}

