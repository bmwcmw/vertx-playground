const express = require('express');
const responseTime = require('response-time');
const bodyParser = require('body-parser');
const compression = require('compression');
const app = express();

app.use(responseTime());
// app.use(compression);
app.get('/raw', (req, res) => res.send('Something'));
// app.use(bodyParser.urlencoded({extended: false})); // parse application/x-www-form-urlencoded
app.use(bodyParser.json()); // enabled for Content-Type: application/json
app.post('/json', (req, res) => {
    let status;
    try {
        status = req.body.status
    } catch (error) {
        status = false
    }
    // console.log(req.body);
    res.setHeader('Content-Type', 'application/json');
    res.status(status ? 200 : 500).send(JSON.stringify({ status: status }));
});

const port = 8072;
const http = require('http');
http.createServer(app).listen(port, () => console.log('Express listening on port ' + port));

const portHttps = 9072;
const https = require('https');
const fs = require('fs');
const options = {
    // key: fs.readFileSync('node.key'),
    // cert: fs.readFileSync('fullchain.pem'),
    requestCert: false,
    rejectUnauthorized: false
};
https.createServer(options, app).listen(portHttps, () => console.log('Express listening on port ' + portHttps));