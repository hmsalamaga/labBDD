package edu.iis.mto.bdd.trains.junit;

import edu.iis.mto.bdd.trains.cucumber.steps.JodaLocalTimeConverter;
import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.ItineraryService;
import edu.iis.mto.bdd.trains.services.ItineraryServiceImpl;
import edu.iis.mto.bdd.trains.services.TimetableService;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WhenCalculatingArrivalTimes {
    ItineraryService itineraryService;
    TimetableService timetableService;
    List<LocalTime> trainsTimes;
    List<LocalTime> expectedTrainsTimes;

    String departure = "Warszawa";
    String destination = "Kraków";
    String[] stations = {departure, "Szydłowiec", "Kielce", destination};
    Line line = Line.named("S7")
                            .departingFrom(departure)
                            .withStations(stations);
    JodaLocalTimeConverter jodaLocalTimeConverter = new JodaLocalTimeConverter();
    LocalTime startingTime = jodaLocalTimeConverter.transform("7:00");

    @Before
    public void setUp() {
        timetableService = mock(TimetableService.class);
        itineraryService = new ItineraryServiceImpl(timetableService);
        when(timetableService.findLinesThrough(departure, destination))
                .thenReturn(Collections.singletonList(line));
    }

    @Test
    public void shouldReturn4DepartureTimes() {
        trainsTimes = new ArrayList<LocalTime>() {{
            add(new LocalTime("6:50"));
            add(new LocalTime("6:53"));
            add(new LocalTime("6:55"));
            add(new LocalTime("6:59"));
            add(new LocalTime("7:06"));
            add(new LocalTime("7:08"));
            add(new LocalTime("7:10"));
            add(new LocalTime("7:14"));
            add(new LocalTime("8:00"));
        }};

        expectedTrainsTimes = new ArrayList<>() {{
            add(new LocalTime("7:06"));
            add(new LocalTime("7:08"));
            add(new LocalTime("7:10"));
            add(new LocalTime("7:14"));
        }};

        when(timetableService.findArrivalTimes(line, departure))
                .thenReturn(trainsTimes);

        List<LocalTime> resultTimes = itineraryService.findNextDepartures(departure, destination, startingTime);

        assertEquals(expectedTrainsTimes, resultTimes);
    }
}
