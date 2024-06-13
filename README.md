## Running the Accounts API Project

This project is a Maven-based Spring Boot application that provides an API for managing accounts.

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- **Java Development Kit (JDK)** - Ensure JDK 17 or higher is installed.
- **Maven** - Make sure Maven is installed. You can download it from [Maven's official website](https://maven.apache.org/download.cgi) and follow the [installation instructions](https://maven.apache.org/install.html).

### Clone the Repository

Clone this repository to your local machine using Git:

```bash
git clone <repository-url>
cd Accounts Api  # Navigate to the project directory
```

### Build the Application

To build the project, execute the following Maven command from the project's root directory (where the pom.xml file is located):

```bash
./mvnw  clean package
```

### Run the Application

To build the project, execute the following Maven command from the project's root directory (where the pom.xml file is located):

```bash
./mvnw  spring-boot:run
```

This command compiles the code, resolves dependencies, and starts the Spring Boot application.

### Accessing the API

Once the application has started, you can access the API endpoints using a web browser or tools like curl or Postman:

Base URL: http://localhost:8080/api
    
### API Endpoints

Here are some example endpoints provided by the Accounts API:
### Get Beneficiary by ID

- **Endpoint:** `/beneficiaries/{beneficiaryId}`
- **Description:** Retrieves details of a beneficiary based on the provided `beneficiaryId`.

### Get Beneficiary Accounts

- **Endpoint:** `/beneficiaries/{beneficiaryId}/accounts`
- **Description:** Retrieves accounts associated with the beneficiary identified by `beneficiaryId`.

### Get Beneficiary Transactions

- **Endpoint:** `/beneficiaries/{beneficiaryId}/transactions`
- **Description:** Retrieves transaction history related to the beneficiary identified by `beneficiaryId`.

### Get Beneficiary Balance

- **Endpoint:** `/beneficiaries/{beneficiaryId}/balance`
- **Description:** Retrieves the current balance for the beneficiary identified by `beneficiaryId`.

### Get Largest Withdrawal Last Month

- **Endpoint:** `/beneficiaries/{beneficiaryId}/largest-withdrawal-last-month`
- **Description:** Retrieves information about the largest withdrawal made by the beneficiary identified by `beneficiaryId` in the last month.
    
