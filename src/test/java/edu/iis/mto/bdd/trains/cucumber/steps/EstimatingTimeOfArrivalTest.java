package edu.iis.mto.bdd.trains.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.I;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;

public class EstimatingTimeOfArrivalTest {
    @Zakładając("^chcę się dostać z \"([^\"]*)\" do \"([^\"]*)\"$")
    public void chcęSięDostaćZDo(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @I("^następny pociąg odjeżdża o (\\d+):(\\d+) na linii \"([^\"]*)\"$")
    public void następnyPociągOdjeżdżaONaLinii(int arg0, int arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Gdy("^zapytam o godzinę przyjazdu$")
    public void zapytamOGodzinęPrzyjazdu() {

    }

    @Wtedy("^powinienem uzyskać następujący szacowany czas przyjazdu: (\\d+):(\\d+)$")
    public void powinienemUzyskaćNastępującySzacowanyCzasPrzyjazdu(int arg0, int arg1) {
    }
}

