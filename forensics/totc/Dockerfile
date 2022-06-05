FROM gcr.io/distroless/base-debian11 as base1

COPY fake_flag.txt /lib/hyperll.so

FROM debian:bullseye-slim as base2

RUN echo SGVyZSBpcyBsYXllciAyIGZvciB5b3UK && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base3

RUN echo SGVyZSBpcyBsYXllciAzIGZvciB5b3UK && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base4

RUN echo SGVyZSBpcyBsYXllciA0IGZvciB5b3UK && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base5

RUN echo SGVyZSBpcyBsYXllciA1IGZvciB5b3UK && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base6

RUN echo SGVyZSBpcyBsYXllciA2IGZvciB5b3UK && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM python:3-slim-bullseye as base7

RUN echo cHJpbnQoJ05vcGUsIG5vdCB0aGlzIHRpbWUnKQo= | base64 -d > /bin/chall.py

FROM debian:bullseye-slim as base8

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base9

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base10

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base11

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base12

RUN bash -c 'for t in {1..50}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

COPY flag.txt /lib/python4.51

RUN bash -c 'for t in {52..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base13

RUN echo SGVyZSBpcyBsYXllciAxMyBmb3IgeW91Cg== && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base14

RUN echo SGVyZSBpcyBsYXllciAxNCBmb3IgeW91Cg== && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base15

RUN echo SGVyZSBpcyBsYXllciAxNSBmb3IgeW91Cg== && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base16

RUN echo SGVyZSBpcyBsYXllciAxNiBmb3IgeW91Cg== && apt-get update && apt-get upgrade && uname -a > /usr/ctf

FROM debian:bullseye-slim as base17

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base18

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base19

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM debian:bullseye-slim as base20

RUN bash -c 'for t in {1..100}; do head -c 19 /dev/urandom > /lib/python4.$t; done'

FROM gcr.io/distroless/base-debian11 as final

LABEL org.opencontainers.image.authors="Arqsz"
LABEL org.opencontainers.image.version="1.1"
LABEL org.opencontainers.image.description="Container image for Break the Syntax 2022"
LABEL flag="You thought that flag may be here?"

COPY --from=base1 / /
COPY --from=base2 / /
COPY --from=base3 / /
COPY --from=base4 / /
COPY --from=base5 / /
COPY --from=base6 / /
COPY --from=base7 / /
COPY --from=base8 / /
COPY --from=base9 / /
COPY --from=base10 / /
COPY --from=base11 / /
COPY --from=base12 / /
COPY --from=base13 / /
COPY --from=base14 / /
COPY --from=base15 / /
COPY --from=base16 / /
COPY --from=base17 / /
COPY --from=base18 / /
COPY --from=base19 / /
COPY --from=base20 / /

RUN ldconfig

RUN apt-get install -y oathtool

RUN find /l* -iname "*y*" -type f -delete
RUN find /u* -iname "*" ! -iname "*i*" ! -iname "oathtool" -type f -delete
RUN find /b* -iname "*" -type f -delete

USER 1000

ENTRYPOINT ["oathtool"]
CMD ["-h"]
