#!/bin/bash

target_file=${1:-Dockerfile}
target_image=${2:-bts-totp}

cat <<EOT > $target_file
FROM gcr.io/distroless/base-debian11 as base1

COPY fake_flag.txt /lib/hyperll.so

EOT

for i in {2..6}; do
    filler=$(echo "Here is layer $i for you" | base64)
    cat <<EOT >> $target_file
FROM debian:bullseye-slim as base$i

RUN echo $filler && apt-get update && apt-get upgrade && uname -a > /usr/ctf

EOT
done

cat <<EOT >> $target_file
FROM python:3-slim-bullseye as base7

RUN echo cHJpbnQoJ05vcGUsIG5vdCB0aGlzIHRpbWUnKQo= | base64 -d > /bin/chall.py

EOT

for i in {8..11}; do
    cat <<EOT >> $target_file
FROM debian:bullseye-slim as base$i

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.\$t; done'

EOT
done

cat <<EOT >> $target_file
FROM debian:bullseye-slim as base12

RUN bash -c 'for t in {1..50}; do head -c 19 /dev/urandom > /lib/python4.\$t; done'

COPY flag.txt /lib/python4.51

RUN bash -c 'for t in {52..100}; do head -c 19 /dev/urandom > /lib/python4.\$t; done'

EOT

for i in {13..16}; do
    filler=$(echo "Here is layer $i for you" | base64)
    cat <<EOT >> $target_file
FROM debian:bullseye-slim as base$i

RUN echo $filler && apt-get update && apt-get upgrade && uname -a > /usr/ctf

EOT
done

for i in {17..20}; do
    cat <<EOT >> $target_file
FROM debian:bullseye-slim as base$i

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.\$t; done'

EOT
done

cat <<EOT >> $target_file
FROM gcr.io/distroless/base-debian11 as final

LABEL org.opencontainers.image.authors="Arqsz"
LABEL org.opencontainers.image.version="1.1"
LABEL org.opencontainers.image.description="Container image for Break the Syntax 2022"
LABEL flag="You thought that flag may be here?"

EOT

for i in {1..20}; do
    cat <<EOT >> $target_file
COPY --from=base$i / /
EOT
done

cat <<EOT >> $target_file

RUN ldconfig

RUN apt-get install -y oathtool

RUN find /l* -iname "*y*" -type f -delete
RUN find /u* -iname "*" ! -iname "*i*" ! -iname "oathtool" -type f -delete
RUN find /b* -iname "*" -type f -delete

USER 1000

ENTRYPOINT ["oathtool"]
CMD ["-h"]
EOT

docker build -f $target_file -t $target_image . 