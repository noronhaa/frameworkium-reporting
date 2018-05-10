package com.frameworkium.reporting.allure;

import io.qameta.allure.Step;
import io.qameta.allure.model.StepResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

import static io.qameta.allure.Allure.getLifecycle;

public class AllureLogger {

    private static final Logger logger = LogManager.getLogger();
    private static final ThreadLocal<Deque<String>> STEP_UUID_STACK =
            ThreadLocal.withInitial(ArrayDeque::new);

    private AllureLogger() {
        // hide default constructor for this util class
    }

    /**
     * Uses the @Step annotation to log the given log message to Allure.
     *
     * @param message the message to log to the allure report
     */
    @Step("{message}")
    public static void logToAllure(String message) {
        logger.debug("Logged to allure: " + message);
    }


    /**
     * Register the start of a 'Step' to Allure report
     * @param stepName name of the step to parse to Allure report
     */
    public static void stepStart(String stepName) {
        StepResult result = new StepResult().withName(stepName);
        String uuid = UUID.randomUUID().toString();
        getLifecycle().startStep(uuid, result);
        STEP_UUID_STACK.get().addFirst(uuid);
    }

    /**
     * Register to Allure report that a 'Step' has finished
     */
    public static void stepFinish() {
        getLifecycle().stopStep(STEP_UUID_STACK.get().pop());
    }
}
