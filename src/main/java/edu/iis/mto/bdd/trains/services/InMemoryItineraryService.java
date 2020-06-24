package edu.iis.mto.bdd.trains.services;

import com.google.common.collect.Lists;
import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryItineraryService implements ItineraryService {

    private TimetableService timetableService;

    public InMemoryItineraryService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String origin, String destination, LocalTime time) {
        List<Line> foundLines = timetableService.findLinesThrough(origin, destination);
        List<LocalTime> arrivalTimes = Lists.newArrayList();
        for (Line line : foundLines) {
            arrivalTimes.addAll(timetableService.findArrivalTimes(line, origin));
        }
        return arrivalTimes.stream().filter(localTime -> localTime.isAfter(time) && localTime.isBefore(time.plusMinutes(MAX_TRAVEL_TIME))).collect(Collectors.toList());
    }
}
