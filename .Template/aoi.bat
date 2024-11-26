@echo off

if "%2"=="" (
  echo Usage: %0 ^<year^> ^<day^>
  exit /b 1
)

set day=%2
set year=%1

set cookie=<Session Cookie>

if not exist "%cd%\..\%year%" (
    mkdir "%cd%\..\%year%"
)

if not exist "%cd%\..\%year%\Day %day%" (
    mkdir "%cd%\..\%year%\Day %day%"
)

curl -H "Cookie: session=%cookie%" "https://adventofcode.com/%year%/day/%day%/input" > "input.txt"

move "input.txt" "%cd%\..\%year%\Day %day%\input.txt"

:: Copy Solution.java and run.bat into the day folder
copy "Solution.java" "%cd%\..\%year%\Day %day%\Solution.java"
copy "run.bat" "%cd%\..\%year%\Day %day%\run.bat"
