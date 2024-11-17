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
   git clone https://github.com/udaysisodiya16/product-catalog-service.git
   cd product-catalog-service
   ```

2. **Configure the MySQL Database**
    - Create a new database in MySQL:
      ```sql
      CREATE DATABASE product_catalog_service;
      ```
    - Update the MySQL configurations in `src/main/resources/application.properties`:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/product_catalog_service
      spring.datasource.username=root
      spring.datasource.password=your_password
      spring.jpa.hibernate.ddl-auto=update
      ```

3. **Elasticsearch Configuration**
    - Start Elasticsearch on `localhost:9200` using `.\bin\elasticsearch.bat` command . Adjust configurations in
      `application.properties` if using a different
      setup.
    - Update the Elasticsearch configurations in `src/main/resources/application.properties`:
   ```properties
   spring.elasticsearch.uris=http://localhost:9200
   spring.elasticsearch.username=elastic
   spring.elasticsearch.password=your_password
   ```

4. **Build and Run the Application**
    - Use Maven to build and start the Spring Boot application:
      ```bash
      mvn spring-boot:run
      ```

5. **Access Swagger API Documentation**
    - After the application starts, access Swagger UI for API testing and documentation at:  
      [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

## Folder Structure

```
productcatalogservice/
├── src/
│   ├── main/
│   │   ├── java/capstone/productcatalogservice/
│   │   │   ├── ProductCatalogServiceApplication.java      # Main Application File
│   │   │   ├── configurations/                            # configurations beans
│   │   │   ├── controllers/                         # REST Controllers
│   │   │   ├── dtos/                                # Api requst and response dtos
│   │   │   ├── mappers/                             # used to convert data between different object
│   │   │   ├── models/                              # Entity Models
│   │   │   ├── repos/                               # JPA Repositories
│   │   │   ├── services/                            # Service Layer
│   ├── resources/
│   │   ├── application.properties                  # Configuration File
└── README.md

```
