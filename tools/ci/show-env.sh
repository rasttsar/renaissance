#!/bin/bash

set -uoe pipefail

set -x

java -version
javac -version
git --version

cat /os/release
uname -a
