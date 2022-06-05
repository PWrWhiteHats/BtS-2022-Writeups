const puppeteer = require('puppeteer');
const fs = require('fs');

let flag;

const browser_options = {
	headless: true,
	args: [
		'--no-sandbox',
		'--disable-gpu',
		'--disable-preconnect',
		'--disable-translate',
		'--disable-extensions',
		'--disable-file-system',
		'--dns-prefetch-disable',
		'--incognito',
		'--hide-scrollbars',
		'--metrics-recording-only',
		'--mute-audio',
		'--no-first-run',
		'--js-flags=--noexpose_wasm,--jitless'
	]
};

try {
	const path = "/tmp/flag.txt"
	if (fs.existsSync(path)) {
		flag = fs.readFileSync(path, 'utf8');
	} else {
		flag = "BtSCTF{placeholder}"
	}
} catch (err) {
	console.log(`Error {err} occured`)
	process.exit(0);
}

const cookies = [{
	'name': 'flag',
	'value': flag
}];


const check_payments = async (db) => {
	const browser = await puppeteer.launch(browser_options);
	const context = await browser.createIncognitoBrowserContext();
	const page = await context.newPage();
	await page.goto('http://127.0.0.1/');
	await page.setCookie(...cookies);
	await page.goto('http://127.0.0.1/payments', {
		waitUntil: 'networkidle2'
	});
	await browser.close();
	await db.migrate();
};

module.exports = { check_payments };