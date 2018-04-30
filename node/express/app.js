const express = require('express');
const responseTime = require('response-time');
const compression = require('compression');
const app = express();

app.use(responseTime());
app.use(compression);

app.get('/', (req, res) => res.send('Something'));

const port = 8072;
app.listen(port, () => console.log('Express listening on port ' + port));

const https = require('https');
const fs = require('fs');
const options = {
    // key: fs.readFileSync('node.key'),
    // cert: fs.readFileSync('fullchain.pem'),
    requestCert: false,
    rejectUnauthorized: false
};
const portHttps = 9072;
https.createServer(options, app).listen(portHttps, () => console.log('Express listening on port ' + portHttps));