#!/bin/bash

# Default values
BASE_URL=${BASE_URL:-}
BROWSER=${BROWSER:-}

# Parse arguments
    while (( "$#" > 0 )); do
        case "$1" in
            -u|--url)
            BASE_URL="$2"
            shift 2
            ;;
            -b|--browser)
            BROWSER="$2"
            shift 2
            ;;
            -*|--*=) # unsupported flags
            echo "Error: Unsupported flag $1" >&2
            exit 1
            ;;
            *) # preserve positional arguments
            PARAMS="$PARAMS $1"
            shift
            ;;
        esac
    done

    # set positional arguments in their proper place
    eval set -- "$PARAMS"

# Run the docker-compose command with the environment variables
echo "Building selenium-tests image"
docker-compose build --no-cache --force-rm selenium-tests

echo "Running selenium tests"
if [ "$BROWSER" = "FIREFOX_REMOTE" ]; then
    BROWSER=$BROWSER docker-compose --profile firefox up --scale firefox=2 --no-build --exit-code-from selenium-tests
else
    docker-compose --profile chrome up --scale chrome=2 --no-build --exit-code-from selenium-tests
fi

echo "removing old images"
docker images -a | grep 'selenium-tests' | awk '{print $3}' | xargs docker rmi -f