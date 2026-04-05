package ru.aston.model;

import java.util.Comparator;
import java.util.Objects;

public class Car {
    private final String model;
    private final int power;
    private final int year;

    public static final Comparator<Car> byModel = Comparator.comparing(Car::getModel);
    public static final Comparator<Car> byPower = Comparator.comparingInt(Car::getPower);
    public static final Comparator<Car> byYear = Comparator.comparingInt(Car::getYear);

    public String getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", power=" + power +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return power == car.power && year == car.year && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, power, year);
    }

    public Car (CarBuilder builder){
        this.model = builder.model;
        this.power = builder.power;
        this.year = builder.year;
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public static class CarBuilder {
        private String model;
        private int power;
        private int year;

        private CarBuilder() {}

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder power(int power) {
            this.power = power;
            return this;
        }

        public CarBuilder year(int year) {
            this.year = year;
            return this;
        }

        public Car build(){
            if(this.model == null || this.model.isBlank()){
                throw new IllegalStateException("model is null");
            }
            if(this.power < 0){
                throw new IllegalStateException("power is negative");
            }
            if(this.year < 0){
                throw new IllegalStateException("year is negative");
            }
            return new Car(this);
        }
    }
}
