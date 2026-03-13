# 🚀 RevWorkForce-P3: Enterprise HRMS Microservices Platform

RevWorkForce is a modern, scalable Human Resource Management System built on a **Microservices Architecture**. This platform automates workforce management, leave tracking, performance appraisals, and real-time notifications.

---

## 🏛️ Project Architecture

The system is divided into **Infrastructure Services** (Core) and **Business Services** (Domain).

### Core Infrastructure
- **API Gateway (Port 8080):** Central entry point with JWT authentication and routing.
- **Config Server (Port 8888):** Centralized external configuration repository.
- **Eureka Server (Port 8761):** Service Discovery and Load Balancing.

### Business Microservices
- **User Service (8081):** Auth & Profile management.
- **Leave Service (8082):** Leave applications & Balance tracking.
- **Performance Service (8083):** Goal setting & Appraisals.
- **Employee Management (8084):** Org structure & Announcements.
- **Notification Service (8085):** Real-time system alerts.
- **Reporting Service (8086):** Analytics & Dashboards.

### Frontend
- **Angular UI (Port 4200):** Premium, responsive dashboard.

---

## 📋 Prerequisites

Ensure you have the following installed:
- **Java 17+** (JDK)
- **Node.js 18+** & **Angular CLI**
- **Docker Desktop** (with 8GB+ RAM allocated)
- **Maven** (optional, handled by Docker/Jenkins)

---

## 🛠️ Local Development Setup

### 1. Clone the Repository
```bash
git clone https://github.com/thulasikumar0423/RevWorkForce-P3.git
cd RevWorkForce-P3
```

### 2. Configuration & Properties
The centralized properties are managed by the Config Server. You can find the property templates in the team repository:
- **Source Repository:** `https://github.com/RevWorkForceTeam/RevWorkForce-P3.git`
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
