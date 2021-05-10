#!/bin/bash

set -uoe pipefail

set -x

java -version
javac -version
git --version

cat /etc/os-release
uname -a
