package edu.iis.mto.bdd.trains.junit;

import edu.iis.mto.bdd.trains.cucumber.steps.JodaLocalTimeConverter;
import edu.iis.mto.bdd.trains.model.Line;
import edu.iis.mto.bdd.trains.services.ItineraryService;
import edu.iis.mto.bdd.trains.services.ItineraryServiceClass;
import edu.iis.mto.bdd.trains.services.TimetableService;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WhenCalculatingArrivalTimes {

    @Mock
    private TimetableService timetableService; //dubler
    private ItineraryService itineraryService;
    private String from = "Zakopane", to ="Lodz";
    private String[] stations = {from,"Krakow","Czestochowa",to};
    private Line line = Line.named("FajnaLinia").departingFrom(from).withStations(stations);
    private List<LocalTime> times;
    List<LocalTime> expectedResultTimes;
    private JodaLocalTimeConverter jodaLocalTimeConverter = new JodaLocalTimeConverter();
    LocalTime startTime = jodaLocalTimeConverter.transform("8:00");
    @Before
    public void setUp(){
        itineraryService = new ItineraryServiceClass(timetableService);
        times = new ArrayList<>();
        expectedResultTimes = new ArrayList<>();
        when(timetableService.findLinesThrough(from,to)).thenReturn(Collections.singletonList(line));
    }

    @Test public void returnListOfDeparturesIncludingOnlyTwoDepartures() {
        String[] stringTimes = {"7:56","8:02","8:13","8:32","9:00"};
        initTimes(stringTimes);
        when(timetableService.findArrivalTimes(line,from)).thenReturn(times);
        List<LocalTime> resultTimes = itineraryService.findNextDepartures(from,to,startTime);
        String[] stringExpectedTimes = {"8:02","8:13"};
        initExpectedTimes(stringExpectedTimes);
        assertEquals(expectedResultTimes,resultTimes);
    }

    @Test public void returnEmptyListOfDepartures() {
        String[] stringTimes = {"7:56","8:16","8:32","9:00"};
        initTimes(stringTimes);
        when(timetableService.findArrivalTimes(line,from)).thenReturn(times);
        List<LocalTime> resultTimes = itineraryService.findNextDepartures(from,to,startTime);
        assertEquals(0, resultTimes.size());
    }

    @Test public void returnListOfDeparturesIncludingAllPossibleDepartures() {
        String[] stringTimes = {"8:01","8:02","8:10","8:12","8:14"};
        initTimes(stringTimes);
        when(timetableService.findArrivalTimes(line,from)).thenReturn(times);
        List<LocalTime> resultTimes = itineraryService.findNextDepartures(from,to,startTime);
        String[] stringExpectedTimes = {"8:01","8:02","8:10","8:12","8:14"};
        initExpectedTimes(stringExpectedTimes);
        assertEquals(expectedResultTimes,resultTimes);
    }

    private void initExpectedTimes(String[] times) {
        for(int i=0;i<times.length;i++){
            this.expectedResultTimes.add(jodaLocalTimeConverter.transform(times[i]));
        }
    }

    private void initTimes(String[] times){
        for(int i=0;i<times.length;i++){
            this.times.add(jodaLocalTimeConverter.transform(times[i]));
        }
    }
}
