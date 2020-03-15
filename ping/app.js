const http = require("http");
const os = require("os");

console.log("Ping server starting...");

var handler = function(request, response) {
    var remoteAddr = request.connection.remoteAddress;
    console.log("Received request from " + remoteAddr);
    response.writeHead(200);
    response.end(
        "Received request from " +
        remoteAddr +
        "\n" +
        "You've hit " +
        os.hostname() +
        "\n"
    );
};

var www = http.createServer(handler);
www.listen(8080);