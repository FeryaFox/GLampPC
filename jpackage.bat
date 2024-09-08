set JAR_NAME=target\GLampPC-1.0-SNAPSHOT-shaded.jar
set MAIN_CLASS=ru.feryafox.glamppc.MainApplication
set APP_NAME=GLampPC

jpackage ^
    --input target ^
    --name %APP_NAME% ^
    --main-jar %JAR_NAME% ^
    --main-class %MAIN_CLASS% ^
    --type exe