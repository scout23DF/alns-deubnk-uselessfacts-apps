# Overview

Your task is to create a full stack application (backend in Kotlin or Java, frontend in Angular or React) that fetches random facts from the Useless Facts API, caches them, and offers a private area to consult access statistics.
The service should be built with a focus on clean and maintainable architecture.
Many details in this requirements are not specified, feel free to be creative; especially on the frontend layer.
If you manage the backend in Kotlin with quarkus or ktor it would be really great.

# Requirements

## Backend:
1. Fetch Facts: Implement a service that fetches random facts from the Useless Facts API (https://uselessfacts.jsph.pl/random.json?language=en).
2. Caching: Implement in-memory caching to store the facts.
3. Analytics: Provide a private endpoint to consult access statistics for each fact (e.g., number of times accessed).
4. No Database: Use simplified in-memory storage instead of a database.

## Frontend:
1. Fetch Facts: Provide a button that will fetch a random fact, show the result of each operation.
2. View Facts: Provide a list of the cached facts and a detail view of the fact.
3. Analytics: Provide a page, use a different route.

# Backend Endpoints
1. Fetch Fact and Shorten URL:
- Endpoint: POST /facts
- Description: Fetches a random fact from the Useless Facts API, stores it, and returns it (show and store all the fields provided by the api).
 
2. Return single cached fact:
- Endpoint: GET /facts/{shortenedUrl}
- Description: Returns the cached fact and increments the access count.
 
3. Return all cached facts:
- Endpoint: GET /facts
- Description: Returns all cached facts and does not increment the access count.
 
4. Access statistics:
- Endpoint: GET /admin/statistics
- Description: Provides access statistics for all cached facts.

# Instructions
1. Project Setup:
- Use Kotlin or Java for the backend.
- Use Angular or React for the frontend.
2. Implementation
- Create a backend service to fetch random facts from the Useless Facts API and wire it to the frontend.
- Implement in-memory storage to cache facts.
- Track access statistics for each accessed fact.
- Provide a private endpoint to view access statistics.
3. Running the Application
- Provide a README.md file with instructions on how to run the application.
- Include necessary dependencies and setup instructions.
4. Submission
- Ensure your code is well-documented.
- Include unit tests to demonstrate code quality and reliability.

# Evaluation Criteria
- Adherence to clean and maintainable architecture.
- Responsiveness in the frontend layer.
- Code quality and readability.
- Completeness and correctness of the implementation.
- Documentation and ease of setup.
- Use of in-memory storage for caching and analytics.

# Additional Notes
- Ensure to handle error responses from the Useless Facts API appropriately.
- Focus on building a robust and maintainable codebase that can be easily extended and maintained.
