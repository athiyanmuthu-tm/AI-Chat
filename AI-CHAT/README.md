# AI Chat - Demo

This is a minimal AI-powered chat application with a Java Spring Boot backend and a simple frontend (HTML/CSS/JS).

## Project structure

- `backend/` - Spring Boot project (Java)
  - `src/main/java/com/example/aichat/` - Java sources
  - `pom.xml` - Maven configuration
- `frontend/` - Static frontend
  - `index.html`, `styles.css`, `app.js`

## How it works

1. The user types a message in the frontend and clicks "Send".
2. The frontend sends a POST request to `http://localhost:8080/api/ask` with JSON: `{ "message": "..." }`.
3. The backend handles the request (using `ChatController`) and calls `ChatService.generateReply` for a response.
4. The backend returns a JSON response: `{ "reply": "..." }` and the frontend displays it.

## Running the backend (Java Spring Boot)

1. Install Java 17 and Maven.
2. Open a terminal in `backend`:

```powershell
cd backend
mvn spring-boot:run
```

or to build and run the JAR:

```powershell
mvn package
java -jar target/aichat-0.0.1-SNAPSHOT.jar
```

The backend starts on port 8080.
 
### Example cURL request:

```powershell
curl -X POST http://localhost:8080/api/ask -H "Content-Type: application/json" -d '{"message":"Tell me a joke"}'
```

Sample response:

```json
{ "reply": "Why don't programmers like nature? It has too many bugs!" }
```

### Optional: Serve the frontend from backend
Copy the contents of the `frontend` directory into `backend/src/main/resources/static/` (create it if it doesn't exist) and then the frontend will be served from http://localhost:8080/

## Running the frontend (a few options)

- Option A (local file): Open `frontend/index.html` in a browser (some browsers restrict fetch when using `file://` protocol due to CORS — it's recommended to use a static dev server).
- Option B (simple server with Python):

```powershell
cd frontend
python -m http.server 5500
# Open http://localhost:5500 in your browser
```

- Option C (copy to backend static folder and open http://localhost:8080)

## Example API request & response

Request (JSON):

```json
POST /api/ask
Content-Type: application/json

{ "message": "Hello AI" }
```

Response (JSON):

```json
{ "reply": "Hello! How can I help you today?" }
```

## Extending to real AI

The `ChatService` currently uses a simple algorithm to respond. To connect to an AI provider like OpenAI, you can add a method that calls OpenAI's API using `HttpClient` or `OkHttp`, or a Java SDK if you prefer. Add the secret API key as an environment variable and keep it out of source control.

## Notes

- The controller has an `@CrossOrigin(origins = "*")` annotation for simplicity. In production limit origins to your actual frontend origin.
- Use of real AI APIs requires secure storage of keys and usage limits — the current implementation is a proof of concept.
