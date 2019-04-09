package httpserver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import javax.ws.rs.core.MediaType;

public class BasicHttpServerVerticle extends AbstractVerticle {

    static class Logger {
        private static Logger logger;

        private Logger() {
        }

        public static Logger getInstance() {
            if (logger == null) {
                logger = new Logger();
            }
            return logger;
        }

        void info(String msg) {
            System.out.println(msg);
        }
    }

    private static Logger LOGGER = Logger.getInstance();

    private static final int HTTP_PORT = 8070;
    private static final int HTTPS_PORT = 9070;
    private static Vertx vertx;

    public static void main(String[] args) {

        // deploy it
        vertx = Vertx.vertx(); // new VertxOptions().setWorkerPoolSize(1)
        // The number of instances deployed is the number of cores of the machine.
        int instances = Runtime.getRuntime().availableProcessors();

        DeploymentOptions opt = new DeploymentOptions().setInstances(instances);
        vertx.deployVerticle(BasicHttpServerVerticle.class, opt);
    }


    @Override
    public void start() {
//        vertx.exceptionHandler(throwable -> LOGGER.error("exception: {}, message: {}", throwable.getClass().getName(), throwable.getMessage()));

        Router router = setupRouter();

        HttpServer serverHttp = createHttpServer();

        listenHttpServer(serverHttp, router, HTTP_PORT);
    }

    private HttpServer createHttpServer() {
        return vertx.createHttpServer(
            new HttpServerOptions()
                .setTcpQuickAck(true)
                .setTcpCork(true)
                .setCompressionSupported(true)
                .setTcpKeepAlive(true)
                .setIdleTimeout(200));
    }

//    private HttpServer createHttpsServer() {
//        PemKeyCertOptions pemKeyCertOptions = new PemKeyCertOptions();
//        PemTrustOptions pemTrustOptions = new PemTrustOptions();
//        try {
//            String sslPrivkeyFile = ConfigurationManager.getConfiguration().getString("ssl_privkey_file");
//            pemKeyCertOptions = pemKeyCertOptions.addKeyPath(sslPrivkeyFile);
//
//            String sslCertFullchainFile = ConfigurationManager.getConfiguration().getString("ssl_cert_fullchain_file");
//            pemKeyCertOptions = pemKeyCertOptions.addCertPath(sslCertFullchainFile);
//
//            String sslTrustFile = ConfigurationManager.getConfiguration().getString("ssl_trust_file");
//            pemTrustOptions = pemTrustOptions.addCertPath(sslTrustFile);
//        } catch (RuntimeException e) {
//            LOGGER.error("Error while fetching PEM certificates. Please check in resources folder.", e);
//        }
//        HttpServerOptions httpsOptions =
//            new HttpServerOptions()
//                .setTcpQuickAck(true)
//                .setTcpCork(true)
//                .setCompressionSupported(true)
//                .setTcpKeepAlive(true)
//                .setIdleTimeout(200)
//                .setSsl(true)
//                .setKeyCertOptions(pemKeyCertOptions)
//                .setTrustOptions(pemTrustOptions)
//                .addEnabledSecureTransportProtocol("TLSv1.2");
//        if (OpenSSLEngineOptions.isAvailable()) {
//            httpsOptions.setOpenSslEngineOptions(new OpenSSLEngineOptions().setSessionCacheEnabled(true));
//        }
//
//        // Cipher suites in OpenSSL format.
//        String[] ciphers = {
//            "ECDHE-ECDSA-AES256-GCM-SHA384", "ECDHE-RSA-AES256-GCM-SHA384",
//            "ECDHE-ECDSA-CHACHA20-POLY1305", "ECDHE-RSA-CHACHA20-POLY1305",
//            "ECDHE-ECDSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-GCM-SHA256",
//            "ECDHE-ECDSA-AES256-SHA384", "ECDHE-RSA-AES256-SHA384",
//            "ECDHE-ECDSA-AES128-SHA256", "ECDHE-RSA-AES128-SHA256"};
//        httpsOptions.getEnabledCipherSuites().addAll(Arrays.asList(ciphers));
//
//        return vertx.createHttpServer(httpsOptions);
//    }

    /**
     * Setup the server's request handler with the router and tell the server to listen the port defined in the configs file
     */
    private void listenHttpServer(HttpServer server, Router router, int port) {
        server.exceptionHandler(throwable -> {
        });
        server.requestHandler(router::accept)
            .listen(port, asyncResult -> {
                if (asyncResult.failed()) {
                    Throwable cause = asyncResult.cause();
                    cause.printStackTrace();
                } else {
                    LOGGER.info("Start listening to port " + port);
                }
            });
    }

    /**
     * Setup our router to make it redirect incoming request to the bidHandler.
     */
    private Router setupRouter() {
        Router router = Router.router(vertx);
        // router.route() // Add a route with no matching criteria, i.e. it matches all requests or failures.
        router.get("/json").consumes(MediaType.APPLICATION_JSON).produces(MediaType.APPLICATION_JSON).handler(context -> {
            JsonObject bodyAsJson = context.getBodyAsJson();
            Boolean status = bodyAsJson.getBoolean("status");
            HttpServerResponse response = context.response();
            response.headers().add("Content-Type", MediaType.APPLICATION_JSON);
            response.write(new JsonObject().put("status", status).encode()).setStatusCode(status ? 200 : 500).end();
        });
        router.get("/raw").consumes(MediaType.TEXT_HTML).produces(MediaType.TEXT_HTML).handler(context -> {
            HttpServerResponse response = context.response();
            response.headers().add("Content-Type", MediaType.TEXT_HTML);
            response.write("Something").end();
        });

        return router;
    }
}
