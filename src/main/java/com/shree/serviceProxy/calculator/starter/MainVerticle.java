package com.shree.serviceProxy.calculator.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.serviceproxy.ProxyHelper;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
public static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
//  final Router router = Router.router(vertx);
public static final String ADD_ADDRESS = "com.add.calculator";
public static final String SUBTRACT_ADDRESS = "com.subtract.calculator";
public static final String MULTIPLY_ADDRESS = "com.multiply.calculator";
public static final String DIVIDE_ADDRESS = "com.divide.calculator";
CalculationServiceInterface calculationServiceInterface = new CalculationService();

public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
//    exception handler
    vertx.exceptionHandler(error -> {
      LOG.error("Unhandled : ", error);
    });
    vertx.deployVerticle(new MainVerticle(), asyncResult ->{
      if(asyncResult.failed()){
        LOG.error("Failed to deploy ", asyncResult.cause());
        return;
      }
      LOG.info("Deployed {} !", MainVerticle.class.getName());
    });

    vertx.deployVerticle(new OutputVerticle(), asyncResult ->
    {
      if(asyncResult.failed())
      {
        LOG.error("Failed to deploy ", asyncResult.cause());
        return;
      }
      LOG.info("Deployed {} !", OutputVerticle.class.getName());
    });

  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//  router.get("/add/:num1/:num2").handler(this :: add);
//  router.get("/sub/:num1/:num2").handler(this :: sub);
//  router.get("/mul/:num1/:num2").handler(this :: mul);
//  router.get("/div/:num1/:num2").handler(this :: div);

//    register the service to the eventBus by passing address of service and ServiceInterface class
  new ServiceBinder(vertx)
    .setAddress(ADD_ADDRESS)
    .register(CalculationServiceInterface.class, calculationServiceInterface);

    CalculationService service = new CalculationService();
//    new ServiceBinder(vertx)
//      .setAddress(ADD_ADDRESS)
//      .register(CalculationServiceInterface.class, service);

    ProxyHelper.registerService(CalculationServiceInterface.class, vertx, service, SUBTRACT_ADDRESS);

    new ServiceBinder(vertx)
      .setAddress(MULTIPLY_ADDRESS)
        .register(CalculationServiceInterface.class, service);


    new ServiceBinder(vertx)
      .setAddress(DIVIDE_ADDRESS)
        .register(CalculationServiceInterface.class, service);

//    //  get the router and attach it to the HTTP server
//    vertx.createHttpServer()
//      .requestHandler(router)
//      .exceptionHandler(error -> {
//        LOG.error("HTTP Server Error : ", error);
//      })
//      .listen(8888, http -> {
//      if (http.succeeded()) {
//        startPromise.complete();
//        LOG.info("HTTP server started on port 8888");
//      } else {
//        startPromise.fail(http.cause());
//      }
//    });
    startPromise.complete();
  }

//  private void div(RoutingContext routingContext) {
//    routingContext.response().end("Hello from divide");
//  }
//
//  private void mul(RoutingContext routingContext) {
//    routingContext.response().end("Hello from Multiply");
//  }
//
//  private void sub(RoutingContext routingContext) {
//    routingContext.response().end("Hello from subtract");
//  }
//
//  private void add(RoutingContext routingContext) {
//    routingContext.response().end("Hello from add");
//    System.out.println("Hello from addsssssssss");
//    new ServiceBinder(vertx)
//      .setAddress(ADD_ADDRESS)
//      .register(CalculationServiceInterface.class, calculationServiceInterface);
//  }
}
