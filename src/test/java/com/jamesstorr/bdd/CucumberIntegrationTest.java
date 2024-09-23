package com.jamesstorr.bdd;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/bdd/features",
        glue = {"com.jamesstorr.bdd.steps", "com.jamesstorr.bdd.util", "com.jamesstorr.bdd.config"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports"
        }
)
public class CucumberIntegrationTest {
}
