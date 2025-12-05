# MinIO File Upload Service

Сервис для загрузки файлов в MinIO (S3-совместимое хранилище).

## Возможности

- ✅ Загрузка TXT файлов
- ⏳ Загрузка PNG файлов (в разработке)
- ⏳ Загрузка JSON файлов (в разработке)

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

### Загрузка TXT файла

**Endpoint:** `POST /api/files/upload/txt`

**Описание:** Загружает TXT файл в MinIO бакет `first-bucket`

**Параметры:**
- `file` (multipart/form-data) - TXT файл для загрузки

**Требования:**
- Тип файла: `text/plain`
- Расширение: `.txt`
- Максимальный размер: 10 MB

**Пример запроса (curl):**

```bash
curl -X POST http://localhost:8080/api/files/upload/txt \
  -F "file=@example.txt"
```

**Пример успешного ответа:**

```json
{
  "success": true,
  "message": "TXT файл успешно загружен",
  "fileName": "txt/a1b2c3d4-e5f6-7890-abcd-ef1234567890.txt",
  "originalFileName": "example.txt",
  "fileSize": 1024,
  "contentType": "text/plain"
}
```

**Пример ответа с ошибкой:**

```json
{
  "success": false,
  "error": "Файл должен быть типа text/plain (TXT)"
}
```

## Тестирование с Postman

1. Создайте новый POST запрос на `http://localhost:8080/api/files/upload/txt`
2. Во вкладке "Body" выберите "form-data"
3. Добавьте ключ `file` с типом "File"
4. Выберите TXT файл для загрузки
5. Нажмите "Send"

## Структура проекта

```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── config/
│   │   │   └── MinioConfig.java           # Конфигурация MinIO клиента
│   │   ├── controller/
│   │   │   └── FileController.java        # REST контроллер для загрузки файлов
│   │   ├── service/
│   │   │   └── MinioService.java          # Сервис для работы с MinIO
│   │   └── MidApplication.java            # Главный класс приложения
│   └── resources/
│       └── application.properties         # Конфигурация приложения
└── docker-compose.yml                      # Docker Compose для MinIO
```

## Конфигурация

Настройки MinIO находятся в `application.properties`:

```properties
minio.endpoint=http://localhost:9000
minio.access-key=admin
minio.secret-key=admin123
minio.bucket-name=first-bucket
```

## Логика работы TXT эндпоинта

1. **Валидация файла:**
   - Проверка, что файл не пустой
   - Проверка Content-Type: `text/plain`
   - Проверка расширения: `.txt`

2. **Проверка бакета:**
   - Если бакет не существует, он создается автоматически

3. **Загрузка файла:**
   - Генерируется уникальное имя файла (UUID)
   - Файл сохраняется в папке `txt/` внутри бакета
   - Возвращается информация о загруженном файле

4. **Обработка ошибок:**
   - HTTP 400: неверный тип файла
   - HTTP 500: ошибка сервера при загрузке

## Проверка файлов в MinIO

1. Откройте MinIO Console: http://localhost:9080
2. Войдите с учетными данными (admin/admin123)
3. Перейдите в раздел "Buckets" → "first-bucket"
4. Проверьте загруженные файлы в папке `txt/`

## Следующие шаги

- [ ] Реализовать эндпоинт для PNG файлов
- [ ] Реализовать эндпоинт для JSON файлов
- [ ] Добавить эндпоинт для получения файлов
- [ ] Добавить эндпоинт для удаления файлов

