# Logic Gate Simulator (Work in Progress)

To run application:
```
./mvnw clean javafx:run -pl javafx-gui -DskipTests=true
```

Currently, linking via `jlink` is not working (`simulator` does not use Java 9 modules).
```
mvn clean javafx:jlink -pl javafx-gui
# Will create javafx-gui/target/bzzz-gui/bin/launcher
```