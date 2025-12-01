# AI Chat - Backend (Spring Boot)

## Prerequisites
- Java 17 (or the configured Java version)
- Maven

## Running the backend

```powershell
cd backend
mvn spring-boot:run
```

or build and run the jar:

```powershell
cd backend
mvn package
java -jar target/aichat-0.0.1-SNAPSHOT.jar
```

### Using an OpenAI API Key (optional)
If you'd like the backend to call OpenAI instead of the simple dummy logic, set an environment variable `OPENAI_API_KEY` before starting the app:

```powershell
$env:OPENAI_API_KEY = "sk-..."
mvn spring-boot:run
```

### API Endpoint
- POST `/api/ask` - accepts JSON `{"message":"..."}` and returns JSON `{"reply":"..."}`.

Example curl:

```powershell
curl -X POST http://localhost:8080/api/ask -H "Content-Type: application/json" -d '{"message":"Hi there!"}'
```

Expected response (example):

```json
{ "reply": "Hello! How can I help you today?" }
```

## Notes
- CORS is enabled for all origins on the controller via `@CrossOrigin(origin = "*")` for development convenience.
- To serve the frontend from the backend, copy `frontend/*` to `src/main/resources/static/` and then open `http://localhost:8080/` to access the frontend.
