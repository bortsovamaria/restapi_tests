#!/bin/bash
mvn clean
mvn package
#mvn test
mvn allure:serve