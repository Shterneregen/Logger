@ECHO OFF
set JAR_FILE=listener.jar
IF "%1"=="start" (
    ECHO start Listener
    start javaw -jar %JAR_FILE%
) ELSE IF "%1"=="stop" (
    ECHO stop Listener
	for /f "tokens=1" %%i in ('jps -l ^| find "%JAR_FILE%"') do ( taskkill /F /PID %%i )
) ELSE (
    ECHO please, use "run.bat start" or "run.bat stop"
)
pause