#!/bin/bash

# Compile Java files with Gson library
javac -d bin -cp "./lib/*" src/*.java

# Run the project
java -cp "./bin;./lib/*" src.EduChain