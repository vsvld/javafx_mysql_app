package model;

import java.sql.Connection;

/**
 * @author Vsevolod Al'okhin
 */
public class RoomDAO {
    private static Connection con;
    private static RoomDAO instance;
    private static final String SQL_INSERT = "INSERT INTO rooms(room_name, pc_one_number, pc_one_power_watts, pc_two_number, pc_one_power_watts, worked_hours) VALUES (?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM rooms WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE rooms SET room_name=?, pc_one_number=?, pc_one_power_watts=?, pc_two_number=? WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM rooms";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM rooms WHERE id=?";
}
