package model;

import com.sun.javaws.exceptions.InvalidArgumentException;
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
    private static final String SQL_SELECT_NAMES = "SELECT room_name FROM rooms";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM rooms WHERE id=?";

    private RoomDAO() throws SQLException {
        con = DbUtil.getConnection();
    }

    public static RoomDAO getInstance() throws SQLException {
        if (instance == null) instance = new RoomDAO();
        return instance;
    }

    public void addRoom(Room room) throws SQLException, InvalidArgumentException {
        // check unique fields before updating db
        checkUniqueness(room);

        PreparedStatement preparedStatement = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, room.getName());
        preparedStatement.setInt(2, room.getPc1number());
        preparedStatement.setDouble(3, room.getPc1power());
        preparedStatement.setInt(4, room.getPc2number());
        preparedStatement.setDouble(5, room.getPc2power());
        preparedStatement.setDouble(6, room.getWorkedHours());
        preparedStatement.setDouble(7, room.getEnergyConsumption());

        preparedStatement.executeUpdate();

        // TODO find out, how it works
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) room.setId(generatedKeys.getInt(1));

        preparedStatement.close();
    }

    public void deleteRoom(int roomId) throws SQLException {
        PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE);
        preparedStatement.setInt(1, roomId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void updateRoom(Room room) throws SQLException, InvalidArgumentException {
        // check unique fields before updating db
        checkUniqueness(room);

        PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
        preparedStatement.setString(1, room.getName());
        preparedStatement.setInt(2, room.getPc1number());
        preparedStatement.setDouble(3, room.getPc1power());
        preparedStatement.setInt(4, room.getPc2number());
        preparedStatement.setDouble(5, room.getPc2power());
        preparedStatement.setDouble(6, room.getWorkedHours());
        preparedStatement.setDouble(7, room.getEnergyConsumption());
        preparedStatement.setInt(8, room.getId());
    }


    public List<Room> getAllRooms() throws SQLException {
        List<Room> rooms = new ArrayList<>();

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);

        while (rs.next()) {
            Room room = new Room();

            room.setId(rs.getInt("id"));
            room.setName(rs.getString("room_name"));
            room.setPc1number(rs.getInt("pc_one_number"));
            room.setPc1power(rs.getDouble("pc_one_power_watts"));
            room.setPc2number(rs.getInt("pc_two_number"));
            room.setPc2power(rs.getDouble("pc_two_power_watts"));
            room.setWorkedHours(rs.getDouble("worked_hours"));
            room.calculateEnergyConsumption();

            rooms.add(room);
        }

        statement.close();
        return rooms;
    }

    public Room getRoomById(int roomId) throws SQLException {
        Room room = new Room();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_SELECT_BY_ID);
        preparedStatement.setInt(1, roomId);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) room = (Room) rs;

        return room;
    }

    private List<String> getRoomNames() throws SQLException {
        List<String> names = new ArrayList<>();

        PreparedStatement preparedStatement = con.prepareStatement(SQL_SELECT_NAMES);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) names.add(rs.getString("room_name"));

        return names;
    }

    private void checkUniqueness(Room room) throws SQLException {
        for (String name : getRoomNames())
            if (name.equals(room.getName()))
                throw new IllegalArgumentException("Computer room with this name already exists!");
    }
}
