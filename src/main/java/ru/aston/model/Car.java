package ru.aston.model;

public class Car {
    private final String model;
    private final int power;
    private final int year;

    public Car(CarBuilder builder) {
        this.model = builder.model;
        this.power = builder.power;
        this.year = builder.year;
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }

    public String getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }

    public int getYear() {
        return year;
    }

    public static class CarBuilder {
        private String model;
        private int power;
        private int year;

        private CarBuilder() {
        }

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

        public Car build() {
            return new Car(this);
        }
    }
}
