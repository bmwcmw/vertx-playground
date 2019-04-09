const http = require("http");

const port = 8071;
http.createServer(function (request, response) {
    // if (request.method === 'POST') {
    //     //
    // } else if (request.url === '/') {
    //     //
    // } else {
    //     //
    // }
    response.writeHead(200, {
        'Content-Type': 'text/html'
    });
    response.write('Something');
    response.end();
}).listen(port, () => console.log('Node listening on port ' + port));

const http2 = require('http2');
const fs = require('fs');
const portHttps = 9071;
const hostname = '127.0.0.1';

const options = {
    // key: fs.readFileSync('node.key'),
    // cert: fs.readFileSync('fullchain.pem'),
    requestCert: false,
    rejectUnauthorized: false
};

// Create a plain-text HTTP/2 server
http2.createSecureServer(options).on('stream', (stream, headers) => {
    stream.respond({
        'Content-Type': 'text/html'
    });
    stream.write('Something');
    stream.end();
}).listen(portHttps, hostname, () => {
    console.log('Node listening on port ' + portHttps);
});