@echo off

if "%2"=="" (
  echo Usage: %0 ^<year^> ^<day^>
  exit /b 1
)

set day=%2
set year=%1
set cookie=<Session Cookie>

curl -H "Cookie: session=%cookie%" "https://adventofcode.com/%year%/day/%day%/input" > "input.txt"