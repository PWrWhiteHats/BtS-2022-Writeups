docker build -t ctf . 
docker run -it --name=ctf --rm -p 18000:80 -it ctf