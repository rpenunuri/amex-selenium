#!/bin/bash

# Wait for the Selenium Grid hub to be up and running.
while ! nc -z selenium-hub 4444; do
  echo 'Waiting for the Selenium Grid hub...'
  sleep 1
done

# Run the selenium tests.
mvn clean verify