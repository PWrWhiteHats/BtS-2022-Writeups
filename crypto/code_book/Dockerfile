FROM python:2.7

RUN \
    apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get -y install --no-install-recommends \
        ucspi-tcp \
    && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*  /etc/xinetd.d/*
RUN pip2 install pycryptodome
RUN useradd -m ctf
WORKDIR /home/ctf
COPY oracle.py /home/ctf/oracle.py
COPY flag.txt /home/ctf/flag.txt 
COPY start.sh /home/ctf/start.sh 
RUN chmod 755 /home/ctf/flag.txt
RUN chown -R root:root /home/ctf

EXPOSE 4444
CMD ["/home/ctf/start.sh"]