package edu.iis.mto.bdd.trains.junit;

import edu.iis.mto.bdd.trains.cucumber.steps.JodaLocalTimeConverter;
import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.ItineraryService;
import edu.iis.mto.bdd.trains.services.ItineraryServiceImplementation;
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
    private String departure = "Radom",
            destination = "Wałbrzych";
    private String[] constStations = {departure, "Kielce", "Ostrowiec", destination};
    private Line line = Line.named("Line123")
            .departingFrom(departure)
            .withStations(constStations);
    private List<LocalTime> trainsTimes;
    List<LocalTime> expectedTrainsTimes;
    private JodaLocalTimeConverter jodaLocalTimeConverter = new JodaLocalTimeConverter();
    LocalTime startingTime = jodaLocalTimeConverter.transform("5:00");

    @Before
    public void setUp() {
        timetableService = mock(TimetableService.class);
        itineraryService = new ItineraryServiceImplementation(timetableService);
        trainsTimes = new ArrayList<>();
        expectedTrainsTimes = new ArrayList<>();
        when(timetableService.findLinesThrough(departure, destination))
                .thenReturn(Collections.singletonList(line));
    }

    @Test
    public void shouldReturnNoneDepartureTimes() {
        trainsTimes = new ArrayList<LocalTime>() {{
            add(new LocalTime("4:50"));
            add(new LocalTime("4:59"));
            add(new LocalTime("5:16"));
            add(new LocalTime("5:17"));
            add(new LocalTime("6:00"));
        }};

        when(timetableService.findArrivalTimes(line, departure))
                .thenReturn(trainsTimes);

        List<LocalTime> resultTimes = itineraryService.findNextDepartures(departure, destination, startingTime);

        assertEquals(0, resultTimes.size());
    }

    @Test
    public void shouldReturn2DepartureTimes() {
        trainsTimes = new ArrayList<LocalTime>() {{
            add(new LocalTime("4:50"));
            add(new LocalTime("5:06"));
            add(new LocalTime("5:14"));
            add(new LocalTime("5:17"));
            add(new LocalTime("6:00"));
        }};

        expectedTrainsTimes = new ArrayList<LocalTime>() {{
            add(new LocalTime("5:06"));
            add(new LocalTime("5:14"));
        }};

        when(timetableService.findArrivalTimes(line, departure))
                .thenReturn(trainsTimes);

        List<LocalTime> resultTimes = itineraryService.findNextDepartures(departure, destination, startingTime);

        assertEquals(expectedTrainsTimes, resultTimes);
    }

    @Test
    public void shouldReturnAllDepartureTimes() {
        trainsTimes = new ArrayList<LocalTime>() {{
            add(new LocalTime("5:01"));
            add(new LocalTime("5:03"));
            add(new LocalTime("5:05"));
            add(new LocalTime("5:07"));
            add(new LocalTime("5:09"));
        }};

        when(timetableService.findArrivalTimes(line, departure))
                .thenReturn(trainsTimes);

        List<LocalTime> resultTimes = itineraryService.findNextDepartures(departure, destination, startingTime);

        assertEquals(trainsTimes, resultTimes);
    }
}