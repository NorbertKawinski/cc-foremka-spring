package com.codechievement.foremka.v1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;

@Component
public class TestScenarios {
    private final Map<Class<?>, Map<Object, TestScenario>> scenariosByType = new ConcurrentHashMap<>();

    public <T extends TestScenario> T computeIfAbsent(Class<T> clazz, Supplier<T> supplier) {
        var scenariosByInput = scenariosByType.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
        //noinspection unchecked
        return (T) scenariosByInput.computeIfAbsent("default", k -> supplier.get());
    }

    public <T extends TestScenario, IN> T computeIfAbsent(Class<T> clazz, IN input, Function<IN, T> supplier) {
        var scenariosByInput = scenariosByType.computeIfAbsent(clazz, k -> new ConcurrentHashMap<>());
        //noinspection unchecked
        return (T) scenariosByInput.computeIfAbsent(input, k -> supplier.apply(input));
    }

    public <IN, OUT extends TestScenario> OUT get(ScenarioFactory<IN, OUT> factory, IN input) {
        return computeIfAbsent(factory.getScenarioClass(), input, factory::create);
    }
}
