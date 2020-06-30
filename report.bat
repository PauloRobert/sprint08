@echo off
SET DEVELOPMENT_HOME=C:\Eclipse_Novo\Workspace\

cd %DEVELOPMENT_HOME%\ApiSprint08\
call mvn clean install surefire-report:report
call mvn site -DgenerateReports=false

EXIT /B