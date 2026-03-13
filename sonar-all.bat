@echo off
echo ========================================
echo SonarQube Multi-Service Scanner (with Coverage)
echo ========================================

set TOKEN=squ_404cf10b2035070d22d42ef43ae825b1a7542056

echo [1/8] Analyzing API Gateway...
cd infrastructure\api-gateway
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [2/8] Analyzing User Service...
cd services\user-service
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [3/8] Analyzing Notification Service...
cd services\notification-service
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [4/8] Analyzing Employee Management Service...
cd services\employee-management-service
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [5/8] Analyzing Leave Service...
cd services\leave-service
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [6/8] Analyzing Performance Service...
cd services\performance-service
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [7/8] Analyzing Reporting Service...
cd services\reporting-service
call mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo [8/8] Analyzing Angular Frontend...
cd frontend\revworkforce-ui
echo Running Frontend Tests for coverage...
call npx ng test --no-watch --code-coverage
call npx sonar-scanner -Dsonar.host.url=http://localhost:9000 -Dsonar.token=%TOKEN%
cd ..\..

echo.
echo ========================================
echo All analyses completed successfully!
echo View results: http://localhost:9000
echo ========================================
pause
