import java.util.*;

// Stock class to represent individual stocks
class Stock {
    private String symbol;
    private String name;
    private double price;
    
    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void displayInfo() {
        System.out.println(symbol + " - " + name + " | Price: $" + String.format("%.2f", price));
    }
}

// Transaction class to record buy/sell operations
class Transaction {
    private String type; // "BUY" or "SELL"
    private String stockSymbol;
    private int quantity;
    private double pricePerShare;
    private Date date;
    
    public Transaction(String type, String stockSymbol, int quantity, double pricePerShare) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.date = new Date();
    }
    
    public void displayTransaction() {
        System.out.println(type + " | " + stockSymbol + " | Qty: " + quantity + 
                         " | Price: $" + String.format("%.2f", pricePerShare) + 
                         " | Total: $" + String.format("%.2f", quantity * pricePerShare));
    }
}

// Portfolio item to track stocks owned by user
class PortfolioItem {
    private String stockSymbol;
    private int quantity;
    private double avgBuyPrice;
    
    public PortfolioItem(String stockSymbol, int quantity, double avgBuyPrice) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.avgBuyPrice = avgBuyPrice;
    }
    
    public String getStockSymbol() {
        return stockSymbol;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void addQuantity(int qty, double price) {
        // Calculate new average buy price
        double totalCost = (avgBuyPrice * quantity) + (price * qty);
        quantity += qty;
        avgBuyPrice = totalCost / quantity;
    }
    
    public void reduceQuantity(int qty) {
        quantity -= qty;
    }
    
    public double getAvgBuyPrice() {
        return avgBuyPrice;
    }
    
    public void displayPortfolioItem(double currentPrice) {
        double invested = avgBuyPrice * quantity;
        double currentValue = currentPrice * quantity;
        double profitLoss = currentValue - invested;
        
        System.out.println(stockSymbol + " | Qty: " + quantity + 
                         " | Avg Buy: $" + String.format("%.2f", avgBuyPrice) +
                         " | Current: $" + String.format("%.2f", currentPrice) +
                         " | P/L: $" + String.format("%.2f", profitLoss));
    }
}

// User class to represent a trader
class User {
    private String username;
    private double balance;
    private HashMap<String, PortfolioItem> portfolio;
    private ArrayList<Transaction> transactionHistory;
    
    public User(String username, double initialBalance) {
        this.username = username;
        this.balance = initialBalance;
        this.portfolio = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }
    
    public String getUsername() {
        return username;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public boolean buyStock(Stock stock, int quantity) {
        double totalCost = stock.getPrice() * quantity;
        
        if (balance >= totalCost) {
            balance -= totalCost;
            
            // Add to portfolio
            if (portfolio.containsKey(stock.getSymbol())) {
                portfolio.get(stock.getSymbol()).addQuantity(quantity, stock.getPrice());
            } else {
                portfolio.put(stock.getSymbol(), new PortfolioItem(stock.getSymbol(), quantity, stock.getPrice()));
            }
            
            // Record transaction
            transactionHistory.add(new Transaction("BUY", stock.getSymbol(), quantity, stock.getPrice()));
            return true;
        }
        return false;
    }
    
    public boolean sellStock(Stock stock, int quantity) {
        if (portfolio.containsKey(stock.getSymbol())) {
            PortfolioItem item = portfolio.get(stock.getSymbol());
            
            if (item.getQuantity() >= quantity) {
                double totalEarning = stock.getPrice() * quantity;
                balance += totalEarning;
                
                item.reduceQuantity(quantity);
                
                // Remove from portfolio if quantity becomes 0
                if (item.getQuantity() == 0) {
                    portfolio.remove(stock.getSymbol());
                }
                
                // Record transaction
                transactionHistory.add(new Transaction("SELL", stock.getSymbol(), quantity, stock.getPrice()));
                return true;
            }
        }
        return false;
    }
    
    public void displayPortfolio(HashMap<String, Stock> allStocks) {
        System.out.println("\n===== PORTFOLIO =====");
        System.out.println("User: " + username);
        System.out.println("Cash Balance: $" + String.format("%.2f", balance));
        System.out.println("\nHoldings:");
        
        if (portfolio.isEmpty()) {
            System.out.println("No stocks in portfolio.");
        } else {
            double totalValue = balance;
            for (PortfolioItem item : portfolio.values()) {
                Stock stock = allStocks.get(item.getStockSymbol());
                item.displayPortfolioItem(stock.getPrice());
                totalValue += stock.getPrice() * item.getQuantity();
            }
            System.out.println("\nTotal Portfolio Value: $" + String.format("%.2f", totalValue));
        }
    }
    
    public void displayTransactionHistory() {
        System.out.println("\n===== TRANSACTION HISTORY =====");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : transactionHistory) {
                t.displayTransaction();
            }
        }
    }
}

// Main trading platform class
public class StockTradingPlatform {
    private HashMap<String, Stock> stocks;
    private User currentUser;
    private Scanner scanner;
    
    public StockTradingPlatform() {
        stocks = new HashMap<>();
        scanner = new Scanner(System.in);
        initializeStocks();
    }
    
    // Initialize some sample stocks
    private void initializeStocks() {
        stocks.put("AAPL", new Stock("AAPL", "Apple Inc.", 175.50));
        stocks.put("GOOGL", new Stock("GOOGL", "Google", 140.25));
        stocks.put("MSFT", new Stock("MSFT", "Microsoft", 380.75));
        stocks.put("TSLA", new Stock("TSLA", "Tesla", 245.00));
        stocks.put("AMZN", new Stock("AMZN", "Amazon", 178.30));
    }
    
    // Simulate market price changes
    private void updateMarketPrices() {
        Random random = new Random();
        for (Stock stock : stocks.values()) {
            // Random price change between -5% to +5%
            double changePercent = (random.nextDouble() * 10) - 5;
            double newPrice = stock.getPrice() * (1 + changePercent / 100);
            stock.setPrice(newPrice);
        }
        System.out.println("Market prices updated!");
    }
    
    // Display all available stocks
    private void displayMarket() {
        System.out.println("\n===== MARKET DATA =====");
        for (Stock stock : stocks.values()) {
            stock.displayInfo();
        }
    }
    
    // Main menu
    private void showMenu() {
        System.out.println("\n===== STOCK TRADING PLATFORM =====");
        System.out.println("1. View Market Data");
        System.out.println("2. Buy Stock");
        System.out.println("3. Sell Stock");
        System.out.println("4. View Portfolio");
        System.out.println("5. View Transaction History");
        System.out.println("6. Update Market Prices");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }
    
    // Buy stock process
    private void buyStockProcess() {
        displayMarket();
        System.out.print("\nEnter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();
        
        if (stocks.containsKey(symbol)) {
            Stock stock = stocks.get(symbol);
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            
            if (quantity > 0) {
                double totalCost = stock.getPrice() * quantity;
                System.out.println("Total cost: $" + String.format("%.2f", totalCost));
                System.out.print("Confirm purchase? (yes/no): ");
                String confirm = scanner.next();
                
                if (confirm.equalsIgnoreCase("yes")) {
                    if (currentUser.buyStock(stock, quantity)) {
                        System.out.println("Purchase successful!");
                    } else {
                        System.out.println("Insufficient balance!");
                    }
                }
            } else {
                System.out.println("Invalid quantity!");
            }
        } else {
            System.out.println("Stock not found!");
        }
    }
    
    // Sell stock process
    private void sellStockProcess() {
        System.out.print("\nEnter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();
        
        if (stocks.containsKey(symbol)) {
            Stock stock = stocks.get(symbol);
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            
            if (quantity > 0) {
                double totalEarning = stock.getPrice() * quantity;
                System.out.println("Total earning: $" + String.format("%.2f", totalEarning));
                System.out.print("Confirm sale? (yes/no): ");
                String confirm = scanner.next();
                
                if (confirm.equalsIgnoreCase("yes")) {
                    if (currentUser.sellStock(stock, quantity)) {
                        System.out.println("Sale successful!");
                    } else {
                        System.out.println("You don't own enough shares!");
                    }
                }
            } else {
                System.out.println("Invalid quantity!");
            }
        } else {
            System.out.println("Stock not found!");
        }
    }
    
    // Start the platform
    public void start() {
        System.out.println("Welcome to Stock Trading Platform!");
        System.out.print("Enter your username: ");
        String username = scanner.next();
        System.out.print("Enter starting balance: $");
        double balance = scanner.nextDouble();
        
        currentUser = new User(username, balance);
        System.out.println("\nAccount created successfully!");
        
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    displayMarket();
                    break;
                case 2:
                    buyStockProcess();
                    break;
                case 3:
                    sellStockProcess();
                    break;
                case 4:
                    currentUser.displayPortfolio(stocks);
                    break;
                case 5:
                    currentUser.displayTransactionHistory();
                    break;
                case 6:
                    updateMarketPrices();
                    break;
                case 7:
                    System.out.println("Thank you for using Stock Trading Platform!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
    
    // Main method
    public static void main(String[] args) {
        StockTradingPlatform platform = new StockTradingPlatform();
        platform.start();
    }
}