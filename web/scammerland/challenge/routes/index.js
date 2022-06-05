const express = require('express');
const router = express.Router();
const bot = require('../bot');

let db;

const response = data => ({ message: data });

router.get('/', (req, res) => {
    return res.render('index');
});

router.post('/api/pay', async (req, res) => {
    const { card_number, owner_name, expire_date, cvv } = req.body;
    if (card_number && owner_name && expire_date && cvv) {
        return db.add_payment(card_number, owner_name, expire_date, cvv)
            .then(() => {
                bot.check_payments(db);
                res.send(response('Payment successful'));
            });
    }
    return res.status(403).send(response('Empty payment details'));
});

router.get('/payments', async (req, res, next) => {
    if (req.ip === '127.0.0.1') {
        return db.get_payments()
            .then(payments => {
                res.render('payments', { payments });
            })
            .catch(() => res.status(500).send(response('Something went wrong!')));
    } else {
        return res.redirect('/');
    }
});

module.exports = database => {
    db = database;
    return router;
};
