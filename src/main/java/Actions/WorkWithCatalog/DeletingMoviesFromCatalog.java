package Actions.WorkWithCatalog;

import DataStructure.CustomerAccount;

import java.util.ArrayList;

public class DeletingMoviesFromCatalog implements ActionsInCatalogFactory {

    public DeletingMoviesFromCatalog(){}


    @Override
    public void actionInCatalog(CustomerAccount customerAccount, int newMovieId) {
        customerAccount.getCustomerMoviesList();
        if (isAvailableThisMovie(customerAccount.getCustomerMoviesList(), newMovieId)) {
            customerAccount.getCustomerMoviesList().remove(customerAccount.getIndexMovieInUserCatalog(newMovieId));
            //deleteFilmFromWatchedFilms(customerAccount, newMovieId);
             System.out.println("yes");
        }
    }

    @Override
    public boolean isAvailableThisMovie(ArrayList<Integer> movieList, int newMovieId) {
        for (int i = 0; i < movieList.size(); i++)
            if (movieList.get(i).equals(newMovieId))
                return true;
        return false;
    }

    public void deleteFilmFromWatchedFilms(CustomerAccount customerAccount, int id){
        for(int i:customerAccount.getWatchedCustomerMoviesList()){
            if(id==i){
                customerAccount.getWatchedCustomerMoviesList().remove(customerAccount.getWatchedIndexMovieInUserCatalog(id));
            }
        }
    }
}
