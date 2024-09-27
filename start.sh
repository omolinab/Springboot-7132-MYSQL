#!/bin/bash

PROJECT=business-application
DATABASE=mysql

export M2_HOME=$HOME/.m2

echo -----------------------------
echo build MODEL
echo -----------------------------
(
  cd $PROJECT-model/
  mvn clean install
)

echo -----------------------------
echo build KJAR
echo -----------------------------
(
  cd $PROJECT-kjar/
  mvn clean install
)

echo -----------------------------
echo running APP
echo -----------------------------
(
  cd $PROJECT-service/
  mvn clean install

  #Uncomment to debug
  mvn \
    -P$DATABASE \
    spring-boot:run \
    -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Dorg.kie.server.startup.strategy=LocalContainersStartupStrategy -Dspring.profiles.active=$DATABASE -Dorg.kie.maven.resolver.folder=$HOME/.m2/repository" 

  #mvn spring-boot:run \
  #  -Dorg.kie.server.startup.strategy=LocalContainersStartupStrategy \
  #  -Dspring.profiles.active=$DATABASE \
  #  -P$DATABASE
  
)

