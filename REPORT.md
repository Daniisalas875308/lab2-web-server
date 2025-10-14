# Lab 2 Web Server -- Project Report

## Description of Changes
### Task 1: Customize the Whitelabel Error Page
For this task, I modified the default whitelabel error page provided by Spring Boot. I created a custom HTML page that includes a user-friendly message and a link to the home page. 
The custom error page is styled using CSS to match the overall theme of the application.

Then, to probe de correct functioning of the custom error page, I made a integration test that checks that the application returns a custom HTML error page (instead of the default Whitelabel one) when a non-existing URL is requested. It verifies the HTTP 404 status and the presence of specific messages in the response body.

### Task 2: Add a New EndpointAdd a New Endpoint
this task, a new REST endpoint /time was implemented to return the current server time in JSON format. A TimeDTO data class was created to represent the response structure, along with a TimeProvider interface to abstract time retrieval. Its implementation, TimeService, was annotated with @Service so that Spring can manage it automatically. An extension function toDTO() was added to convert a LocalDateTime into a TimeDTO, and a TimeController class was created to expose the /time endpoint using dependency injection.

To validate the implementation, two types of tests were developed. The first one, TimeEndpointIntegrationTest, is an integration test that starts the full application and verifies that the /time endpoint returns an HTTP 200 status and a valid timestamp close to the current time. This ensures that the endpoint, service, and JSON serialization work correctly together.

The second test, TimeEndpointMockMvcTest, uses MockMvc and a mocked TimeProvider to simulate different server times. By providing controlled values, it checks that the controller correctly serializes the expected LocalDateTime values into the JSON response. This approach verifies the endpoint’s behavior in isolation, without depending on the real system clock.

### Task 3: Enable HTTP/2 and SSL Support

In this task, SSL encryption and HTTP/2 support were enabled for the Spring Boot application. A self-signed certificate and private key were generated using OpenSSL, producing the files localhost.crt and localhost.key. These were then combined into a PKCS#12 keystore (localhost.p12), which securely stores both the certificate and key. The keystore was placed inside the src/main/resources directory to be accessible at runtime.

The application’s configuration was updated in application.yml to enable HTTPS and HTTP/2 on port 8443. The file specifies the keystore location, type, and password, allowing Spring Boot to use the certificate for encrypted communication. With this setup, the server now supports secure connections via HTTPS and improved performance using HTTP/2.

To verify the configuration, the application was started with ./gradlew bootRun, and requests were tested using curl with SSL validation disabled (-k) to ensure that the application correctly responded over HTTPS. The expected results included HTTP/2 in the response headers, confirming that both SSL and HTTP/2 were properly configured and working as intended.

## Technical Decisions
The custom error page was implemented using a dedicated HTML template to replace the default Whitelabel page, I put it the page in de Templates file, following the Spring Boot best practices. 

The /time endpoint was designed with a service interface and a DTO to decouple the controller from the time source, making it easier to test and extend. 

SSL and HTTP/2 were enabled using a self-signed PKCS#12 keystore, following Spring Boot best practices for secure and performant communication. These choices prioritize maintainability, testability, and adherence to framework conventions.
## Learning Outcomes
- Configured Spring Boot for HTTPS using a self-signed certificate and a PKCS#12 keystore.

- Enabled HTTP/2 and learned how it affects client-server communication.

- Implemented a custom error page, reinforcing the separation of presentation and business logic.

- Created a service interface, DTO, and dependency injection, making it testable and maintainable.

- Wrote integration and unit tests to validate REST endpoints and application behavior.

- Gained practical understanding of Spring Boot configuration, and testing best practices.

## AI Disclosure
### AI Tools Used
- ChatGPT
- GitHub Copilot

### AI-Assisted Work
- Generated the HTML code
- Generated the tests structure and some test cases
- Resolved some issues during the implementation

### Original Work
- The understanding of the structure of a Spring Boot application.
- The implementation of the tests.
- The structure of the time module.
- The task 3 completely.
- The manual verification of the tasks.