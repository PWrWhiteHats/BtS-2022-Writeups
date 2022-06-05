docker build -t ctf . 
docker run --name=ctf --rm -p 8080:80 -it ctf