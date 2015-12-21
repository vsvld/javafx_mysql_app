package utilities;

import entity.Room;

import java.util.List;

/**
 * @author Vsevolod Al'okhin
 */
public class SummaryInfo {
    public static double calculateWholeEnergyConsumption(List<Room> rooms) {
        double sum = 0;
        for (Room room : rooms) sum += room.getEnergyConsumption();
        return sum;
    }

    public static double calculateAverageEnergyConsumption(List<Room> rooms) {
        return calculateWholeEnergyConsumption(rooms) / rooms.size();
    }
}
