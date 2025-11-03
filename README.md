# CodeAlpha_Project-2
# Stock Trading Platform

A console-based stock trading simulation application built in Java that allows users to buy and sell stocks, manage their portfolio, and track transaction history.

## Features

- **User Account Management**: Create a trading account with a custom username and starting balance
- **Live Market Data**: View real-time prices for available stocks
- **Stock Trading**: Buy and sell stocks with instant execution
- **Portfolio Management**: Track your stock holdings with profit/loss calculations
- **Transaction History**: Complete record of all buy and sell operations
- **Market Simulation**: Dynamic price updates that simulate market fluctuations (-5% to +5%)

## Available Stocks

The platform comes pre-loaded with five major stocks:

- **AAPL** - Apple Inc.
- **GOOGL** - Google
- **MSFT** - Microsoft
- **TSLA** - Tesla
- **AMZN** - Amazon

## How to Run

1. Ensure you have Java Development Kit (JDK) installed on your system
2. Save the `StockTradingPlatform.java` file
3. Compile the program:
   ```bash
   javac StockTradingPlatform.java
   ```
4. Run the application:
   ```bash
   java StockTradingPlatform
   ```

## Usage Guide

### Getting Started

1. When you launch the application, you'll be prompted to:
   - Enter a username
   - Set your starting balance (e.g., $10000)

### Main Menu Options

1. **View Market Data**: Displays current prices for all available stocks
2. **Buy Stock**: Purchase shares of a specific stock
3. **Sell Stock**: Sell shares from your portfolio
4. **View Portfolio**: See your current holdings, cash balance, and total portfolio value
5. **View Transaction History**: Review all past buy/sell transactions
6. **Update Market Prices**: Simulate market movement with random price changes
7. **Exit**: Close the application

### Trading Stocks

**To Buy:**
- Select option 2 from the main menu
- Enter the stock symbol (e.g., AAPL)
- Specify the quantity you want to purchase
- Confirm the transaction
- The system will check if you have sufficient balance

**To Sell:**
- Select option 3 from the main menu
- Enter the stock symbol you want to sell
- Specify the quantity
- Confirm the transaction
- The system will verify you own enough shares

## Key Components

### Classes

- **Stock**: Represents individual stocks with symbol, name, and price
- **Transaction**: Records buy/sell operations with timestamp
- **PortfolioItem**: Tracks stocks owned by the user with average buy price
- **User**: Manages user account, balance, portfolio, and transaction history
- **StockTradingPlatform**: Main application controller

### Portfolio Features

- **Average Buy Price**: Automatically calculated when buying multiple batches
- **Profit/Loss Tracking**: Real-time calculation based on current market prices
- **Total Portfolio Value**: Combines cash balance and stock holdings

## Example Session

```
Welcome to Stock Trading Platform!
Enter your username: john_doe
Enter starting balance: $10000

Account created successfully!

===== STOCK TRADING PLATFORM =====
1. View Market Data
2. Buy Stock
3. Sell Stock
4. View Portfolio
5. View Transaction History
6. Update Market Prices
7. Exit
Enter your choice: 1

===== MARKET DATA =====
AAPL - Apple Inc. | Price: $175.50
GOOGL - Google | Price: $140.25
MSFT - Microsoft | Price: $380.75
TSLA - Tesla | Price: $245.00
AMZN - Amazon | Price: $178.30
```

## Technical Details

- **Language**: Java
- **Data Structures**: HashMap for stock storage and portfolio management, ArrayList for transaction history
- **Input Handling**: Scanner class for console input
- **Price Formatting**: All prices displayed with 2 decimal places

## Limitations

- Console-based interface only
- Single user session (no multi-user support)
- No data persistence (portfolio resets on restart)
- Simplified market simulation
- No real-time market data integration

## Future Enhancements

Potential improvements could include:
- Database integration for data persistence
- Multiple user account support
- Historical price charts
- More sophisticated market simulation
- Support for limit orders and stop-loss
- Dividend tracking
- GUI interface

## License

This is an educational project demonstrating object-oriented programming concepts in Java.

## Author

Created as a stock trading simulation demonstration.
