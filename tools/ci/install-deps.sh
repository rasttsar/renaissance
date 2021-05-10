#!/bin/bash

set -uoe pipefail

if [ -z "${GITHUB_ACTION:-}" ]; then
    echo "Only works inside GitHub Actions." >&2
    exit 101
fi

install_on_oracle_linux() {
    # This is really ugly but it is much faster for CI
    # as calling yum would refresh the database first
    case "$1" in
        git)
            rpm -Uvh --nodeps https://yum.oracle.com/repo/OracleLinux/OL7/latest/x86_64/getPackage/git-1.8.3.1-13.el7.x86_64.rpm
            ;;
        unzip)
            rpm -Uvh --nodeps  https://yum.oracle.com/repo/OracleLinux/OL7/latest/x86_64/getPackage/unzip-6.0-21.el7.x86_64.rpm
            ;;
        *)
            yum install --assumeyes "$1"
            ;;
    esac
}

install_on_unknown() {
    echo "Unknown distribution, cannot install." >&2
    cat /etc/os-release >&2
    exit 102
}


do_install() {
    echo "Will install:" "$@"
    "$installer" "$@"
}

distribution_id="$( ( cat /etc/os-release ; echo 'echo $ID'; ) | sh )"
case "$distribution_id" in
    ol)
        installer=install_on_oracle_linux
        ;;
    *)
        installer=install_on_unknown
        ;;
esac

git version &>/dev/null || do_install git
unzip -v &>/dev/null || do_install unzip
