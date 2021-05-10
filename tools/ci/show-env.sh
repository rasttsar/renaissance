#!/bin/bash

set -uoe pipefail

set -x

java -version
javac -version
git --version || echo "Git not installed." >&2

cat /etc/os-release
uname -a
