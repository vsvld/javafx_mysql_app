package entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

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

    public Room(IntegerProperty id, StringProperty name, IntegerProperty pc1number, IntegerProperty pc2number,
                DoubleProperty pc1power, DoubleProperty pc2power, DoubleProperty workedHours) {
        this.id = id;
        this.name = name;
        this.pc1number = pc1number;
        this.pc2number = pc2number;
        this.pc1power = pc1power;
        this.pc2power = pc2power;
        this.workedHours = workedHours;
    }

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
        this.name.set(name);
    }

    public int getPc1number() {
        return pc1number.get();
    }

    public IntegerProperty pc1numberProperty() {
        return pc1number;
    }

    public void setPc1number(int pc1number) {
        this.pc1number.set(pc1number);
    }

    public int getPc2number() {
        return pc2number.get();
    }

    public IntegerProperty pc2numberProperty() {
        return pc2number;
    }

    public void setPc2number(int pc2number) {
        this.pc2number.set(pc2number);
    }

    public double getPc1power() {
        return pc1power.get();
    }

    public DoubleProperty pc1powerProperty() {
        return pc1power;
    }

    public void setPc1power(double pc1power) {
        this.pc1power.set(pc1power);
    }

    public double getPc2power() {
        return pc2power.get();
    }

    public DoubleProperty pc2powerProperty() {
        return pc2power;
    }

    public void setPc2power(double pc2power) {
        this.pc2power.set(pc2power);
    }

    public double getWorkedHours() {
        return workedHours.get();
    }

    public DoubleProperty workedHoursProperty() {
        return workedHours;
    }

    public void setWorkedHours(double workedHours) {
        this.workedHours.set(workedHours);
    }

    private DoubleProperty calculateEnergyConsumption() {
        double result = (getPc1power() * getPc1number() + getPc2power() * getPc2number()) * getWorkedHours();
        energyConsumption.set(result);
        return energyConsumption;
    }
}
