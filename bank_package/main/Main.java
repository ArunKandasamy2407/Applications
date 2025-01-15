package bank_package.main;

import bank_package.entity.User;
import bank_package.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static Main main = new Main();

    public static void main(String[] args)
    {
        while(true) {
            System.out.println("Enter your username: ");
            String username = scanner.next();

            System.out.println("Enter your password: ");
            String password = scanner.next();

            User user = userService.login(username, password);
            if (user != null) {
                if (user.getRole().equals("admin")) {
                    main.initAdmin();
                } else {
                    main.initCustomer(user);
                }
            } else {
                System.out.println("Login Failed.");
            }
        }
    }

    private void initAdmin(){

        System.out.println("You are an admin.");
        boolean isAdmin = true;
        String userName = "";

        while(isAdmin) {
            System.out.println("1.Exit/Logout ");
            System.out.println("2.Create a customer account ");
            System.out.println("3.See all Transactions ");
            System.out.println("4.Check balance ");
            System.out.println("5.Approve check book request ");

            int selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    isAdmin = false;
                    System.out.println("You have been successfully logged out. ");
                    break;
                case 2:
                    main.addNewCustomer();
                    break;
                case 3:
                    System.out.println("Enter the userName: ");
                    userName = scanner.next();
                    main.printTransactions(userName);
                    break;
                case 4:
                    System.out.println("Enter the userName: ");
                    userName = scanner.next();
                    main.checkBankBalance(userName);
                    break;
                case 5:
                    List<String> userIds= main.getUserForCheckBookRequest();
                    System.out.println("Please select user from below...");
                    System.out.println(userIds);
                    System.out.println("Enter the username");

                    userName = scanner.next();
                    if(main.approveCheckBookRequest(userName)) System.out.println("Checkbook request approved. ");
                    else System.out.println("Enter valid userName!!! ");
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private void initCustomer(User user){

        System.out.println("You are a customer.");
        boolean isLoggedIn = true;
        String userName = user.getUsername();

        while(isLoggedIn){
            System.out.println("1.Exit/Logout ");
            System.out.println("2.Check Bank Balance ");
            System.out.println("3.Fund transfer ");
            System.out.println("4.Transaction History ");
            System.out.println("5.Raise CheckBook request ");

            int selectedOption = scanner.nextInt();

            switch (selectedOption) {
                case 1:
                    isLoggedIn = false;
                    System.out.println("You have been successfully logged out. ");
                    break;
                case 2:
                    main.checkBankBalance(userName);
                    break;
                case 3:
                    main.fundTransfer(user);
                    break;
                case 4:
                    main.printTransactions(userName);
                    break;
                case 5:
                    Map<String,Boolean> allRequests = main.getAllCheckBookRequest();
                    if(allRequests.containsKey(userName))
                    {
                        if(allRequests.get(userName)) System.out.println("You have already raised an request and it is already approved. ");
                        else System.out.println("You have already raised an request and it is waiting for approval. ");
                    }
                    else main.raiseCheckbookRequest(userName);
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    private void addNewCustomer()
    {
        System.out.println("Enter UserName: ");
        String username = scanner.next();

        System.out.println("Enter Password: ");
        String password = scanner.next();

        System.out.println("Enter contactNumber: ");
        String contactNumber = scanner.next();

        boolean added = userService.addNewCustomer(username,password,contactNumber);

        if(added) {
            System.out.println("Customer account created. ");
        }
        else {
            System.out.println("Customer account creation failed... ");
        }
    }

    public void checkBankBalance(String userName) {
        Double balance = userService.checkBankBalance(userName);
        if(balance!=null)
            System.out.println("Your Bank balance is "+ balance);
        else
            System.out.println("Invalid user details...");
    }

    private void fundTransfer(User user){
        System.out.println("Enter the payee account userName: ");
        String toAccountName = scanner.next();

        User toUser = getUser(toAccountName);

        if(toUser!=null) {
            System.out.println("valid username");
            System.out.println("Enter amount to transfer: ");

            Double amount = scanner.nextDouble();
            Double balance = userService.checkBankBalance(user.getUsername());

            if(balance>=amount){
                boolean transferState = userService.transferAmount(user.getUsername(),toUser.getUsername(),amount);
                System.out.println(transferState?"Transfer Successful ":"Transfer failed!!!");
            }
            else
                System.out.println("Insufficient Balance : "+balance);
        }
        else
            System.out.println("Please enter valid username!!! ");
    }

    private User getUser(String userName){
        return userService.getUser(userName);
    }

    private void printTransactions(String username) {
        userService.printTransactions(username);
    }

    private void raiseCheckbookRequest(String userName){
        userService.raiseCheckBookRequest(userName);
        System.out.println("Request raised successfully for checkBook. ");
    }

    private Map<String, Boolean> getAllCheckBookRequest(){
        return userService.getAllCheckBookRequest();
    }

    private List<String> getUserForCheckBookRequest(){
        return userService.getUserForCheckBookRequest();
    }

    private boolean approveCheckBookRequest(String userName){
        return userService.approveCheckBookRequest(userName);
    }
}
