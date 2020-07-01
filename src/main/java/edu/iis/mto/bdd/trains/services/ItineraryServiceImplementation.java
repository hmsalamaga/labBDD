package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryServiceImplementation implements ItineraryService {

    private TimetableService timetableService;

    public ItineraryServiceImplementation(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String from, String to, LocalTime time) {
        List<Line> linesThrough = timetableService.findLinesThrough(from, to);

        List<LocalTime> result = new ArrayList<>();
        List<LocalTime> arrivaltimes;
        for (Line line : linesThrough) {
            arrivaltimes = timetableService.findArrivalTimes(line, to);
            for (LocalTime localTime : arrivaltimes) {
                if (localTime.isAfter(time)) {
                    if(!localTime.isAfter(time.plusMinutes(MAX_TIME))) {
                        result.add(localTime);
                    }
                }
            }

        }

        return result;
    }
}
