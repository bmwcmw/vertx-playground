const express = require('express');
const socket = require('./socket');
const app = express();

app.get('/', function (req, res) {
    res.sendFile(__dirname + '/ws.html');
});

const port = 8073;
app.listen(port, function () {
    console.log('Uws listening on port ' + port)
});