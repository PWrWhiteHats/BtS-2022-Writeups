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
COPY flag.txt flag.txt 
COPY start.sh start.sh 
RUN chmod 755 /home/ctf/flag.txt
RUN chown -R root:root /home/ctf

EXPOSE 2441

COPY supervisord.conf /etc/supervisord.conf
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisord.conf"]
