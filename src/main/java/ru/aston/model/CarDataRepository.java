package ru.aston.model;

import java.util.Map;

public class CarDataRepository {
    private static final Map<String, Integer> MODELS_POWER = Map.ofEntries(
            Map.entry("Aurus Senat", 598),
            Map.entry("Lada Vesta Sportline", 118),
            Map.entry("UAZ Patriot", 150),
            Map.entry("Moskvich 6", 174),
            Map.entry("Evolute i-JET", 646),
            Map.entry("Tesla Model S Plaid", 1020),
            Map.entry("Ford Mustang Dark Horse", 500),
            Map.entry("Chevrolet Corvette Z06", 670),
            Map.entry("Dodge Durango SRT Hellcat", 710),
            Map.entry("Cadillac CT5-V Blackwing", 668),
            Map.entry("Nissan GT-R Nismo", 600),
            Map.entry("Toyota GR Supra", 340),
            Map.entry("Honda Civic Type R (FL5)", 329),
            Map.entry("Subaru WRX", 271),
            Map.entry("Lexus RC F", 472),
            Map.entry("BMW M5 Competition", 625),
            Map.entry("Mercedes-AMG E63 S", 612),
            Map.entry("Audi RS7 Performance", 630),
            Map.entry("Porsche 911 Turbo S", 650),
            Map.entry("Volkswagen Golf R", 320)
    );

    public static Map<String, Integer> getModelsPower() {
        return MODELS_POWER;
    }
}
