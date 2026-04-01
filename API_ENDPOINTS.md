# API Endpoints Documentation

This document provides an overview of the REST API endpoints available in the Illicit Capital Flow Detection application.

## Base URL

The base URL for all endpoints is: `https://api.example.com/v1`

## Endpoints

### 1. `GET /api/transactions`
**Description:** Retrieves a list of transactions.

**Parameters:**
- `page` (optional): Page number to retrieve.
- `limit` (optional): Number of records per page.

**Response:**
- `200 OK`: Returns an array of transaction objects.

### 2. `POST /api/transactions`
**Description:** Creates a new transaction.

**Request Body:**
- `amount`: The amount of the transaction.
- `source`: The source of the transaction.
- `destination`: The destination of the transaction.

**Response:**
- `201 Created`: Returns the created transaction object.

### 3. `GET /api/transactions/{id}`
**Description:** Retrieves details of a specific transaction by ID.

**Parameters:**
- `id`: The ID of the transaction.

**Response:**
- `200 OK`: Returns the transaction object.
- `404 Not Found`: If the transaction does not exist.

### 4. `PUT /api/transactions/{id}`
**Description:** Updates the details of a specific transaction.

**Request Body:**
- `amount`: (optional) The updated amount.
- `source`: (optional) The updated source.
- `destination`: (optional) The updated destination.

**Response:**
- `200 OK`: Returns the updated transaction object.
- `404 Not Found`: If the transaction does not exist.

### 5. `DELETE /api/transactions/{id}`
**Description:** Deletes a specific transaction by ID.

**Parameters:**
- `id`: The ID of the transaction.

**Response:**
- `204 No Content`: Indicates the transaction was successfully deleted.

## Example Usage

### 1. Retrieve Transactions
```bash
curl -X GET https://api.example.com/v1/api/transactions?page=1&limit=10
```

### 2. Create a New Transaction
```bash
curl -X POST https://api.example.com/v1/api/transactions \
-H "Content-Type: application/json" \
-d '{"amount": 1000, "source": "Bank A", "destination": "Bank B"}'
```

### 3. Update a Transaction
```bash
curl -X PUT https://api.example.com/v1/api/transactions/1 \
-H "Content-Type: application/json" \
-d '{"amount": 1500}'
```

### 4. Delete a Transaction
```bash
curl -X DELETE https://api.example.com/v1/api/transactions/1
```

## Conclusion

This document provides a comprehensive overview of the available API endpoints within the Illicit Capital Flow Detection application. For any additional questions or support, please refer to the official documentation or contact the development team.