# Food Delivery App Backend

A Spring Boot backend application for a food delivery system with MySQL database integration.

## Features

- User authentication and authorization with JWT
- Food item management
- Shopping cart functionality
- Order management
- File upload for food images
- RESTful API endpoints
- MySQL database integration

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Security
- Spring Data JPA
- MySQL 8.0
- JWT for authentication
- Maven for dependency management

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

## Setup Instructions

### 1. Database Setup

Create a MySQL database:

```sql
CREATE DATABASE food_delivery_db;
```

### 2. Configuration

Update the database configuration in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:4000`

## API Endpoints

### Authentication
- `POST /api/login` - User login
- `POST /api/register` - User registration

### Food Items
- `GET /api/food/list` - Get all food items
- `GET /api/food/category/{category}` - Get foods by category
- `GET /api/food/categories` - Get all categories
- `GET /api/food/search?name={name}` - Search foods by name
- `GET /api/food/{id}` - Get food by ID

### Cart
- `POST /api/cart/get` - Get user's cart (requires token)
- `POST /api/cart/add` - Add item to cart (requires token)
- `POST /api/cart/remove` - Remove item from cart (requires token)

### Orders
- `POST /api/order/place` - Place an order (requires token)
- `GET /api/order/my-orders` - Get user's orders (requires token)
- `GET /api/order/{orderId}` - Get order by ID (requires token)
- `GET /api/order/admin/all` - Get all orders (admin, requires token)
- `PUT /api/order/admin/{orderId}/status` - Update order status (admin, requires token)

### File Upload
- `POST /api/upload/image` - Upload food images

## Database Schema

### Users Table
- id (Primary Key)
- first_name
- last_name
- email (Unique)
- password (Encrypted)
- phone_number
- created_at
- updated_at

### Foods Table
- id (Primary Key)
- name
- description
- price
- image_url
- category
- is_available
- created_at
- updated_at

### Orders Table
- id (Primary Key)
- user_id (Foreign Key)
- total_amount
- delivery_fee
- status
- payment_status
- session_url
- address (Embedded)
- created_at
- updated_at

### Order Items Table
- id (Primary Key)
- order_id (Foreign Key)
- food_id (Foreign Key)
- quantity
- unit_price
- total_price

### Cart Items Table
- id (Primary Key)
- user_id (Foreign Key)
- food_id (Foreign Key)
- quantity

## Security

- JWT-based authentication
- Password encryption using BCrypt
- CORS enabled for frontend integration
- Role-based access control (planned for future)

## File Upload

Images are stored in the `uploads/` directory and served via `/images/{filename}` endpoint.

## Development

### Adding New Features

1. Create entity classes in `com.fooddelivery.entity`
2. Create repository interfaces in `com.fooddelivery.repository`
3. Create service classes in `com.fooddelivery.service`
4. Create controller classes in `com.fooddelivery.controller`
5. Add DTOs in `com.fooddelivery.dto` if needed

### Testing

Run tests with:
```bash
mvn test
```

## Deployment

1. Build the JAR file:
```bash
mvn clean package
```

2. Run the JAR file:
```bash
java -jar target/food-delivery-backend-0.0.1-SNAPSHOT.jar
```

## Environment Variables

Set these environment variables for production:
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `JWT_EXPIRATION`








