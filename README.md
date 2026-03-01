
# cc-foremka-spring 

A framework for managing test data in Spring applications.


# Features

* Define test data scenarios using Spring-injectable beans
* Improve test performance by reusing test data across multiple test cases


# Usage

1. Add the dependency to your project:

```xml
<dependency>
    <groupId>com.codechievement.foremka</groupId>
    <artifactId>cc-foremka-spring</artifactId>
    <version>0.0.1</version>
    <scope>test</scope>
</dependency>
```

2. Define a scenario representation that implements `TestScenario`:

```java
import com.codechievement.foremka.v1.TestScenario;

public record UserScenario(String username, String email) implements TestScenario {}
```

3. Create a factory that implements `ScenarioFactory` to build your scenario from an input:

```java
import com.codechievement.foremka.v1.ScenarioFactory;
import org.springframework.stereotype.Component;

@Component
public class UserScenarioFactory implements ScenarioFactory<String, UserScenario> {
    @Override
    public Class<UserScenario> getScenarioClass() {
        return UserScenario.class;
    }

    @Override
    public UserScenario create(String username) {
        return new UserScenario(username, username + "@test.com");
    }
}
```

4. Create a provider by extending `ScenarioProvider` which is the main interface for obtaining scenario instances in your tests:

```java
import com.codechievement.foremka.v1.ScenarioProvider;
import org.springframework.stereotype.Component;

@Component
public class UserScenarioProvider extends ScenarioProvider<String, UserScenario> {}
```

5. Inject and use the provider to obtain scenario instances:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.junit.jupiter.api.Test;

@SpringJUnitConfig
class MyIntegrationTest {

    @Autowired
    private UserScenarioProvider userScenarioProvider;

    @Test
    void testWithScenario() {
        UserScenario alice = userScenarioProvider.get("alice");

        assertThat(alice.username(), is("alice"));
        assertThat(alice.email(), is("alice@test.com"));
    }

    @Test
    void scenariosAreCachedByInput() {
        UserScenario first  = userScenarioProvider.get("bob");
        UserScenario second = userScenarioProvider.get("bob");

        assertThat(first.username(), is("bob"));
        assertThat(first, is(sameInstance(second)));
    }
}
```

Scenarios are created lazily on the first call to `get()` and cached by their input,  
so every subsequent call with the same input returns the same instance.  
Different inputs produce different scenario instances.


# Roadmap

* Improve test performance by persisting scenarios in database between test runs
