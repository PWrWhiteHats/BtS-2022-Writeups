// Kudos to original authors of challenge from HTB CTF rayhan0x01, Makelaris and Makelaris jr

const sqlite = require('sqlite-async');

class Database {
    constructor(db_file) {
        this.db_file = db_file;
        this.db = undefined;
    }

    async connect() {
        this.db = await sqlite.open(this.db_file);
    }
    async migrate() {
        return this.db.exec(`
			DROP TABLE IF EXISTS payments;

			CREATE TABLE IF NOT EXISTS payments (
				id              INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
				owner_name      VARCHAR(500) NOT NULL,
                card_number     VARCHAR(500) NOT NULL,
                expire_date     VARCHAR(500) NOT NULL,
                cvv             VARCHAR(500) NOT NULL,
				created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
			);
		`);
    }

    async add_payment(card_number, owner_name, expire_date, cvv) {
        return new Promise(async (resolve, reject) => {
            try {
                let stmt = await this.db.prepare('INSERT INTO payments (card_number, owner_name, expire_date, cvv) VALUES (?,?,?,?)');
                resolve(await stmt.run(card_number, owner_name, expire_date, cvv));
            } catch (e) {
                reject(e);
            }
        });
    }

    async get_payments() {
        return new Promise(async (resolve, reject) => {
            try {
                let stmt = await this.db.prepare('SELECT * FROM payments');
                let records = await stmt.all();
                // Clear records if too much in DB
                if (records.length > 4) {
                    (await this.db.prepare('DELETE FROM payments;')).run();
                }
                resolve(records);
            } catch (e) {
                reject(e);
            }
        });
    }

}

module.exports = Database;