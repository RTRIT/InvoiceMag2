
### Build application
`mvn clean package`

### Create docker network:
`docker network create invoice-network`

### Run MySql container in the network
` docker run --network invoice-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian`

### Build Docker image for the invoice application
`docker build -t invoice-application:0.0.1 .`

### Inspect network to get MySQL container IP
`docker network inspect invoice-network`

### Run the invoice application container (replace IP)
`docker run --network invoice-network --name invoice-application -p 8443:8443 -e SPRING_DATASOURCE_URL=jdbc:mysql://172.18.0.2:3306/InvoiceManagement invoice-application:0.0.1`


--------------------------

### Connect to mysql docker network and create database named InvoiceManagement






### Run application in invoice-network
`docker run --network invoice-network --name invoice-applicatin:0.0.1 -p 8443:8443 -e DBMS_CONNECTION=jdbc:mysql://172.18.0.2:3306/InvoiceManagement invoice-application:0.0.1`


### DOCKER GUIDELINE
# List docker container
`docker ps -a `

### list network in docker
`docker network ls`

### Start container and run that container in specified network
`docker start <name container>`
`docker network connect invoice-network mysql`
