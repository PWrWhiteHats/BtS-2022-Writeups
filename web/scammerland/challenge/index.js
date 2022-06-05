const express = require('express');
const path = require('path');
const routes = require('./routes');
const app = express();
const Database = require('./db');
const db = new Database('payments.db');


app.use(express.json());

app.use(function (req, res, next) {
    req.setTimeout(10000, function () { });
    next();
});
app.set("view engine", "hbs");
app.set('views', './views');
app.use('/static', express.static(path.resolve('static')));
app.use('/', routes(db));

app.all('*', (req, res) => {
    return res.status(404).send({
        message: '404 not found'
    });
});

(async () => {
    await db.connect();
    await db.migrate();
    app.listen(80, '0.0.0.0', () => console.log('Server started on 0.0.0.0:80'));
})();