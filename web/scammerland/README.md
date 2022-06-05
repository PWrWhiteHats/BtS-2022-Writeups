# Scammerland

Someone've sent me this website, which is used by scammers for their frauds. I think I might somehow hack them this way... Can you confirm? Let's show those puppets who is the boss!  

```
When scammer comes, I'ma steal his beat
Take a long night hacking their own thing
Be fighting them all night with all my friends
Sitting on the shores of scammerland, oh
```

> **Hint 1**: I cannot access list of their cards via my browser...but maybe they can get it through theirs?

> **Hint 2**: I don't think the name is validated. I am curious what can be passed in this parameter

## Flag

Check in [flag.txt](flag.txt) file

## How to run

To set up this challenge remote you need to:

1. Use following commands:
```
docker build -t scammerland .
docker run -p 8080:80 --rm -it --name scammerland scammerland
```

## Solution

Check in [SOLUTION.md](solution/SOLUTION.md) file
