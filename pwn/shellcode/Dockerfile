FROM ubuntu:20.04

RUN \
    apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get -y install --no-install-recommends \
    ucspi-tcp supervisor \
    && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*  /etc/xinetd.d/*
RUN useradd -m ctf
WORKDIR /home/ctf
COPY chall chall
COPY start.sh start.sh 
COPY flag.txt flag.txt 
RUN chmod 755 /home/ctf/flag.txt
RUN chown -R root:root /home/ctf

EXPOSE 2440

COPY supervisord.conf /etc/supervisord.conf

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
