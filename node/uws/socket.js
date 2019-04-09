var WebSocketServer = require('uws').Server;
var wss = new WebSocketServer({ port: 9073 });

function onMessage(message) {
    console.log('onMessage: ' + message);
}

wss.on('connection', function(ws) {
    ws.on('message', onMessage);
    ws.send('Something');
});