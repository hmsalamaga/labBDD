package edu.iis.mto.bdd.trains.services;

import edu.iis.mto.bdd.trains.model.Line;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class ItineraryServiceImpl implements ItineraryService {

    TimetableService timetableService;

    public ItineraryServiceImpl(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    @Override
    public List<LocalTime> findNextDepartures(String departure, String destination, LocalTime startTime) {
        List<Line> lines = timetableService.findLinesThrough(departure, destination);
        List<LocalTime> nextDepartures = new ArrayList<>();

        for (Line line : lines) {
            List<LocalTime> tempDepartures = timetableService.findArrivalTimes(line, departure);

            for (LocalTime localTime : tempDepartures) {
                if (localTime.isAfter(startTime) && localTime
                        .isBefore(startTime.plusMinutes(DEFAULT_PERIOD_TIME))) {
                    nextDepartures.add(localTime);
                }
            }
        }

        return nextDepartures;
    }
}
