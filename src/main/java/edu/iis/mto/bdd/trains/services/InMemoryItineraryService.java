package edu.iis.mto.bdd.trains.services;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
        List<LocalTime> filteredArrivalTimes = Lists.newArrayList();
        Iterables.filter(arrivalTimes, Range.greaterThan(time)).forEach(filteredArrivalTimes::add);

        return filteredArrivalTimes;
    }
}
