# MinIO File Upload Service

Сервис для загрузки файлов в MinIO (S3-совместимое хранилище).

## Возможности

- ✅ Загрузка TXT файлов
- ✅ Загрузка PNG файлов
- ✅ Загрузка JSON файлов

## Технологии

- Spring Boot 4.0.0
- MinIO Java SDK 8.5.7
- Lombok
- Docker & Docker Compose

## Запуск проекта

### 1. Запуск MinIO через Docker Compose

```bash
docker-compose up -d
```

MinIO будет доступен по адресам:
- API: http://localhost:9000
- Web Console: http://localhost:9080
- Логин: `admin`
- Пароль: `admin123`

### 2. Запуск Spring Boot приложения

```bash
./gradlew bootRun
```

Или на Windows:
```bash
gradlew.bat bootRun
```

## API Эндпоинты

### 1. Загрузка TXT файла

**Endpoint:** `POST /api/files/upload/txt`

**Требования:**
- Content-Type: `text/plain`
- Расширение: `.txt`
- Максимальный размер: 10 MB

**Пример (curl):**
```bash
curl -X POST http://localhost:8080/api/files/upload/txt \
  -F "file=@example.txt"
```

**Успешный ответ:**
```json
{
  "success": true,
  "message": "TXT файл успешно загружен",
  "fileName": "txt/uuid.txt",
  "originalFileName": "example.txt",
  "fileSize": 1024,
  "contentType": "text/plain"
}
```

---

### 2. Загрузка PNG файла

**Endpoint:** `POST /api/files/upload/png`

**Требования:**
- Content-Type: `image/png`
- Расширение: `.png`
- Максимальный размер: 10 MB

**Пример (curl):**
```bash
curl -X POST http://localhost:8080/api/files/upload/png \
  -F "file=@image.png"
```

**Успешный ответ:**
```json
{
  "success": true,
  "message": "PNG файл успешно загружен",
  "fileName": "png/uuid.png",
  "originalFileName": "image.png",
  "fileSize": 51200,
  "contentType": "image/png"
}
```

---

### 3. Загрузка JSON файла

**Endpoint:** `POST /api/files/upload/json`

**Требования:**
- Content-Type: `application/json`
- Расширение: `.json`
- Максимальный размер: 10 MB

**Пример (curl):**
```bash
curl -X POST http://localhost:8080/api/files/upload/json \
  -F "file=@data.json"
```

**Успешный ответ:**
```json
{
  "success": true,
  "message": "JSON файл успешно загружен",
  "fileName": "json/uuid.json",
  "originalFileName": "data.json",
  "fileSize": 2048,
  "contentType": "application/json"
}
```

---

## Тестирование с Postman

1. Создайте POST запрос на нужный endpoint
2. Во вкладке "Body" выберите "form-data"
3. Добавьте ключ `file` с типом "File"
4. Выберите файл нужного типа
5. Нажмите "Send"

## Проверка файлов в MinIO

1. Откройте MinIO Console: http://localhost:9080
2. Войдите (admin/admin123)
3. Перейдите в "Buckets" → "first-bucket"
4. Файлы организованы по папкам:
   - `txt/` - текстовые файлы
   - `png/` - изображения
   - `json/` - JSON данные

## Конфигурация

`application.properties`:

```properties
minio.endpoint=http://localhost:9000
minio.access-key=admin
minio.secret-key=admin123
minio.bucket-name=first-bucket

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

## Структура проекта

```
src/main/java/com/example/demo/
├── config/
│   └── MinioConfig.java       # Конфигурация MinIO клиента
├── controller/
│   └── FileController.java    # REST эндпоинты
├── service/
│   └── MinioService.java      # Бизнес-логика загрузки
└── MidApplication.java        # Main класс
```

## Обработка ошибок

Все эндпоинты возвращают ошибки в формате:

```json
{
  "success": false,
  "error": "Описание ошибки"
}
```

**HTTP коды:**
- `200` - Успешная загрузка
- `400` - Неверный формат файла или пустой файл
- `500` - Ошибка сервера

