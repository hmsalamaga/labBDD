package edu.iis.mto.bdd.trains.cucumber.steps;

import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.Transform;
import cucumber.api.java.pl.I;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;
import org.joda.time.LocalTime;

public class EstimateArrivalTime {

    @Zakładając("^chcę dostać się z (.*) do (.*)$")
    public void chcęDostaćSięZFromDoTo(String from, String to) {
        throw new PendingException();
    }

    @I("^pociąg odjeżdża o (.*) na linii (.*)$")
    public void pociągOdjeżdżaODepartureTimeNaLiniiLine(
            @Transform(JodaLocalTimeConverter.class) LocalTime departureTime, String line) {
        throw new PendingException();
    }

    @Gdy("^zapytam o godzinę przyjazdu pociągu$")
    public void zapytamOGodzinęPrzyjazduPociągu() {
        throw new PendingException();
    }

    @Wtedy("^uzyskam szacowany czas przyjazdu: (.*)$")
    public void uzyskamSzacowanyCzasPrzyjazduArrivalTime(
            @Transform(JodaLocalTimeConverter.class) LocalTime arrivalTime) {
        throw new PendingException();
    }
}
