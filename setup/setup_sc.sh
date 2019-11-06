#!/usr/bin/env bash
set -e

while getopts ":v:u:k:h:c:t:" opt; do
  case ${opt} in
    v) SC_VERSION=$OPTARG;;
    u) SC_USERNAME=$OPTARG;;
    k) SC_API_KEY=$OPTARG;;
    h) SC_HOST=$OPTARG;;
    c) SC_PROXY_CREDENTIALS=$OPTARG;;
    t) SC_TUNNEL_IDENTIFIER=$OPTARG;;
  esac
done

cd /tmp
curl https://saucelabs.com/downloads/sc-${SC_VERSION}-linux.tar.gz -o saucelabs.tar.gz
tar -xzf saucelabs.tar.gz
chmod a+x sc-${SC_VERSION}-linux/bin/sc
cd ./sc-${SC_VERSION}-linux/bin

./sc \
  -u ${SC_USERNAME} \
  -k ${SC_API_KEY} \
  -p ${SC_HOST} \
  -w ${SC_PROXY_CREDENTIALS} \
  -i ${SC_TUNNEL_IDENTIFIER} \
  --no-remove-colliding-tunnels \
  &

wget --retry-connrefused --no-check-certificate -T 60 localhost:4445