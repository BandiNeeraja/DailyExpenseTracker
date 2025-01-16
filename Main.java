import java.text.SimpleDateFormat;
import java.util.*;

// Expense class to store expense details
class Expense {
    private double amount;
    private String category;
    private String description;
    private Date date;

    public Expense(double amount, String category, String description, Date date) {
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    @Override
    public String toString() {
        return "Amount: Rs" + amount + ", Category: " + category + ", Description: " + description + ", Date: "
                + getFormattedDate();
    }
}

// ExpenseManager to manage expenses
class ExpenseManager {
    private List<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public double getTotalExpenses() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public List<Expense> getExpensesByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return expenses.stream()
                .filter(exp -> sdf.format(exp.getDate()).equals(sdf.format(date)))
                .toList();
    }

    public List<Expense> getExpensesByMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        return expenses.stream().filter(exp -> {
            cal.setTime(exp.getDate());
            return cal.get(Calendar.MONTH) + 1 == month && cal.get(Calendar.YEAR) == year;
        }).toList();
    }

    public List<Expense> getExpensesByYear(int year) {
        Calendar cal = Calendar.getInstance();
        return expenses.stream().filter(exp -> {
            cal.setTime(exp.getDate());
            return cal.get(Calendar.YEAR) == year;
        }).toList();
    }

    public void displayExpenses(List<Expense> expensesList) {
        if (expensesList.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            expensesList.forEach(System.out::println);
        }
    }
}

// Main class to handle user input
public class Main {
    private ExpenseManager expenseManager;
    private Scanner scanner;

    public Main() {
        this.expenseManager = new ExpenseManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Expense Tracker has started!");

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Total Expenses");
            System.out.println("3. View Expenses by Day");
            System.out.println("4. View Expenses by Month");
            System.out.println("5. View Expenses by Year");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> System.out.printf("Total Expenses: Rs%.2f%n", expenseManager.getTotalExpenses());
                case 3 -> viewExpensesByDay();
                case 4 -> viewExpensesByMonth();
                case 5 -> viewExpensesByYear();
                case 6 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addExpense() {
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter category (Food, Travel, etc.): ");
        String category = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        Date date = new Date(); // Current date and time
        Expense expense = new Expense(amount, category, description, date);
        expenseManager.addExpense(expense);
        System.out.println("Expense added successfully!");
    }

    private void viewExpensesByDay() {
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateString = scanner.nextLine();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            List<Expense> dailyExpenses = expenseManager.getExpensesByDate(date);
            expenseManager.displayExpenses(dailyExpenses);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }

    private void viewExpensesByMonth() {
        System.out.print("Enter month (1-12): ");
        int month = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        List<Expense> monthlyExpenses = expenseManager.getExpensesByMonth(month, year);
        expenseManager.displayExpenses(monthlyExpenses);
    }

    private void viewExpensesByYear() {
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        List<Expense> yearlyExpenses = expenseManager.getExpensesByYear(year);
        expenseManager.displayExpenses(yearlyExpenses);
    }

    public static void main(String[] args) {
        new Main().start();
    }
}
</create_file>
