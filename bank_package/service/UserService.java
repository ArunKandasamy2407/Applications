package bank_package.service;

import bank_package.entity.User;
import bank_package.repository.UserRepository;

import java.util.List;
import java.util.Map;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public void printUsers() { userRepository.printUsers(); }

    public User login(String username, String password) {
        return userRepository.login(username,password);
    }

    public boolean addNewCustomer(String userName,String password,String contactnumber) {
        return userRepository.addNewCustomer(userName,password,contactnumber);
    }

    public Double checkBankBalance(String userName) {
        return userRepository.checkBankBalance(userName);
    }

    public User getUser(String userName){
        return userRepository.getUser(userName);
    }

    public boolean transferAmount(String senderName, String recieverName, Double amount){
        return userRepository.transferAmount(senderName,recieverName,amount);
    }

    public void printTransactions(String username){
        userRepository.printTransactions(username);
    }

    public void raiseCheckBookRequest(String userName){
        userRepository.raiseCheckBookRequest(userName);
    }

    public Map<String,Boolean> getAllCheckBookRequest() {
        return userRepository.getAllCheckBookRequest();
    }

    public List<String> getUserForCheckBookRequest(){
        return userRepository.getUserForCheckBookRequest();
    }

    public boolean approveCheckBookRequest(String userName){
        return userRepository.approveCheckBookRequest(userName);
    }
}
