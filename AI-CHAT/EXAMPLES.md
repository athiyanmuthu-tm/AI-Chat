# Examples & Quick Start

## Example JSON Request

POST to `/api/ask` with:

```json
{
  "message": "Tell me a joke"
}
```

## Example JSON Response

```json
{
  "reply": "Why don't programmers like nature? It has too many bugs!"
}
```

## Quickstart (backend)

```powershell
cd backend
mvn spring-boot:run
```

## Quickstart (frontend)

```powershell
cd frontend
python -m http.server 5500
# open http://localhost:5500
```

## cURL Example

```powershell
curl -X POST http://localhost:8080/api/ask -H "Content-Type: application/json" -d '{"message":"Hello AI"}'
```

If the server is accessible and running, you will get a reply as JSON.