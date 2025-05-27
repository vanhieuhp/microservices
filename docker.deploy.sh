#!/bin/bash

echo $1
if
BASE_DIR=$(pwd)
#cd $BASE_DIR/accounts && ./gradlew clean build -x test
#cd $BASE_DIR/products && ./gradlew clean build -x test
#cd $BASE_DIR/cards && ./mvnw clean install -DskipTest
#cd $BASE_DIR/loans && ./mvnw clean install -DskipTest

cd $BASE_DIR
#docker build -t accounts:1.0 ./accounts
#docker build -t cards:1.0 ./cards
#docker build -t loans:1.0 ./loans
#docker build -t products:1.0 ./products