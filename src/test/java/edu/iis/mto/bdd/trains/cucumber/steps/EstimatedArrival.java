package edu.iis.mto.bdd.trains.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.I;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;
import org.joda.time.LocalTime;

public class EstimatedArrival {

    @Zakładając("^chcę się dostać z \"([^\"]*)\" do \"([^\"]*)\"$")
    public void givenOriginAndDestination(String origin, String destination) {
        throw new PendingException();
    }

    @I("^następny pociąg odjeżdza o (.*) na linii \"([^\"]*)\"$")
    public void andGivenTimeAndLine(@Transform(JodaLocalTimeConverter.class) LocalTime departureTime) {
        throw new PendingException();
    }

    @Gdy("^zapytam o godzinę przyjazdu$")
    public void whenIAskForArrivalTime() {
        throw new PendingException();
    }

    @Wtedy("^powinienem uzyskać następujący szacowany czas przyjazdu: (.*)$")
    public void thenIShouldBeGivenFollowingArrivalEstimate(@Transform(JodaLocalTimeConverter.class) LocalTime estimatedArrivalTime) {
        throw new PendingException();
    }
}
