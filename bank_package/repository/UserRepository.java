package bank_package.repository;

import bank_package.entity.Transaction;
import bank_package.entity.User;

import java.time.LocalDate;
import java.util.*;

public class UserRepository {

    private static Set<User> users = new HashSet<>();
    private static List<Transaction> transactions = new ArrayList<>();
    private static Map<String,Boolean> checkBookRequests = new HashMap<>();

    static {
        User user1 = new User("admin","admin","9876543210","admin",0.0);
        User user2 = new User("user2","user2","9876543211","user",1000.0);
        User user3 = new User("user3","user3","9876543212","user",2000.0);
        User user4 = new User("user4","user4","9876543213","user",25000.0);

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
    }

    public void printUsers()
    {
        System.out.println(users);
    }
    public User login(String username,String password)
    {
        List<User> finalList = users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .toList();

        if(!finalList.isEmpty()){
            return finalList.getFirst();
        }
        return null;
    }

    public boolean addNewCustomer(String username, String password, String contactNumber){
        User newUser = new User(username,password,contactNumber,"user", 1000.0);
        return users.add(newUser);
    }

    public Double checkBankBalance(String userName) {
        List<User> finalList = users.stream().filter(user -> user.getUsername().equals(userName)).toList();
        if(!finalList.isEmpty())
            return finalList.getFirst().getAccountBalance();
        return null;
    }

    public User getUser(String userName){
        List<User> finalList = users.stream().filter(user -> user.getUsername().equals(userName)).toList();
        if(!finalList.isEmpty())
            return finalList.getFirst();
        return null;
    }

    public boolean transferAmount(String senderName, String recieverName, Double amount){
       return debit(senderName,recieverName,amount) && credit(senderName,recieverName,amount);
    }

    private boolean debit(String senderName,String recieverName, Double amount){
        User user = getUser(senderName);
        Double accountbalance = user.getAccountBalance();

        users.remove(user);

        Double finalBalance = accountbalance-amount;
        user.setAccountBalance(finalBalance);

        Transaction transaction = new Transaction(LocalDate.now(),senderName,recieverName,amount,"debit",accountbalance,finalBalance);
        transactions.add(transaction);

        return users.add(user);
    }

    private boolean credit(String senderName, String recieverName, Double amount){
        User user = getUser(recieverName);
        Double accountbalance = user.getAccountBalance();

        users.remove(user);

        Double finalBalance = accountbalance+amount;
        user.setAccountBalance(finalBalance);

        Transaction transaction = new Transaction(LocalDate.now(),recieverName,senderName,amount,"credit",accountbalance,finalBalance);
        transactions.add(transaction);

        return users.add(user);
    }

    public void printTransactions(String userName) {
        List<Transaction> userTransactions = transactions.stream().filter(transaction -> transaction.getUserId().equals(userName)).toList();
        System.out.println("Date\t UserName\t Amount\t TransactionType\t InitalBalance\t FinalBalance");
        System.out.println("--------------------------------------------------------------------------------------");
        for (Transaction t : userTransactions) {
            System.out.println(t.getTransactionDate()
                    + "\t" + t.getForUserId()
                    + "\t" + t.getTransactionAmount()
                    + "\t" + t.getTransactionType()
                    + "\t" + t.getInitialBalance()
                    + "\t" + t.getFinalBalance()
            );
            System.out.println("--------------------------------------------------------------------------------------");
        }

    }

    public void raiseCheckBookRequest(String userName){
            checkBookRequests.put(userName,false);
    }

    public Map<String,Boolean> getAllCheckBookRequest(){
        return checkBookRequests;
    }

    public List<String> getUserForCheckBookRequest(){
        List<String> userNames = new ArrayList<>();

        for (Map.Entry<String,Boolean> entry : checkBookRequests.entrySet()){
            if(!entry.getValue()) userNames.add(entry.getKey());
        }

        return userNames;
    }

    public boolean approveCheckBookRequest(String userName){
        if(checkBookRequests.containsKey(userName)){
            checkBookRequests.put(userName,true);
            return true;
        }
        return false;
    }
}
