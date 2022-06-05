FROM ubuntu:20.04

RUN \
    apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get -y install --no-install-recommends \
        ucspi-tcp \
        python3 \
    && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
RUN useradd -m ctf
WORKDIR /home/ctf
COPY start.sh start.sh 
COPY flag.txt flag.txt 
COPY chal.py chal.py
RUN chmod 755 /home/ctf/flag.txt
RUN chown -R root:root /home/ctf

EXPOSE 2439
CMD ["/home/ctf/start.sh"]
