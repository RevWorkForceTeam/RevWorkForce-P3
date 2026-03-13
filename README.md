 RevWorkforce - Enterprise Microservices Platform (P3)
A cloud-native, scalable Human Resource Management (HRM) system built with Spring Cloud and Angular, designed to streamline corporate workflows through a distributed architecture.

📋 Table of Contents

Overview

System Architecture

Technology Stack

Key Features

Infrastructure Setup

CI/CD & DevOps

Running the Application

Default Credentials
🌟 Overview
RevWorkforce (P3) is the microservices evolution of our HRM platform. It decomposes the monolithic system into 9 specialized services, ensuring high availability, independent scalability, and robust fault tolerance.

🏗️ Architecture
The platform follows a Distributed Microservices Pattern:

┌─────────────────────────────────────────────────────────┐
│                    Angular 18 Frontend                  │
└───────────────┬─────────────────────────────────────────┘
                │
┌───────────────▼───────────────┐      ┌────────────────────────┐
│      API Gateway (8080)       │◄─────┤   Eureka Server (8761) │
│   (Auth, Routing, Security)   │      │  (Service Discovery)   │
└───────────────┬───────────────┘      └────────────────────────┘
                │
                ├───────► [User Service] (8081)
                ├───────► [Leave Service] (8082)
                ├───────► [Performance Service] (8083)
                ├───────► [Employee Management] (8084)
                ├───────► [Notification Service] (8085)
                └───────► [Reporting Service] (8086)
🛠️ Technology Stack
Backend (Microservices)
Framework: Spring Boot 3.2.2 / Spring Cloud 2023
Infrastructure: Netflix Eureka (Discovery), Spring Cloud Gateway, Config Server
Communication: OpenFeign (Inter-service), REST (Client-to-Service)
Security: JWT (JSON Web Tokens) with cross-service validation
Database: MySQL (Distributed instances)
Code Quality: SonarQube & JaCoCo

Frontend
Framework: Angular 18.2
UI Framework: Vanilla CSS & Bootstrap 5.3
Build Tool: Angular CLI (Optimized for Docker builds)
DevOps & Infrastructure
Containerization: Docker & Docker Compose
CI/CD: Jenkins (Pipeline-as-Code)
Analysis: SonarQube Community Edition

✨ Features
Admin Features
Centralized Management: Full control over employee lifecycle and org structure.
Leave Quotas: Custom leave type definitions (Sick, Annual, Casual).
System monitoring: View the health status of all 9 microservices.
Manager Features
Team Hierarchy: Manage direct reports and approve workforce requests.
Goal Orchestration: Set and track KPIs for the entire team.
Employee Features
Self-Service: Apply for leaves, track balances, and update profiles.
Reporting: View personal performance charts and reporting history.

🚀 Infrastructure Setup
1. Prerequisites
JDK 17+
Docker Desktop (8GB RAM recommended)
Node.js 18+
2. Properties Repository
This application pulls its properties from a centralized Git repo via the Config Server.

3. Docker Deployment
The entire ecosystem is orchestrated via Docker Compose:

bash
# Start all 10 services + MySQL + Jenkins
docker-compose -f devops/docker/docker-compose.yml up -d
🔄 CI/CD & DevOps
The project includes a robust Jenkinsfile with the following pipeline stages:

Checkout: Pulls latest code from GitHub.
Build Backend: Parallel Maven builds for all services.
Build Frontend: Production build with NODE_OPTIONS=--max-old-space-size=4096.
Sonar Scan: Deep security analysis for JS, TS, and Java files.
Docker Push: Tags and pushes images to Docker Hub under thulasikumarp.
🔑 Default Credentials
Admin Account
Email: admin@gmail.com
Password: admin123
Infrastructure
Jenkins: http://localhost:8088 (User: thulasikumarp)
SonarQube: http://localhost:9000 (User: admin)
Eureka Dashboard: http://localhost:8761

📁 Project Structure
text
RevWorkForce-P3/
├── infrastructure/         # Gateway, Eureka, Config Server
├── services/               # User, Leave, Performance, etc.
├── frontend/               # Angular UI
├── devops/                 # Dockerfiles, Jenkinsfile, Compose
└── init-db.sql             # Database startup script

🧼 Security Features
Stateless Auth: Every request is validated at the Gateway and propagated via headers.
Resource Protection: Only Admins can reach management endpoints via Gateway rules.
Vault Integration: Secrets are handled via environment variables in Docker.



## 🛠️ Local Development Setup

### 1. Clone the Repository
```bash
git clone https://github.com/RevWorkForceTeam/RevWorkForce-P3.git
cd RevWorkForce-P3
```

### 2. Configuration & Properties
The centralized properties are managed by the Config Server. You can find the property templates in the team repository:
- **Source Repository:** `https://github.com/RevWorkForceTeam/RevWorkForce-Config.git`
- Ensure your `mysql` and `jwt` secrets are updated in the `application.yml` files within the config repo.

### 3. Running with Docker Compose (Recommended)
This is the fastest way to start the entire ecosystem.

```bash
# Build and Start all services
docker-compose -f devops/docker/docker-compose.yml up -d --build
```

---

## 🔍 Code Quality (SonarQube)

We use SonarQube for static analysis and security scanning.

1.  **Start SonarQube:**
    ```bash
    docker run -d --name sonarqube -p 9000:9000 sonarqube:community
    ```
2.  **Run Scan (Backend):**
    ```bash
    mvn clean verify sonar:sonar -Dsonar.projectKey=revworkforce-backend -Dsonar.host.url=http://localhost:9000
    ```
3.  **Run Scan (Frontend):**
    ```bash
    cd frontend/revworkforce-ui
    npx sonar-scanner -Dsonar.projectKey=revworkforce-frontend -Dsonar.host.url=http://localhost:9000
    ```

---

## 🚀 CI/CD Pipeline (Jenkins)

The project includes a fully robust `Jenkinsfile` for automated deployments.

### Setup Jenkins
1.  **Start Jenkins:**
    ```bash
    docker-compose -f devops/docker/docker-compose.yml up -d jenkins
    ```
2.  **Access:** [http://localhost:8088](http://localhost:8088)
3.  **Setup Credentials:** Add `docker-hub-credentials` to Jenkins for image pushing.

### Pipeline Stages
- **Checkout:** Pulls latest code from GitHub.
- **Build Backend:** Compiles Java services using Maven.
- **Build Frontend:** Compiles Angular app (Optimized for 4GB RAM).
- **Sonar Analysis:** Runs security scans for both Frontend and Backend.
- **Docker Build:** Creates production images for 10 services.
- **Push to Hub:** Pushes images to `thulasikumarp/revworkforce-*`.

---

## 🐳 Docker Hub Repositories
Images are pushed to Docker Hub under the user: **`thulasikumarp`**.
- `docker pull thulasikumarp/revworkforce-ui:latest`
- `docker pull thulasikumarp/revworkforce-user-service:latest`
- *(And 8 more services...)*

---

## 🩹 Troubleshooting

- **Memory Issues (`Killed` error):** Ensure Docker Desktop has at least **8GB RAM** and **64GB Disk Space** allocated.
- **Port Conflicts:** Ensure ports 8080, 4200, 8888, and 9000 are not used by other apps.
- **Docker Socket Permissions:** If Jenkins can't build images, run:
  `docker exec -u root jenkins chmod 666 /var/run/docker.sock`

---

## 🤝 Contribution
For team policies and property update requests, please refer to the [Team Organization Page](https://github.com/RevWorkForceTeam).
