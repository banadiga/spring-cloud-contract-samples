package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author Marcin Grzejszczak
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//remove::start[]
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "com.example:beer-api-producer")
//remove::end[]
@DirtiesContext
public class BeerVerificationListenerTest extends AbstractTest {

    //remove::start[]
    @Autowired
    StubTrigger stubTrigger;
    //remove::end[]
    @Autowired
    BeerVerificationListener listener;

    //tag::listener_test[]
    @Test
    public void should_increase_the_eligible_counter_when_verification_was_accepted() {
        int initialCounter = this.listener.eligibleCounter.get();

        //remove::start[]
		this.stubTrigger.trigger("accepted_verification");
        //remove::end[]

        then(this.listener.eligibleCounter.get()).isGreaterThan(initialCounter);
    }

    @Test
    public void should_increase_the_noteligible_counter_when_verification_was_rejected() {
        int initialCounter = this.listener.notEligibleCounter.get();

        //remove::start[]
		this.stubTrigger.trigger("rejected_verification");
        //remove::end[]

        then(this.listener.notEligibleCounter.get()).isGreaterThan(initialCounter);
    }
    //end::listener_test[]
}
