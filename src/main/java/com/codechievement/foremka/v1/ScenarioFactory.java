package com.codechievement.foremka.v1;

public interface ScenarioFactory<IN, OUT extends TestScenario> {
    Class<OUT> getScenarioClass();

    OUT create(IN input);
}
