package com.employeemanagement.app.handler;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class EmployeeHandler {

    private final EventBus eventBus;

    public EmployeeHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void addEmployee(RoutingContext context) {
        JsonObject employee = context.getBodyAsJson();
        if (employee == null || !employee.containsKey("name") || !employee.containsKey("position")) {
            context.response().setStatusCode(400).end("Invalid employee data");
            return;
        }
        eventBus.request("employee.add", employee, reply -> {
            if (reply.succeeded()) {
                context.response().setStatusCode(201).end(reply.result().body().toString());
            } else {
                context.response().setStatusCode(500).end(reply.cause().getMessage());
            }
        });
    }

    public void getEmployee(RoutingContext context) {
        String id = context.pathParam("id");
        eventBus.request("employee.get", id, reply -> {
            if (reply.succeeded()) {
                context.response().end(reply.result().body().toString());
            } else {
                context.response().setStatusCode(404).end(reply.cause().getMessage());
            }
        });
    }

    public void updateEmployee(RoutingContext context) {
        String id = context.pathParam("id");
        JsonObject employee = context.getBodyAsJson().put("id", id);
        eventBus.request("employee.update", employee, reply -> {
            if (reply.succeeded()) {
                context.response().end(reply.result().body().toString());
            } else {
                context.response().setStatusCode(500).end(reply.cause().getMessage());
            }
        });
    }

    public void deleteEmployee(RoutingContext context) {
        String id = context.pathParam("id");
        eventBus.request("employee.delete", id, reply -> {
            if (reply.succeeded()) {
                context.response().setStatusCode(204).end();
            } else {
                context.response().setStatusCode(500).end(reply.cause().getMessage());
            }
        });
    }
}
