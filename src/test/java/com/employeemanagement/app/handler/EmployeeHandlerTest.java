package com.example.handler;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class EmployeeHandlerTest {

    private Vertx vertx;
    private EventBus eventBus;
    private EmployeeHandler handler;

    @BeforeEach
    void setup() {
        vertx = Vertx.vertx();
        eventBus = vertx.eventBus();
        handler = new EmployeeHandler(eventBus);
    }

    @Test
    void testAddEmployee(VertxTestContext testContext) {
        JsonObject employee = new JsonObject().put("name", "John Doe").put("position", "Developer");
        eventBus.<JsonObject>request("employee.add", employee, reply -> {
            if (reply.succeeded()) {
                testContext.verify(() -> {
                    JsonObject response = reply.result().body();
                    testContext.completeNow();
                });
            } else {
                testContext.failNow(reply.cause());
            }
        });
    }

