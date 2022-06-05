docker build -t ctf . 
docker run --name=ctf --rm -p 18000:80 -it -e WORDPRESS_SITE_URL=localhost:18000 ctf

# Admin password: `tZ633sGZ(xp8WYcbkFdG1Rdj`