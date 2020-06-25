package edu.iis.mto.bdd.trains.cucumber.steps;

import cucumber.api.Transform;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;
import edu.iis.mto.bdd.trains.services.InMemoryItineraryService;
import edu.iis.mto.bdd.trains.services.InMemoryTimetableService;
import edu.iis.mto.bdd.trains.services.ItineraryService;
import org.joda.time.LocalTime;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OptimalItinerarySteps {
    private ItineraryService itineraryService;
    private List<LocalTime> trainTimes;

    @Zakładając("^pociągi linii \"(.*)\" z \"(.*)\" odjeżdżają ze stacji \"(.*)\" do \"(.*)\" o$")
    public void givenArrivingTrains(String line, String lineStart, String departure, String destination,
                                    @Transform(JodaLocalTimeConverter.class) List<LocalTime> departureTimes) {
        itineraryService = new InMemoryItineraryService(new InMemoryTimetableService());
    }

    @Gdy("^chcę podróżować z \"([^\"]*)\" do \"([^\"]*)\" o (.*)$")
    public void whenIWantToTravel(String departure, String destination,
                                  @Transform(JodaLocalTimeConverter.class) LocalTime startTime) {
        trainTimes = itineraryService.findNextDepartures(departure, destination, startTime);
    }

    @Wtedy("^powinienem uzyskać informację o pociągach o:$")
    public void shouldBeInformedAbout(@Transform(JodaLocalTimeConverter.class) List<LocalTime> expectedTrainTimes) {
        assertEquals(trainTimes, expectedTrainTimes);
    }
}
