# 📚 LMS - Library Management System

![Test Status](https://github.com/your-username/lms/actions/workflows/test.yml/badge.svg)
![Build Status](https://github.com/your-username/lms/actions/workflows/docker-build-push.yml/badge.svg)
![Docker Pulls](https://img.shields.io/docker/pulls/your-username/lms)
![Docker Image Size](https://img.shields.io/docker/image-size/your-username/lms)

---

## 📖 Overview

The **LMS (Library Management System)** is a modern RESTful API for managing books and borrowers in a library. Its modular architecture enables scalable and efficient operations.

### Key Features:
- **Borrower and Book Registration**
- **Borrow and Return Books**
- **Comprehensive Logging with SLF4J and Logback**
- **API Documentation with Open API 3.0**
- **Containerization**: Docker support for streamlined deployments.
- **Kubernetes Integration**: Scalability with MySQL and Kubernetes.
 
---

## 🚀 Features

- **Backend** : Java 17 with Spring Boot 
- **Database**: MySQL for Dev 
- **Build Tool** : Gradle
- **Testing**: JUnit 5 and Mockito
  **Containerization**: Docker and Kubernetes
  **Documentation**: Springdoc OpenAPI 3 (Swagger)  
-   
-  

---

## 🛠️ Getting Started

### Prerequisites
- **Java 17** installed
- **Docker** installed
- **MySQL 8.0+**
- **Gradle** installed
- **Postman** or **cURL** for testing

---

### Build and Run Locally

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/lms.git
    cd lms
    ```

2. **Configure the database**:
    - Create a new MySQL database:
      ```sql
      CREATE DATABASE lms;
      ```
    - Update the `application.yml` file in `src/main/resources`:
      ```yaml
      spring:
        datasource:
          url: jdbc:mysql://localhost:3306/lms
          username: root
          password: root
      ```

3. **Build the project**:
    ```bash
    gradle clean build
    ```

4. **Run the application**:
    ```bash
    gradle bootRun --args='--spring.profiles.active=dev'
    ```

5. **Access the application**:
    - Swagger Documentation: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - Base API URL: [http://localhost:8080/api](http://localhost:8080/api)

---

## 📦 Docker Deployment

1. **Build Docker Image**:
    ```bash
    docker build -t your-username/lms .
    ```

2. **Run Docker Container**:
    ```bash
    docker run -p 8080:8080 your-username/lms
    ```

3. **Run with a specific profile**:
    ```bash
    docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod your-username/lms
    ```

---

## 🏗️ Kubernetes Deployment

1. **Prerequisites**:
    - Kubernetes cluster
    - `kubectl` installed and configured

2. **Apply Kubernetes Manifests**:
    ```bash
    kubectl apply -f K8S/lms.yaml
    kubectl apply -f K8S/log-persist-volume.yaml.yaml
    kubectl apply -f K8S/mysql-k8s.yaml 
    ```

3. **Access the Application**:
    - Use the external IP of the LoadBalancer service:
      ```bash
      kubectl get svc lms
      ```

---

## 🧪 Testing

### Run Unit Tests
Run the tests using Gradle:
```bash
gradle test
