package com.employeemanagement.app.handler;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.employeemanagement.app.verticle.EmployeeDBVerticle;

@ExtendWith(VertxExtension.class)
public class EmployeeHandlerTest {

    private Vertx vertx;
    private EmployeeHandler handler;

    @BeforeEach
    void setup(VertxTestContext testContext) {
        vertx = Vertx.vertx();
        vertx.deployVerticle(new EmployeeDBVerticle(), testContext.succeeding(id -> testContext.completeNow()));
        handler = new EmployeeHandler(vertx.eventBus());
    }

    @Test
    void testAddEmployee(VertxTestContext testContext) {
        JsonObject employee = new JsonObject().put("name", "John Doe").put("position", "Developer");

        vertx.eventBus().<JsonObject>request("employee.add", employee, reply -> {
            if (reply.succeeded()) {
                testContext.verify(() -> {
                    JsonObject response = reply.result().body();
                    // Assert the response (e.g., check the generated ID or values)
                    testContext.completeNow();
                });
            } else {
                testContext.failNow(reply.cause());
            }
        });
    }
}

