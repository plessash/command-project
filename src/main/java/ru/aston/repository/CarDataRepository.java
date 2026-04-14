package ru.aston.repository;


import ru.aston.model.Car;

import java.util.List;

public class CarDataRepository {

    private static final List<Car> CARS = List.of(
            new Car("Aurus Senat", 598, 2024),
            new Car("Lada Vesta Sportline", 118, 2023),
            new Car("UAZ Patriot", 150, 2022),
            new Car("Moskvich 6", 174, 2024),
            new Car("Evolute i-JET", 646, 2024),
            new Car("Tesla Model S Plaid", 1020, 2024),
            new Car("Ford Mustang Dark Horse", 500, 2024),
            new Car("Chevrolet Corvette Z06", 670, 2023),
            new Car("Dodge Durango SRT Hellcat", 710, 2023),
            new Car("Cadillac CT5-V Blackwing", 668, 2022),
            new Car("Nissan GT-R Nismo", 600, 2021),
            new Car("Toyota GR Supra", 340, 2022),
            new Car("Honda Civic Type R (FL5)", 329, 2023),
            new Car("Subaru WRX", 271, 2022),
            new Car("Lexus RC F", 472, 2021),
            new Car("BMW M5 Competition", 625, 2021),
            new Car("Mercedes-AMG E63 S", 612, 2020),
            new Car("Audi RS7 Performance", 630, 2024),
            new Car("Porsche 911 Turbo S", 650, 2023),
            new Car("Volkswagen Golf R", 320, 2022)
    );

    public static List<Car> getCars() {
        return CARS;
    }
}