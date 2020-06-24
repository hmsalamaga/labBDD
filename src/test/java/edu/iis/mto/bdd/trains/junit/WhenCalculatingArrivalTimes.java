package edu.iis.mto.bdd.trains.junit;

import edu.iis.mto.bdd.trains.cucumber.steps.JodaLocalTimeConverter;
import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.InMemoryItineraryService;
import edu.iis.mto.bdd.trains.services.ItineraryService;
import edu.iis.mto.bdd.trains.services.TimetableService;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WhenCalculatingArrivalTimes {
    private ItineraryService itineraryService;
    private TimetableService timetableService;
    private String origin;
    private String destination;
    private JodaLocalTimeConverter jodaLocalTimeConverter;

    @Before
    public void setUp() {
        timetableService = mock(TimetableService.class);
        itineraryService = new InMemoryItineraryService(timetableService);
        jodaLocalTimeConverter = new JodaLocalTimeConverter();
        origin = "Widzew";
        destination = "Retkinia";
        Line line = Line.named("WZ").departingFrom("Olechów").withStations("Olechów", "Augustów", "Widzew", "Stary Widzew", "Centrum", "Polesie", "Retkinia");
        Line lineTwo = Line.named("WZ2").departingFrom("Zarzew").withStations("Zarzew", "Widzew", "Stary Widzew", "Centrum", "Polesie", "Retkinia");
        Line lineThree = Line.named("WZ3").departingFrom("Augustów").withStations("Augustów", "Widzew", "Stary Widzew", "Centrum", "Polesie", "Retkinia");

        when(timetableService.findLinesThrough(origin, destination)).thenReturn(
                List.of(line, lineTwo, lineThree)
        );

        when(timetableService.findArrivalTimes(line, origin)).thenReturn(
                List.of(
                        jodaLocalTimeConverter.transform("7:01"),
                        jodaLocalTimeConverter.transform("8:01"),
                        jodaLocalTimeConverter.transform("9:01"),
                        jodaLocalTimeConverter.transform("10:01")
                )
        );

        when(timetableService.findArrivalTimes(lineTwo, origin)).thenReturn(
                List.of(
                        jodaLocalTimeConverter.transform("7:02"),
                        jodaLocalTimeConverter.transform("8:02")
                )
        );

        when(timetableService.findArrivalTimes(lineThree, origin)).thenReturn(
                List.of(
                        jodaLocalTimeConverter.transform("7:03"),
                        jodaLocalTimeConverter.transform("8:03"),
                        jodaLocalTimeConverter.transform("9:03")
                )
        );
    }

    @Test
    public void shouldReturnSixResultsWhenStartingAtEight() {
        LocalTime travelStartTime = jodaLocalTimeConverter.transform("8:00");

        List<LocalTime> expectedDepartureTimes = List.of(
                jodaLocalTimeConverter.transform("8:01"),
                jodaLocalTimeConverter.transform("9:01"),
                jodaLocalTimeConverter.transform("10:01"),
                jodaLocalTimeConverter.transform("8:02"),
                jodaLocalTimeConverter.transform("8:03"),
                jodaLocalTimeConverter.transform("9:03")
        );

        List<LocalTime> resultDeparturesTimes = itineraryService.findNextDepartures(origin, destination, travelStartTime);

        assertEquals(expectedDepartureTimes, resultDeparturesTimes);
    }

    @Test
    public void shouldReturnEmptyListWhenStartingAtEleven() {
        LocalTime travelStartTime = jodaLocalTimeConverter.transform("11:00");

        List<LocalTime> resultDeparturesTimes = itineraryService.findNextDepartures(origin, destination, travelStartTime);

        assertTrue(resultDeparturesTimes.isEmpty());
    }
}
