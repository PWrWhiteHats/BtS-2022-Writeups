# Time-based one-time container

## Description

We managed to deliver a container image to our clients, so they could easily generate TOTP codes by themselves (security 4 the win). Unfortunately, one of them mentioned that their container is not working always as it should... We can't find the issue so maybe you can tell us, what is wrong with this?

**image**: `docker.io/whitehatspwr/bts-totc-challenge`

**mirror**: `registry.gitlab.com/whitehatspwr/bts-totc-challenge`

## Flag 

Check in [flag.txt](flag.txt) file

## How to build?

You have to be logged in to docker registries at gitlab and dockerhub first.

```
./build-container.sh
./push-image.sh
```

Users get image registry's address such as above in [description](#description)