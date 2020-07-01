package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.cucumber.steps.JodaLocalTimeConverter;
import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WhenCalculatingArrivalTimes {

    @Mock
    TimetableService timetableService;
    ItineraryService itineraryService;
    JodaLocalTimeConverter jodaLocalTimeConverter;
    Line line;

    @Before
    public void setUp() {
        itineraryService = new ItineraryService(timetableService);
        jodaLocalTimeConverter = new JodaLocalTimeConverter();
        line = Line.named("Linia 1").departingFrom("A")
                .withStations("B", "C", "D", "E");
    }

    @Test
    public void findNextDeparturesShouldReturnListOfTwo() {

        when(timetableService.findLinesThrough(anyString(),anyString())).thenReturn(List.of(line));

        jodaLocalTimeConverter.transform("10:00");
        when(timetableService.findArrivalTimes(line,"B")).thenReturn(List.of(
                jodaLocalTimeConverter.transform("10:00"),
                jodaLocalTimeConverter.transform("10:05"),
                jodaLocalTimeConverter.transform("10:10")
        ));

        List<LocalTime> localTimes = itineraryService.findNextDepartures("B", "E",
                jodaLocalTimeConverter.transform("10:01"));
        List<LocalTime> expected = List.of(jodaLocalTimeConverter.transform("10:05"),
                jodaLocalTimeConverter.transform("10:10"));
        assertEquals(expected,localTimes);
    }
    @Test
    public void findNextDeparturesShouldReturnEmptyList() {

        when(timetableService.findLinesThrough(anyString(),anyString())).thenReturn(List.of(line));

        jodaLocalTimeConverter.transform("10:00");
        when(timetableService.findArrivalTimes(line,"B")).thenReturn(List.of(
                jodaLocalTimeConverter.transform("10:00"),
                jodaLocalTimeConverter.transform("10:05"),
                jodaLocalTimeConverter.transform("10:10")
        ));

        List<LocalTime> localTimes = itineraryService.findNextDepartures("B", "E",
                jodaLocalTimeConverter.transform("10:11"));
        List<LocalTime> expected = List.of();
        assertEquals(expected,localTimes);
    }
    @Test
    public void findNextDeparturesShouldReturnFullList() {

        when(timetableService.findLinesThrough(anyString(),anyString())).thenReturn(List.of(line));

        jodaLocalTimeConverter.transform("10:00");
        when(timetableService.findArrivalTimes(line,"B")).thenReturn(List.of(
                jodaLocalTimeConverter.transform("10:00"),
                jodaLocalTimeConverter.transform("10:05"),
                jodaLocalTimeConverter.transform("10:10")
        ));

        List<LocalTime> localTimes = itineraryService.findNextDepartures("B", "E",
                jodaLocalTimeConverter.transform("9:58"));
        List<LocalTime> expected = List.of(jodaLocalTimeConverter.transform("10:00"),
                jodaLocalTimeConverter.transform("10:05"),
                jodaLocalTimeConverter.transform("10:10"));
        assertEquals(expected,localTimes);
    }
}