package entity;

import javafx.beans.property.*;

/**
 * @author Vsevolod Al'okhin
 */
public class Room {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty pc1number;
    private IntegerProperty pc2number;
    private DoubleProperty pc1power;
    private DoubleProperty pc2power;
    private DoubleProperty workedHours;
    private DoubleProperty energyConsumption;

    public Room() {
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        pc1number = new SimpleIntegerProperty();
        pc2number = new SimpleIntegerProperty();
        pc1power = new SimpleDoubleProperty();
        pc2power = new SimpleDoubleProperty();
        workedHours = new SimpleDoubleProperty();
        energyConsumption = new SimpleDoubleProperty();
    }

//    public Room(IntegerProperty id, StringProperty name, IntegerProperty pc1number, IntegerProperty pc2number,
//                DoubleProperty pc1power, DoubleProperty pc2power, DoubleProperty workedHours) {
//        this.id = id;
//        this.name = name;
//        this.pc1number = pc1number;
//        this.pc2number = pc2number;
//        this.pc1power = pc1power;
//        this.pc2power = pc2power;
//        this.workedHours = workedHours;
//        energyConsumption = calculateEnergyConsumption();
//    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 40) {
            throw new IllegalArgumentException("Computer room name is too long!");
        } else if (name.length() < 1) {
            throw new IllegalArgumentException("Computer room name is too short!");
        }
        this.name.set(name);
    }

    public int getPc1number() {
        return pc1number.get();
    }

    public IntegerProperty pc1numberProperty() {
        return pc1number;
    }

    public void setPc1number(int pc1number) {
        if (pc1number < 0) throw new IllegalArgumentException("Number of computers of type 1 cannot be negative!");
        this.pc1number.set(pc1number);
    }

    public int getPc2number() {
        return pc2number.get();
    }

    public IntegerProperty pc2numberProperty() {
        return pc2number;
    }

    public void setPc2number(int pc2number) {
        if (pc2number < 0) throw new IllegalArgumentException("Number of computers of type 2 cannot be negative!");
        this.pc2number.set(pc2number);
    }

    public double getPc1power() {
        return pc1power.get();
    }

    public DoubleProperty pc1powerProperty() {
        return pc1power;
    }

    public void setPc1power(double pc1power) {
        if (pc1power < 0) throw new IllegalArgumentException("Power of computers of type 1 cannot be negative!");
        this.pc1power.set(pc1power);
    }

    public double getPc2power() {
        return pc2power.get();
    }

    public DoubleProperty pc2powerProperty() {
        return pc2power;
    }

    public void setPc2power(double pc2power) {
        if (pc2power < 0) throw new IllegalArgumentException("Power of computers of type 2 cannot be negative!");
        this.pc2power.set(pc2power);
    }

    public double getWorkedHours() {
        return workedHours.get();
    }

    public DoubleProperty workedHoursProperty() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        if (workedHours < 0) throw new IllegalArgumentException("Worked hours cannot be negative!");
        this.workedHours.set(workedHours);
    }

    public double getEnergyConsumption() {
        return energyConsumption.get();
    }

    public DoubleProperty energyConsumptionProperty() {
        return energyConsumption;
    }

    public DoubleProperty calculateEnergyConsumption() {
        double result = (getPc1power() * getPc1number() + getPc2power() * getPc2number()) * getWorkedHours();
        energyConsumption.set(result);
        return energyConsumption;
    }
}
