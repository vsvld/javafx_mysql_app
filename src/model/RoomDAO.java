package model;

import entity.Room;
import utilities.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vsevolod Al'okhin
 */
public class RoomDAO {
    private static Connection con;
    private static RoomDAO instance;
    private static final String SQL_INSERT = "INSERT INTO rooms(room_name, pc_one_number, pc_one_power_watts, pc_two_number, pc_two_power_watts, worked_hours, energy_consumption) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM rooms WHERE id=?";
    private static final String SQL_UPDATE = "UPDATE rooms SET room_name=?, pc_one_number=?, pc_one_power_watts=?, pc_two_number=?, pc_two_power_watts=?, worked_hours=?, energy_consumption=? WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM rooms";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM rooms WHERE id=?";

    private RoomDAO() {
        con = DbUtil.getConnection();
    }

    public static RoomDAO getInstance() {
        if (instance == null) instance = new RoomDAO();
        return instance;
    }

    public void addRoom(Room room) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, room.getName());
            preparedStatement.setInt(2, room.getPc1number());
            preparedStatement.setDouble(3, room.getPc1power());
            preparedStatement.setInt(4, room.getPc2number());
            preparedStatement.setDouble(5, room.getPc2power());
            preparedStatement.setDouble(6, room.getWorkedHours());
            preparedStatement.setDouble(7, room.getEnergyConsumption());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(int roomId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, roomId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoom(Room room) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, room.getName());
            preparedStatement.setInt(2, room.getPc1number());
            preparedStatement.setDouble(3, room.getPc1power());
            preparedStatement.setInt(4, room.getPc2number());
            preparedStatement.setDouble(5, room.getPc2power());
            preparedStatement.setDouble(6, room.getWorkedHours());
            preparedStatement.setDouble(7, room.getEnergyConsumption());
            preparedStatement.setInt(8, room.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);
            while (rs.next()) rooms.add((Room) rs);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public Room getRoomById(int roomId) {
        Room room = new Room();

        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) room = (Room) rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }
}
