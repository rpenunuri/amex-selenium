# Selenium with Page Object Model Demo

This is a demo project that demonstrates end-to-end testing with Selenium using the Page Object Model (POM). The project is set up to run tests both locally and in a Continuous Integration (CI) environment using Docker or TestNG.

## Project Structure

The project is structured as follows:

- `tests`: This directory contains the test files.
- `pages`: This directory contains the Page Object Model files.
- `scripts`: This directory contains scripts for running the tests.

## Running Tests
Tests can be executed using docker, bash script, maven, and TestNG.

### Docker Support
This project includes a Dockerfile and a docker-compose.yml file that are used for running the tests in a Docker container. This allows you to run the tests in any environment that supports Docker, including most CI environments.
Note: tests will run in headless mode in the Docker container to speed up execution time.

To run the tests in a Docker container, use the following command:

```
# Running tests with chrome browser
docker-compose --profile chrome up
```

You can also provide specific url (this can be useful to run tests against different test environments) and browser to run tests with a command like this:

```
BASE_URL=https://www.americanexpress.com/us BROWSER=FIREFOX_REMOTE docker-compose --profile firefox up
```

### Bash script

To facilitate test execution on local environments, you can run tests using the `run-selenium-tests.sh` script in the `scripts` directory. This script accepts the following arguments:

- `-u|--url`: The base URL used for all of the test cases. If not provided, `https://www.americanexpress.com/us` is used.
- `-b|--browser`: The browser to use for the tests. If not provided, CHROME_REMOTE is used.

This script uses docker compose to run tests, passing the provided arguments as environment variables.

For example, to run all tests with the firefox browser, you would use the following command:

```
chmod +x ./scripts/run-selenium-tests.sh
./scripts/run-selenium-tests.sh -b FIREFOX_REMOTE
```

### Maven test runner
You can run your tests in your local environment using maven, here is an example:

#### Run all tests
```
# Local chrome driver will be used to run tests
mvn clean veryfy
```

## CI Support
This project can be easily integrated with popular CI environments like GitHub Actions and GitLab CI. You can reuse the existing `run-selenium-tests` script or `docker-compose` for this.

### GitHub Actions
To use this project with GitHub Actions (see: `.github/workflows/run-tests-chrome.yml`), you can create a .github/workflows/yourworkflow.yml file with the following content:

```
name: Run selenium tests

on: [push, pull_request]

jobs:
    test:
        runs-on: ubuntu-latest

        steps:
        - name: Checkout code
          uses: actions/checkout@v4
    
        - name: Run tests with chromium browser
          run: |
            chmod +x ./scripts/run-selenium-tests.sh
            ./scripts/run-selenium-tests.sh
   ```

### GitLab CI
To use this project with GitLab CI, you can create a .gitlab-ci.yml file with the following content:


```
test:
  script:
    - chmod +x ./scripts/run-selenium-tests.sh
    - /scripts/run-selenium-tests.sh
```