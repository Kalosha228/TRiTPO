package DataStructure;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerAccount implements Serializable {

    private String login;
    private String password;
    private ArrayList<Integer> customerMoviesList;
    private ArrayList<Integer> watchedCustomerMoviesList;

    private boolean authorizeStatus;

    public CustomerAccount() {
    }

    public CustomerAccount(String login, String password) {
        authorizeStatus = false;
        this.login = login;
        this.password = password;
        customerMoviesList = new ArrayList<>();
        watchedCustomerMoviesList = new ArrayList<>();
    }

    public void setCustomerMoviesList(ArrayList<Integer> moviesList) {
        this.customerMoviesList = moviesList;
    }

    public void setWatchedCustomerMoviesList(ArrayList<Integer> moviesList) {
        this.customerMoviesList = moviesList;
    }

    public ArrayList<Integer> getCustomerMoviesList() {
        return customerMoviesList;
    }

    public ArrayList<Integer> getWatchedCustomerMoviesList() {
        return watchedCustomerMoviesList;
    }

    public void setUserLogin(String userLogin) {

    }

    public void setUserPassword(String userPasssword) {

    }

    public String getUserLogin() {
        return this.login;
    }

    public String getUserPassword() {
        return this.password;
    }

    public int getIndexMovieInUserCatalog(int movieId) {
        for (int i = 0; i < customerMoviesList.size(); i++)
            if (customerMoviesList.get(i) == movieId)
                return i;
        return -1;
    }

    public CustomerAccount getStatus() {
        return this;
    }

    public void restoreState(CustomerAccount acc) {
        this.login = acc.getUserLogin();
    }

    public boolean checkIsMovieInCatalog(int id){
        for(int i:customerMoviesList){
            if(i==id)
                return true;
        }
        return false;
    }

    public int getWatchedIndexMovieInUserCatalog(int newMovieId) {
        for(int i:watchedCustomerMoviesList){
            if(i==newMovieId)
                return i;
        }
        return -1;
    }

}
