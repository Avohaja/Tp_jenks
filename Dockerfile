FROM adoptopenjdk:8-jdk-hotspot AS build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Vérifications
RUN ls -la
RUN ls -la .mvn
RUN ls -la .mvn/wrapper

# Vérifier le début du script
RUN head -5 mvnw

# Vérifier la présence éventuelle de CRLF (^M)
RUN cat -A mvnw | head -5

# Rendre exécutable
RUN chmod +x mvnw

# Tester l'interpréteur shell
RUN which sh
RUN sh --version || true

# Tester le wrapper sans exécution directe
RUN sh mvnw --version

# Build Maven
RUN sh mvnw package

RUN mkdir -p target/dependency && \
    (cd target/dependency && jar -xf ../*.jar)

FROM adoptopenjdk:8-jdk-hotspot

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.demo.bankapp.BankApplication"]