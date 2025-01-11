package com.employeemanagement.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import com.employeemanagement.app.handler.EmployeeHandler;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) {
        EventBus eventBus = vertx.eventBus();
        EmployeeHandler employeeHandler = new EmployeeHandler(eventBus);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.post("/employees").handler(employeeHandler::addEmployee);
        router.get("/employees/:id").handler(employeeHandler::getEmployee);
        router.put("/employees/:id").handler(employeeHandler::updateEmployee);
        router.delete("/employees/:id").handler(employeeHandler::deleteEmployee);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8080)
            .onSuccess(server -> {
                System.out.println("HTTP server started on port " + server.actualPort());
                startPromise.complete();
            })
            .onFailure(startPromise::fail);
    }
}
