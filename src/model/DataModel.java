package model;

import com.sun.javaws.exceptions.InvalidArgumentException;
import entity.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Vsevolod Al'okhin
 */
public class DataModel {
    private static DataModel instance;
    private static ObservableList<Room> cache;
    private static RoomDAO dao;

    private DataModel() throws SQLException {
        cache = FXCollections.observableArrayList();
        dao = RoomDAO.getInstance();
    }

    public static DataModel getInstance() throws SQLException {
        if (instance == null) instance = new DataModel();
        return instance;
    }

    public ObservableList<Room> getCache() {
        return cache;
    }

    public void clear() {
        cache.clear();
    }

    public void add(Room room) throws SQLException, InvalidArgumentException {
        dao.addRoom(room);
        cache.add(room);
    }

    public void addAll(List<Room> rooms) {
        cache.addAll(rooms);
    }

    public void delete(int id) throws SQLException {
        dao.deleteRoom(cache.get(id).getId());
        cache.remove(id);
    }

    public void edit(int id) throws SQLException, InvalidArgumentException {
        Room tmp = cache.get(id);
        dao.updateRoom(tmp);
        cache.set(id, tmp);
    }

    public void load() throws SQLException {
        addAll(dao.getAllRooms());
    }
}
