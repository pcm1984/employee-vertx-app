package com.employeemanagement.app.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmployeeDBVerticle extends AbstractVerticle {

    private final Map<String, JsonObject> db = new HashMap<>();

    @Override
    public void start(Promise<Void> startPromise) {
        vertx.eventBus().consumer("employee.add", this::addEmployee);
        vertx.eventBus().consumer("employee.get", this::getEmployee);
        vertx.eventBus().consumer("employee.update", this::updateEmployee);
        vertx.eventBus().consumer("employee.delete", this::deleteEmployee);
        startPromise.complete();
    }

    private void addEmployee(Message<JsonObject> message) {
        String id = UUID.randomUUID().toString();
        JsonObject employee = message.body().put("id", id);
        db.put(id, employee);
        message.reply(employee);
    }

    private void getEmployee(Message<String> message) {
        JsonObject employee = db.get(message.body());
        if (employee != null) {
            message.reply(employee);
        } else {
            System.err.println("Employee with ID " + message.body() + " not found");
            message.fail(404, "Employee not found");
        }
    }

    private void updateEmployee(Message<JsonObject> message) {
        String id = message.body().getString("id");
        if (db.containsKey(id)) {
            db.put(id, message.body());
            message.reply(message.body());
        } else {
            System.err.println("Failed to update. Employee with ID " + id + " not found");
            message.fail(404, "Employee not found");
        }
    }

    private void deleteEmployee(Message<String> message) {
        if (db.remove(message.body()) != null) {
            message.reply(null);
        } else {
            System.err.println("Failed to delete. Employee with ID " + message.body() + " not found");
            message.fail(404, "Employee not found");
        }
    }
}
