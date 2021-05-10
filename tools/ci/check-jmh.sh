#!/bin/bash
source "$(dirname "$0")/common.sh"

ls -l "$RENAISSANCE_JMH_JAR" || find "$( dirname "$( dirname "$RENAISSANCE_JMH_JAR" )" )"

# Ensure that the JMH bundle contains non-empty BenchmarkList
test $(unzip -p "$RENAISSANCE_JMH_JAR" META-INF/BenchmarkList | wc -l) -gt 0
