package com.shree.serviceProxy.calculator.starter;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyClose;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ProxyHelper;

@VertxGen
@ProxyGen
public interface CalculationServiceInterface {
   void add(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);
   void subtract(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);
   void  multiply(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);
   void divide(Double num1, Double num2, Handler<AsyncResult<Double>> resultHandler);

  // Create is used when you deploy a vertical (ie you deploy the service)
  static CalculationServiceInterface create()
  {
    return new CalculationService();
  }

  // Proxy is used when you use/test the service as server client code

  static CalculationServiceInterface createProxy(Vertx vertx, String address)
  {
   return new CalculationServiceInterfaceVertxEBProxy(vertx, address);
//    return createProxy(CalculationServiceInterface.class, vertx,address);
//   return ProxyHelper.createProxy(CalculationServiceInterface.class, vertx, address);
  }

  @ProxyClose
  void close();
}
