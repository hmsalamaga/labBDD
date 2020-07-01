package edu.iis.mto.bdd.trains.junit;

import edu.iis.mto.bdd.trains.cucumber.steps.JodaLocalTimeConverter;
import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.ItineraryService;
import edu.iis.mto.bdd.trains.services.ItineraryServiceImplementation;
import edu.iis.mto.bdd.trains.services.TimetableService;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WhenCalculatingArrivalTimes {
    @Mock
    private TimetableService timetableService;
    private ItineraryService itineraryService;
    private List<LocalTime> expectedReturnedTimes;
    private List<LocalTime> startingTimes;
    private String from = "from", to = "to";
    private String[] stations = {from, "intermediate1", "intermediate2", to};
    private Line line = Line.named("line").departingFrom(from).withStations(stations);
    private JodaLocalTimeConverter jodaLocalTimeConverter = new JodaLocalTimeConverter();
    private LocalTime startTime;

    @Before
    public void setUp() {
        itineraryService = new ItineraryServiceImplementation(timetableService);
        expectedReturnedTimes = new ArrayList<>();
        startingTimes = new ArrayList<>();
        Mockito.when(timetableService.findLinesThrough(from, to)).thenReturn(Collections.singletonList(line));
    }

    @Test
    public void shouldReturnAllGivenDepartureTimes() {
        startingTimes = tranformTimesStrings(new String[]{"8:01", "8:02", "8:03", "8:04"});
        expectedReturnedTimes = startingTimes;
        startTime = jodaLocalTimeConverter.transform("8:00");
        Mockito.when(timetableService.findArrivalTimes(line, from)).thenReturn(startingTimes);
        List<LocalTime> returnedByTesting = itineraryService.findNextDepartures(from, to, startTime);
        assertEquals(expectedReturnedTimes, returnedByTesting);
    }

    @Test
    public void shouldReturnThreeGivenDepartureTimes() {
        startingTimes = tranformTimesStrings(new String[]{"8:01", "8:03", "8:04", "8:05"});
        expectedReturnedTimes.addAll(startingTimes);
        expectedReturnedTimes.remove(0);
        startTime = jodaLocalTimeConverter.transform("8:02");
        Mockito.when(timetableService.findArrivalTimes(line, from)).thenReturn(startingTimes);
        List<LocalTime> returnedByTesting = itineraryService.findNextDepartures(from, to, startTime);
        assertEquals(expectedReturnedTimes, returnedByTesting);
    }

    @Test
    public void shouldReturnEmptyList() {
        startingTimes = tranformTimesStrings(new String[]{"8:01", "8:02", "8:03", "8:04"});
        startTime = jodaLocalTimeConverter.transform("8:05");
        Mockito.when(timetableService.findArrivalTimes(line, from)).thenReturn(startingTimes);
        List<LocalTime> returnedByTesting = itineraryService.findNextDepartures(from, to, startTime);
        assertEquals(0, returnedByTesting.size());
    }

    private List<LocalTime> tranformTimesStrings(String[] strings) {
        List<LocalTime> result = new ArrayList<>();
        for (String string : strings) {
            result.add(jodaLocalTimeConverter.transform(string));
        }
        return result;
    }
}
