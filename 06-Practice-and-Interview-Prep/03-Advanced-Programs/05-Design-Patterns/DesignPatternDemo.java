/**
 * Design Patterns Implementation
 * 
 * Demonstrates fundamental Gang of Four design patterns:
 * - Singleton Pattern: Ensure single instance
 * - Factory Pattern: Object creation abstraction
 * - Observer Pattern: Event notification system
 * - Strategy Pattern: Algorithm encapsulation
 * - Builder Pattern: Complex object construction
 */

import java.util.*;

public class DesignPatternDemo {
    
    // ==================== SINGLETON PATTERN ====================
    /**
     * Singleton: Ensure a class has only one instance and provide global access
     * 
     * Use cases: Database connections, logger, configuration manager
     */
    static class DatabaseConnection {
        private static DatabaseConnection instance;
        private String connectionString;
        private boolean connected;
        
        // Private constructor prevents instantiation
        private DatabaseConnection() {
            this.connectionString = "jdbc:mysql://localhost:3306/database";
            this.connected = false;
        }
        
        // Lazy initialization - instance created on first use
        public static synchronized DatabaseConnection getInstance() {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        }
        
        public void connect() {
            connected = true;
            System.out.println("Connected to: " + connectionString);
        }
        
        public void disconnect() {
            connected = false;
            System.out.println("Disconnected from database");
        }
        
        public boolean isConnected() {
            return connected;
        }
        
        public String executeQuery(String query) {
            if (!connected) {
                return "Error: Not connected";
            }
            return "Result of: " + query;
        }
    }
    
    // Eager initialization variant (thread-safe)
    static class EagerSingleton {
        private static final EagerSingleton instance = new EagerSingleton();
        
        private EagerSingleton() {}
        
        public static EagerSingleton getInstance() {
            return instance;
        }
    }
    
    // ==================== FACTORY PATTERN ====================
    /**
     * Factory: Create objects without specifying exact classes
     * 
     * Use cases: Creating different payment processors, file parsers, etc.
     */
    interface PaymentProcessor {
        void processPayment(double amount);
        String getPaymentMethod();
    }
    
    static class CreditCardProcessor implements PaymentProcessor {
        private String cardNumber;
        
        public CreditCardProcessor(String cardNumber) {
            this.cardNumber = cardNumber;
        }
        
        @Override
        public void processPayment(double amount) {
            System.out.println("Processing credit card payment: $" + amount);
            System.out.println("Card: " + cardNumber.substring(cardNumber.length() - 4));
        }
        
        @Override
        public String getPaymentMethod() {
            return "Credit Card";
        }
    }
    
    static class PayPalProcessor implements PaymentProcessor {
        private String email;
        
        public PayPalProcessor(String email) {
            this.email = email;
        }
        
        @Override
        public void processPayment(double amount) {
            System.out.println("Processing PayPal payment: $" + amount);
            System.out.println("Email: " + email);
        }
        
        @Override
        public String getPaymentMethod() {
            return "PayPal";
        }
    }
    
    static class BitcoinProcessor implements PaymentProcessor {
        private String walletAddress;
        
        public BitcoinProcessor(String walletAddress) {
            this.walletAddress = walletAddress;
        }
        
        @Override
        public void processPayment(double amount) {
            System.out.println("Processing Bitcoin payment: " + amount + " BTC");
            System.out.println("Wallet: " + walletAddress);
        }
        
        @Override
        public String getPaymentMethod() {
            return "Bitcoin";
        }
    }
    
    // Factory class
    static class PaymentProcessorFactory {
        public static PaymentProcessor createProcessor(String type, String credential) {
            switch (type.toLowerCase()) {
                case "creditcard":
                    return new CreditCardProcessor(credential);
                case "paypal":
                    return new PayPalProcessor(credential);
                case "bitcoin":
                    return new BitcoinProcessor(credential);
                default:
                    throw new IllegalArgumentException("Unknown payment type: " + type);
            }
        }
    }
    
    // ==================== OBSERVER PATTERN ====================
    /**
     * Observer: Define a subscription mechanism to notify multiple objects
     * about state changes
     * 
     * Use cases: Event handling systems, model-view updates, notification systems
     */
    interface Observer {
        void update(String event, Object data);
    }
    
    static class StockPriceSubject {
        private String symbol;
        private double price;
        private List<Observer> observers = new ArrayList<>();
        
        public StockPriceSubject(String symbol, double initialPrice) {
            this.symbol = symbol;
            this.price = initialPrice;
        }
        
        public void subscribe(Observer observer) {
            observers.add(observer);
            System.out.println("Observer subscribed to " + symbol);
        }
        
        public void unsubscribe(Observer observer) {
            observers.remove(observer);
            System.out.println("Observer unsubscribed from " + symbol);
        }
        
        public void setPriceChange(double newPrice) {
            double oldPrice = price;
            this.price = newPrice;
            double change = newPrice - oldPrice;
            
            // Notify all observers
            for (Observer observer : observers) {
                observer.update("PRICE_CHANGED", 
                    new PriceChangeData(symbol, oldPrice, newPrice, change));
            }
        }
        
        public double getPrice() {
            return price;
        }
    }
    
    static class PriceChangeData {
        String symbol;
        double oldPrice;
        double newPrice;
        double change;
        
        PriceChangeData(String symbol, double oldPrice, double newPrice, double change) {
            this.symbol = symbol;
            this.oldPrice = oldPrice;
            this.newPrice = newPrice;
            this.change = change;
        }
    }
    
    static class Trader implements Observer {
        private String name;
        
        public Trader(String name) {
            this.name = name;
        }
        
        @Override
        public void update(String event, Object data) {
            if ("PRICE_CHANGED".equals(event) && data instanceof PriceChangeData) {
                PriceChangeData changeData = (PriceChangeData) data;
                System.out.println(name + " notified: " + changeData.symbol + 
                    " changed from $" + changeData.oldPrice + 
                    " to $" + changeData.newPrice + 
                    " (change: " + String.format("%.2f", changeData.change) + ")");
            }
        }
    }
    
    static class NewsReporter implements Observer {
        private String station;
        
        public NewsReporter(String station) {
            this.station = station;
        }
        
        @Override
        public void update(String event, Object data) {
            if ("PRICE_CHANGED".equals(event) && data instanceof PriceChangeData) {
                PriceChangeData changeData = (PriceChangeData) data;
                String trend = changeData.change >= 0 ? "UP" : "DOWN";
                System.out.println(station + " reports: " + changeData.symbol + 
                    " moved " + trend + " by " + 
                    String.format("%.2f%%", (changeData.change/changeData.oldPrice)*100));
            }
        }
    }
    
    // ==================== STRATEGY PATTERN ====================
    /**
     * Strategy: Define family of algorithms and make them interchangeable
     * 
     * Use cases: Sorting algorithms, payment methods, compression strategies
     */
    interface CompressionStrategy {
        String compress(String data);
    }
    
    static class GzipCompression implements CompressionStrategy {
        @Override
        public String compress(String data) {
            return "GZIP[" + data + "]";
        }
    }
    
    static class RarCompression implements CompressionStrategy {
        @Override
        public String compress(String data) {
            return "RAR[" + data + "]";
        }
    }
    
    static class ZipCompression implements CompressionStrategy {
        @Override
        public String compress(String data) {
            return "ZIP[" + data + "]";
        }
    }
    
    static class FileArchiver {
        private CompressionStrategy strategy;
        
        public FileArchiver(CompressionStrategy strategy) {
            this.strategy = strategy;
        }
        
        public void setCompressionStrategy(CompressionStrategy strategy) {
            this.strategy = strategy;
        }
        
        public String archiveFile(String filename, String data) {
            System.out.println("Archiving " + filename + " using " + 
                strategy.getClass().getSimpleName());
            return strategy.compress(data);
        }
    }
    
    // ==================== BUILDER PATTERN ====================
    /**
     * Builder: Construct complex objects step by step
     * 
     * Use cases: Creating objects with many optional parameters
     */
    static class ComputerBuilder {
        private String cpu;
        private String motherboard;
        private String ram;
        private String storage;
        private String gpu;
        private String powerSupply;
        private String cooler;
        
        public ComputerBuilder withCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        public ComputerBuilder withMotherboard(String motherboard) {
            this.motherboard = motherboard;
            return this;
        }
        
        public ComputerBuilder withRAM(String ram) {
            this.ram = ram;
            return this;
        }
        
        public ComputerBuilder withStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public ComputerBuilder withGPU(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public ComputerBuilder withPowerSupply(String powerSupply) {
            this.powerSupply = powerSupply;
            return this;
        }
        
        public ComputerBuilder withCooler(String cooler) {
            this.cooler = cooler;
            return this;
        }
        
        public Computer build() {
            return new Computer(cpu, motherboard, ram, storage, gpu, 
                               powerSupply, cooler);
        }
    }
    
    static class Computer {
        String cpu;
        String motherboard;
        String ram;
        String storage;
        String gpu;
        String powerSupply;
        String cooler;
        
        Computer(String cpu, String motherboard, String ram, String storage,
                String gpu, String powerSupply, String cooler) {
            this.cpu = cpu;
            this.motherboard = motherboard;
            this.ram = ram;
            this.storage = storage;
            this.gpu = gpu;
            this.powerSupply = powerSupply;
            this.cooler = cooler;
        }
        
        @Override
        public String toString() {
            return "Computer {\n" +
                "  CPU: " + (cpu != null ? cpu : "Not set") + "\n" +
                "  Motherboard: " + (motherboard != null ? motherboard : "Not set") + "\n" +
                "  RAM: " + (ram != null ? ram : "Not set") + "\n" +
                "  Storage: " + (storage != null ? storage : "Not set") + "\n" +
                "  GPU: " + (gpu != null ? gpu : "Not set") + "\n" +
                "  Power Supply: " + (powerSupply != null ? powerSupply : "Not set") + "\n" +
                "  Cooler: " + (cooler != null ? cooler : "Not set") + "\n" +
                "}";
        }
    }
    
    // ==================== DEMONSTRATIONS ====================
    
    public static void demonstrateSingleton() {
        System.out.println("\n========== SINGLETON PATTERN ==========");
        
        // Both references point to same instance
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        
        System.out.println("Same instance? " + (db1 == db2));
        
        db1.connect();
        System.out.println("db1 connected? " + db1.isConnected());
        System.out.println("db2 connected? " + db2.isConnected());
        
        System.out.println("Query result: " + db2.executeQuery("SELECT * FROM users"));
        
        db1.disconnect();
        System.out.println("db1 connected? " + db1.isConnected());
        System.out.println("db2 connected? " + db2.isConnected());
    }
    
    public static void demonstrateFactory() {
        System.out.println("\n========== FACTORY PATTERN ==========");
        
        // Create different payment processors
        PaymentProcessor creditCard = PaymentProcessorFactory.createProcessor(
            "creditcard", "1234-5678-9012-3456");
        PaymentProcessor paypal = PaymentProcessorFactory.createProcessor(
            "paypal", "user@example.com");
        PaymentProcessor bitcoin = PaymentProcessorFactory.createProcessor(
            "bitcoin", "1A1z7agoat2LWANY2QYYvYzG1nrwtBqaco");
        
        System.out.println("Processing payments with different methods:");
        creditCard.processPayment(99.99);
        System.out.println();
        
        paypal.processPayment(50.00);
        System.out.println();
        
        bitcoin.processPayment(0.005);
    }
    
    public static void demonstrateObserver() {
        System.out.println("\n========== OBSERVER PATTERN ==========");
        
        StockPriceSubject apple = new StockPriceSubject("AAPL", 150.00);
        
        // Create observers
        Trader trader1 = new Trader("John");
        Trader trader2 = new Trader("Jane");
        NewsReporter cnbc = new NewsReporter("CNBC");
        
        // Subscribe to price changes
        apple.subscribe(trader1);
        apple.subscribe(trader2);
        apple.subscribe(cnbc);
        
        System.out.println("Current price: $" + apple.getPrice() + "\n");
        
        // Price changes - notify all observers
        System.out.println("Stock price change to $155.50:");
        apple.setPriceChange(155.50);
        
        System.out.println("\nStock price change to $148.75:");
        apple.setPriceChange(148.75);
        
        // Unsubscribe
        System.out.println("\n" + "=".repeat(40));
        apple.unsubscribe(cnbc);
        System.out.println("Stock price change to $152.00:");
        apple.setPriceChange(152.00);
    }
    
    public static void demonstrateStrategy() {
        System.out.println("\n========== STRATEGY PATTERN ==========");
        
        FileArchiver archiver = new FileArchiver(new GzipCompression());
        
        String data = "Important backup data";
        System.out.println("Original data: " + data);
        
        System.out.println("\nUsing GZIP:");
        String compressed1 = archiver.archiveFile("backup.tar.gz", data);
        System.out.println("Result: " + compressed1);
        
        System.out.println("\nSwitching to ZIP:");
        archiver.setCompressionStrategy(new ZipCompression());
        String compressed2 = archiver.archiveFile("backup.zip", data);
        System.out.println("Result: " + compressed2);
        
        System.out.println("\nSwitching to RAR:");
        archiver.setCompressionStrategy(new RarCompression());
        String compressed3 = archiver.archiveFile("backup.rar", data);
        System.out.println("Result: " + compressed3);
    }
    
    public static void demonstrateBuilder() {
        System.out.println("\n========== BUILDER PATTERN ==========");
        
        // Build a gaming computer
        Computer gamingPC = new ComputerBuilder()
            .withCPU("Intel i9-13900K")
            .withMotherboard("ASUS ROG Maximus Z790")
            .withRAM("64GB DDR5")
            .withStorage("2TB NVMe SSD + 4TB HDD")
            .withGPU("NVIDIA RTX 4090")
            .withPowerSupply("1200W 80+ Platinum")
            .withCooler("NZXT Kraken X73")
            .build();
        
        System.out.println("Gaming PC Configuration:");
        System.out.println(gamingPC);
        
        // Build a budget computer
        Computer budgetPC = new ComputerBuilder()
            .withCPU("AMD Ryzen 5 5600X")
            .withMotherboard("ASRock B450M")
            .withRAM("16GB DDR4")
            .withStorage("256GB SSD")
            .withPowerSupply("500W 80+ Bronze")
            .build();
        
        System.out.println("\nBudget PC Configuration:");
        System.out.println(budgetPC);
    }
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║          DESIGN PATTERNS IMPLEMENTATION                ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        
        demonstrateSingleton();
        demonstrateFactory();
        demonstrateObserver();
        demonstrateStrategy();
        demonstrateBuilder();
        
        System.out.println("\n" + "=".repeat(55));
        System.out.println("All demonstrations completed successfully!");
        System.out.println("=".repeat(55));
    }
}
