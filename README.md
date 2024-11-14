---

# Product Catalog Service

The **Product Catalog Service** is a Spring Boot microservice that manages product listings, categorization, and
searching. It uses MySQL for data persistence.

## Features

- **Browsing**: View products by categories.
- **Product Details**: Retrieve detailed product information, including images, descriptions, and specifications.
- **Search**: Perform full-text search on product listings, with support for typo correction and suggestions.

## Technologies Used

- **Java 11**
- **Spring Boot**
- **Spring Data JPA** (for MySQL)
- **Elasticsearch** (for enhanced search capabilities)

## Getting Started

### Prerequisites

- **Java 11**
- **MySQL** (for storing product and category data)
- **Elasticsearch** (for full-text search)

### Project Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/product-catalog-service.git
   cd product-catalog-service
   ```

2. **Configure the MySQL Database**
    - Create a new database in MySQL:
      ```sql
      CREATE DATABASE product_catalog;
      ```
    - Update the MySQL configurations in `src/main/resources/application.properties`:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/product_catalog
      spring.datasource.username=root
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      ```

3. **Run Elasticsearch**
    - Start Elasticsearch on `localhost:9200`. Adjust configurations in `application.properties` if using a different
      setup.

4. **Build and Run the Application**
    - Use Maven to build and start the Spring Boot application:
      ```bash
      mvn spring-boot:run
      ```

5. **Access Swagger API Documentation**
    - After the application starts, access Swagger UI for API testing and documentation at:  
      [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## API Endpoints

### 1. Browse Products by Category

- **Endpoint**: `GET /products/category/{categoryId}`
- **Description**: Retrieves a list of products within a specified category.
- **Response**: JSON array of products in the specified category.

### 2. Get Product Details

- **Endpoint**: `GET /products/{productId}`
- **Description**: Fetches detailed information about a specific product.
- **Response**: JSON object with product details, including specifications and description.

### 3. Search Products

- **Endpoint**: `GET /products/search?query={keyword}`
- **Description**: Searches for products that match the provided keyword.
- **Response**: JSON array of products matching the search criteria.

## Elasticsearch Configuration

- Ensure Elasticsearch is running locally or update the host settings in `application.properties`:
  ```properties
  spring.elasticsearch.rest.uris=http://localhost:9200
  ```

## Folder Structure

```
product-catalog-service/
├── src/
│   ├── main/
│   │   ├── java/com/example/productcatalog/
│   │   │   ├── ProductCatalogApplication.java      # Main Application File
│   │   │   ├── controller/                         # REST Controllers
│   │   │   ├── model/                              # Entity Models
│   │   │   ├── repository/                         # JPA Repositories
│   │   │   ├── service/                            # Service Layer
│   ├── resources/
│   │   ├── application.properties                  # Configuration File
└── README.md
```

## Additional Notes

- **Swagger**: Documentation is accessible at `/swagger-ui.html`.
- **Caching**: For production, consider adding a caching layer (e.g., Redis) to reduce the load on the MySQL database
  for frequently accessed products.
- **Error Handling**: Implement custom error handling for better API response management.

---

