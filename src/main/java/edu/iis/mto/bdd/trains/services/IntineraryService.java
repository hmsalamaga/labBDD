package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class IntineraryService {

    private TimetableService timetableService;

    public IntineraryService(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    public List<LocalTime> findNextDepartures(String from, String to, LocalTime time) {
        List<Line> linesThrough = timetableService.findLinesThrough(from, to);

        List<LocalTime> result = new ArrayList<>();
        List<LocalTime> arrivaltimes;
        for (Line line : linesThrough) {
            arrivaltimes = timetableService.findArrivalTimes(line, to);
            for (LocalTime localTime : arrivaltimes) {
                if (localTime.isAfter(time)) {
                    result.add(localTime);
                }
            }

        }

        return result;
    }
}
