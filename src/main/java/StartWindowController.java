import Actions.AuthorizationInSystem;
import Actions.GettingPremiers;
import Actions.SearchMovies;
import Actions.WorkWithCatalog.GettingRecomendations;
import Actions.WorkWithCatalog.MarkMoviesAsWatched;
import Actions.WorkWithUserCatalog;
import DataStructure.AccountsDataBase;
import DataStructure.CustomerAccount;
import com.truedev.kinoposk.api.model.film.FilmExt;
import com.truedev.kinoposk.api.model.navigator.KinopoiskItemType;
import com.truedev.kinoposk.api.model.navigator.NavigatorExt;
import com.truedev.kinoposk.api.service.KinopoiskApiService;
import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.enumerations.MediaType;
import com.uwetrottmann.tmdb2.services.MoviesService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import retrofit2.Response;

/*import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class StartWindowController {

    @FXML
    public Button returnToBackButton;
    @FXML
    public Button authorizeInSystemButton;
    @FXML
    public Label idLabel;
    @FXML
    public Button addMovieButton;
    @FXML
    public Button deleteMovieButton;
    @FXML
    public Label countWatchedMovies;
    @FXML
    public Label countMoviesInUserCatalog;
    @FXML
    public FlowPane flowPaneForCadres;
    @FXML
    public FlowPane flowPaneForShowRecomendationsMovies;
    @FXML
    public ScrollPane scroll3;
    @FXML
    public VBox boxInformationAboutCatalog;
    @FXML
    public CheckBox watchedStatusCheckBox;
    @FXML
    public FlowPane flowPaneForShowWatchedMovies;
    @FXML
    public ScrollPane scroll4;
    public Label companiesLabels;
    public AnchorPane basePane;
    @FXML
    private TextField searchindString;
    @FXML
    private ChoiceBox countryIdsBox;
    @FXML
    private ChoiceBox genreIdsBox;
    @FXML
    private Label sloganLabel;
    @FXML
    private Label movieLengthLabel;
    @FXML
    private Label premierDataLabel;
    @FXML
    private Label countriesLabel;
    @FXML
    private Label ageRatingLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label budgetLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label seriesInfoLabel;
    @FXML
    private FlowPane flowPaneForShowListMovies;
    @FXML
    private Button getPremiersButton;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ImageView imageViewForHandle;

    private SearchMovies searchMovies;
    private Stage stage;
    @FXML
    private Button showCatalogButton;
    @FXML
    private TextField ratingFrom;
    @FXML
    private TextField ratingTo;
    @FXML
    private TextField yearFrom;
    @FXML
    private TextField yearTo;
    @FXML
    private AnchorPane showFilmPane;

    private WorkWithUserCatalog workWithUserCatalog;

    private AuthorizationInSystem authorizationInSystem;

    private GettingPremiers getPremiers;

    private GettingRecomendations getRecomendations;

    private MarkMoviesAsWatched markMoviesAsWatched;

    int indexBackScene;

    public StartWindowController() throws IOException, ClassNotFoundException {
        searchMovies = new SearchMovies();
        initAuthorizationController();
        workWithUserCatalog = new WorkWithUserCatalog();
        getPremiers = new GettingPremiers();
        getRecomendations = new GettingRecomendations();
        markMoviesAsWatched = new MarkMoviesAsWatched();
     //   genreIdsBox.setValue("--");
        readDataBaseFromFile();
    }

    public void setStage(Stage stage) throws IOException {
        this.stage = stage;

        //    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgressWindow.fxml"));


        //StartWindowController st = (StartWindowController) loader.getController();

        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    writeDataBaseInFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getRecomendaitions() throws IOException {
        boxInformationAboutCatalog.setVisible(false);

        scroll3.setVisible(true);
        flowPaneForShowRecomendationsMovies.setVisible(true);

        flowPaneForShowRecomendationsMovies.setHgap(20);
        flowPaneForShowRecomendationsMovies.setVgap(20);

        ;
        indexBackScene = 3;
        if (authorizationInSystem.getStatus()) {

            for (int id : authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList()) {
                MovieResultsPage resultRecomendation = getRecomendations.getRecomendations(id);

                if (resultRecomendation.results.size() > 0)
                    for (int i = 0; i < 5; i++) {
                        if (i < resultRecomendation.results.size()) {

                            Image im = new Image("https://image.tmdb.org/t/p/w500" + resultRecomendation.results.get(i).poster_path, 220, 320, false, false);
                            ImageView imageView = new ImageView(im);
                            Hyperlink nameLink = new Hyperlink(resultRecomendation.results.get(i).title);
                            nameLink.setWrapText(true);
                            int finalI1 = i;

                            nameLink.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {

                                    showFilmPane.setVisible(true);
                                    scroll.setVisible(false);
                                    scroll3.setVisible(false);
                                    scroll4.setVisible(false);
                                    try {
                                        showInforLabelAboutFilmFromTMDB(resultRecomendation.results.get(finalI1));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            VBox v = new VBox(imageView, nameLink);
                            v.setPrefSize(220, 320);
                            flowPaneForShowRecomendationsMovies.getChildren().add(v);
                        }
                    }
            }
        }
    }

    public void initAuthorizationController() throws IOException {
        FXMLLoader l = new FXMLLoader(getClass().getResource("AuthorizeWindow.fxml"));
        Stage st = new Stage();
        Scene scene = new Scene(l.load());
        st.setScene(scene);
        AuthorizationInSystem au = (AuthorizationInSystem) l.getController();
        this.authorizationInSystem = au;
        au.setStage(st);
    }

    public void markMovieAsWatched() {
        if (watchedStatusCheckBox.isSelected()) {
            markMoviesAsWatched.actionInCatalog(authorizationInSystem.getCurrentUserInSystem(), Integer.parseInt(idLabel.getText()));
        } else {
            markMoviesAsWatched.resetWachedStatus(authorizationInSystem.getCurrentUserInSystem(), Integer.parseInt(idLabel.getText()));
        }
    }

    @FXML
    public void doAutorize() throws IOException {
        this.authorizationInSystem.openAutorizeShow(showCatalogButton);
    }

    public void clearFlowPane(FlowPane pane) {
        int countChildrenElements = pane.getChildren().size();
        for (int i = 0; i < countChildrenElements; i++) {
            pane.getChildren().remove(0);
        }
    }

    public void getPremiers() throws IOException {
        Response<MovieResultsPage> response;
        int pageCount = 1;
        indexBackScene = 1;
        scroll.setVisible(true);
        flowPaneForShowListMovies.setVisible(true);
        // returnToBackButton.setVisible(true);

        flowPaneForShowListMovies.setHgap(20);
        flowPaneForShowListMovies.setVgap(20);

        do {

            response = getPremiers.getPremiers(pageCount);
            if (!response.isSuccessful() || response.code() != 200)
                break;

            for (BaseMovie filmInfo : response.body().results) {

                Image im = new Image("https://image.tmdb.org/t/p/w500" + filmInfo.poster_path, 220, 320, false, false);
                ImageView imageView = new ImageView(im);
                Hyperlink nameLink = new Hyperlink(filmInfo.title);
                nameLink.setWrapText(true);

                nameLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        showFilmPane.setVisible(true);
                        scroll.setVisible(false);
                        try {
                            showInforLabelAboutFilmFromTMDB(filmInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                VBox v = new VBox(imageView, nameLink);
                v.setPrefSize(220, 320);
                flowPaneForShowListMovies.getChildren().add(v);
            }
            pageCount++;
        } while (response.code() == 200);
    }

    public void showInforLabelAboutFilmFromTMDB(BaseMovie baseFilm) throws IOException {

        boxInformationAboutCatalog.setVisible(false);
        Movie film = searchMovies.getInformationFromTMDB(baseFilm.id);
        nameLabel.setText(film.title);

        ageRatingLabel.setText(String.valueOf(film.status.value));
        countriesLabel.setText(String.valueOf(film.budget));
        sloganLabel.setText(String.valueOf(film.popularity));
        movieLengthLabel.setText(String.valueOf(film.runtime) + " ");

        genreLabel.setText("");

        for (Genre genre : film.genres)
            genreLabel.setText(genreLabel.getText() + String.valueOf(genre.name) + ", ");

        premierDataLabel.setText(String.valueOf(film.release_date).substring(String.valueOf(film.release_date).length() - 4, String.valueOf(film.release_date).length()));

        budgetLabel.setText("");
        for (Country company : film.production_countries) {
            budgetLabel.setText(budgetLabel.getText() + company.name + " ");
        }

        companiesLabels.setText("");

        for (BaseCompany company : film.production_companies) {
            companiesLabels.setText(companiesLabels.getText() + company.name + " ");
        }

        descriptionLabel.setText(film.overview);

        idLabel.setText(String.valueOf(film.id));

        Image im = new Image("https://image.tmdb.org/t/p/w500" + film.poster_path);
        imageViewForHandle.setImage(im);

        if (authorizationInSystem.getStatus()) {

            addMovieButton.setVisible(true);
            deleteMovieButton.setVisible(true);
            watchedStatusCheckBox.setVisible(true);

            if (authorizationInSystem.getCurrentUserInSystem().checkIsMovieInCatalog(Integer.parseInt(idLabel.getText()))) {
                addMovieButton.setDisable(true);
                deleteMovieButton.setDisable(false);
            } else {
                addMovieButton.setDisable(false);
                deleteMovieButton.setDisable(true);
            }

            //    deleteMovieButton.setDisable(false);

            if (markMoviesAsWatched.isAvailableThisMovie(authorizationInSystem.getCurrentUserInSystem().getWatchedCustomerMoviesList(), film.id))
                watchedStatusCheckBox.setSelected(true);
            else
                watchedStatusCheckBox.setSelected(false);

        } else {
            watchedStatusCheckBox.setVisible(false);
            addMovieButton.setVisible(false);
            deleteMovieButton.setVisible(false);
        }

        returnToBackButton.setVisible(true);
    }

    public void showInfoLabelsAboutCatalog() {
        countMoviesInUserCatalog.setText(countMoviesInUserCatalog.getText().substring(0, 8) + String.valueOf(authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList().size()));
        countWatchedMovies.setText(countWatchedMovies.getText().substring(0, 12) + String.valueOf(authorizationInSystem.getCurrentUserInSystem().getWatchedCustomerMoviesList().size()));
    }

    @FXML
    public void showCatalog() throws IOException {

        Response<Movie> filmInfo;

        if (authorizationInSystem.getStatus()) {

            scroll3.setVisible(false);
            scroll4.setVisible(false);

            flowPaneForShowRecomendationsMovies.setVisible(false);
            boxInformationAboutCatalog.setVisible(true);
            flowPaneForShowListMovies.setVisible(true);

            flowPaneForShowListMovies.setHgap(20);
            flowPaneForShowListMovies.setVgap(20);

            scroll.setVisible(true);
            scroll3.setVisible(false);

            clearFlowPane(flowPaneForShowListMovies);

            showInfoLabelsAboutCatalog();

            workWithUserCatalog.setCurrentCustomerAccount(authorizationInSystem.getCurrentUserInSystem());
            indexBackScene = 1;
            for (int i = 0; i < authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList().size(); i++) {
                filmInfo = searchMovies.getInformationAboutMovie(authorizationInSystem.getCurrentUserInSystem().getCustomerMoviesList().get(i));
                Image im = new Image("https://image.tmdb.org/t/p/w500" + filmInfo.body().poster_path, 220, 320, false, false);

                ImageView imageView = new ImageView(im);
                Hyperlink nameLink = new Hyperlink(filmInfo.body().title);
                nameLink.setWrapText(true);

                Response<Movie> finalFilmInfo = filmInfo;
                nameLink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        showFilmPane.setVisible(true);
                        scroll.setVisible(false);

                        try {
                            showInforLabelAboutFilmFromTMDB(finalFilmInfo.body());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                VBox v = new VBox(imageView, nameLink);
                v.setPrefSize(220, 320);
                flowPaneForShowListMovies.getChildren().add(v);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setHeaderText("Error open user catalog");
            alert.setContentText("You need to authorize in system!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
        }
    }

    public void setOnActionForLink() {

    }

    public int getGenreId(int index) {
        switch (index) {
            case 4:
                return 28;
            case 6:
                return 16;
            case 3:
                return 878;
            case 5:
                return 12;
            case 7:
                return 35;
            case 8:
                return 80;
            case 9:
                return 99;
            case 11:
                return 10751;
            case 10:
                return 18;
            case 12:
                return 14;
            case 13:
                return 36;
            case 14:
                return 27;
            case 15:
                return 9648;
            default:
                return -1;
        }
    }

    public void setParamsForSearch() {
        searchMovies.setKeywords(searchindString.getText());
        //    searchMovies.setCountryId(getCountryId(countryIdsBox.getItems().indexOf(countryIdsBox.getValue())));

        searchMovies.setGenreId(getGenreId(genreIdsBox.getItems().indexOf((String) genreIdsBox.getValue())));
        searchMovies.setRatingFrom(Byte.parseByte(ratingFrom.getText()));

        searchMovies.setRatingTo(Byte.parseByte(ratingTo.getText()));
        searchMovies.setYearFrom(Integer.parseInt(yearFrom.getText()));
        searchMovies.setYearTo(Integer.parseInt(yearTo.getText()));
    }

    public boolean checkInputedParams() {
        if (yearFrom.getText().length() != 0) {
            if (!yearFrom.getText().matches("[0-9]+"))
                return false;
            else
                if(Integer.parseInt(yearFrom.getText())<=0)
                    return false;
        }

        if (yearTo.getText().length() != 0) {
            if (!yearTo.getText().matches("[0-9]+"))
                return false;
            else
            if(Integer.parseInt(yearTo.getText())>2020)
                return false;
        }

        if (ratingFrom.getText().length() != 0) {
            if (!ratingFrom.getText().matches("[0-9.,]+"))
                return false;
        }

        if (ratingTo.getText().length() != 0) {
            if (!ratingTo.getText().matches("[0-9.,]+"))
                return false;

        }
        return true;
    }

    @FXML
    public void doSearchMovies() throws IOException {

        if (!checkInputedParams()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error dialog");
            alert.setHeaderText("Error searching information");
            alert.setContentText("You need to check parameters");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
            return;
        }
        indexBackScene = 1;
        scroll3.setVisible(false);
        scroll4.setVisible(false);

        scroll.setVisible(true);

        flowPaneForShowListMovies.setVisible(true);
        returnToBackButton.setVisible(true);

        flowPaneForShowListMovies.setHgap(20);
        flowPaneForShowListMovies.setVgap(20);

        clearFlowPane(flowPaneForShowListMovies);
        boxInformationAboutCatalog.setVisible(false);

        int pageCount = 0;
        Response<MediaResultsPage> respons;

        do {
            pageCount++;
            respons = searchMovies.multipleSearch(pageCount, searchindString.getText());

            if (!respons.isSuccessful() || respons.code() != 200 || respons.body().results.size() == 0)
                break;
            MediaResultsPage response = respons.body();

            double ratingFromValue;
            double ratingToValue;
            int yearFromValue;
            int yearToValue;
            int year;
            int genreId;
            String temp = null;
            for (int i = 0; i < response.results.size(); i++) {
                ratingFromValue = 0;
                ratingToValue = 1000;
                yearFromValue = 1800;
                yearToValue = 2020;
                genreId = -1;

                if (response.results.get(i).media_type == MediaType.MOVIE) {

                    Double rating = response.results.get(i).movie.popularity;

                    if (response.results.get(i).movie.release_date != null) {
                        temp = response.results.get(i).movie.release_date.toString();
                        year = Integer.parseInt(temp.substring(temp.length() - 4, temp.length()));
                    } else
                        year = 1800;

                    if (!genreIdsBox.getValue().equals("--")) {
                        genreId = getGenreId(genreIdsBox.getItems().indexOf(genreIdsBox.getValue()));
                    } else {
                        if (response.results.get(i).movie.genre_ids.size() > 0)
                            genreId = response.results.get(i).movie.genre_ids.get(0);
                        else
                            genreId = 0;
                        {
                            response.results.get(i).movie.genre_ids.add(genreId);
                        }
                    }
                    if (ratingFrom.getText().length() + ratingTo.getText().length() != 0) {

                        ratingFromValue = Double.parseDouble(ratingFrom.getText());
                        ratingToValue = Double.parseDouble(ratingTo.getText());
                    }

                    if (yearFrom.getText().length() + yearTo.getText().length() != 0) {
                        yearFromValue = Integer.parseInt(yearFrom.getText());
                        yearToValue = Integer.parseInt(yearTo.getText());
                    }
                    if (rating >= ratingFromValue && rating <= ratingToValue && year >= yearFromValue && year <= yearToValue && response.results.get(i).movie.genre_ids.contains(genreId)) {

                        Image im = new Image("https://image.tmdb.org/t/p/w500" + response.results.get(i).movie.poster_path, 220, 320, false, false);
                        ImageView imageView = new ImageView(im);
                        Hyperlink nameLink = new Hyperlink(response.results.get(i).movie.title);
                        nameLink.setWrapText(true);
                        int finalI = i;

                        nameLink.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {

                                showFilmPane.setVisible(true);
                                scroll.setVisible(false);
                                try {
                                    showInforLabelAboutFilmFromTMDB(response.results.get(finalI).movie);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        VBox v = new VBox(imageView, nameLink);
                        v.setPrefSize(220, 320);

                        flowPaneForShowListMovies.getChildren().add(v);
                    }
                }
            }
        } while (true);

    }


    @FXML
    public void returnBackScene() {

        //    if (showFilmPane.isVisible()) {

            /*flowPaneForShowListMovies.setVisible(true);
            scroll.setVisible(true);
            showFilmPane.setVisible(false);*/

        showFilmPane.setVisible(false);
        if (indexBackScene == 1) {
            scroll.setVisible(true);
            showFilmPane.setVisible(true);
        }

        if (indexBackScene == 2) {
            scroll4.setVisible(true);
            flowPaneForShowWatchedMovies.setVisible(true);
        }

        if (indexBackScene == 3) {
            scroll3.setVisible(true);
            flowPaneForShowRecomendationsMovies.setVisible(true);
        }

        returnToBackButton.setVisible(false);

      /*  } else {
            flowPaneForShowListMovies.setVisible(false);
        }*/
    }

    /*public void sendEmail() throws AddressException {

        String to = "customerlogin6@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "anton.durko.work@gmail.com";
        final String username = "anton.durko.work";//change accordingly
        final String password = "rasko123";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "relay.jangosmtp.net";

        Properties props = new Properties();
        // Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Now set the actual message
            message.setText("Hello, this is sample for to check send " +
                    "email using JavaMailAPI ");
            System.out.println("Sent message successfully....");
            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            // throw new RuntimeException(e);
            System.out.println(e.getMessage());
        }
    }*/

    public void writeDataBaseInFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("CustomerDataBaseFile"));
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(authorizationInSystem.getDataBase().getCustomerAccountsDataBase());
        oos.close();
    }

    public void readDataBaseFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(new File("CustomerDataBaseFile"));
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<CustomerAccount> object = (ArrayList<CustomerAccount>) ois.readObject();
        this.authorizationInSystem.getDataBase().setCustomerAccountsDataBase(object);
        this.authorizationInSystem.getDataBase().getIterator().setList(object);
        ois.close();
    }

    @FXML
    public void addMovieInUserCatalog() {
        if (authorizationInSystem.getStatus()) {
            workWithUserCatalog.setCurrentCustomerAccount(authorizationInSystem.getCurrentUserInSystem());
        }
        workWithUserCatalog.getAddingMoviesInCatalog().
                actionInCatalog(workWithUserCatalog.getCurrentCustomerAccount(),
                        Integer.parseInt(idLabel.getText().substring(0, idLabel.getText().length())));
    }

    public void deleteMovieFromUserCatalog() {
        if (authorizationInSystem.getStatus()) {
            workWithUserCatalog.setCurrentCustomerAccount(authorizationInSystem.getCurrentUserInSystem());
        }
        workWithUserCatalog.getDeletingMoviesFromCatalog().
                actionInCatalog(workWithUserCatalog.getCurrentCustomerAccount(),
                        Integer.parseInt(idLabel.getText().substring(0, idLabel.getText().length())));
    }

    public void showWatchedMoviesFromCatalog() throws IOException {
        scroll4.setVisible(true);
        scroll.setVisible(false);

        flowPaneForShowWatchedMovies.setVisible(true);
        flowPaneForShowWatchedMovies.setHgap(20);
        flowPaneForShowWatchedMovies.setVgap(20);
        boxInformationAboutCatalog.setVisible(false);
        scroll3.setVisible(false);
        clearFlowPane(flowPaneForShowWatchedMovies);
        Response<Movie> filmInfo;
        indexBackScene = 2;
        for (int i = 0; i < authorizationInSystem.getCurrentUserInSystem().getWatchedCustomerMoviesList().size(); i++) {
            filmInfo = searchMovies.getInformationAboutMovie(authorizationInSystem.getCurrentUserInSystem().getWatchedCustomerMoviesList().get(i));
            Image im = new Image("https://image.tmdb.org/t/p/w500" + filmInfo.body().poster_path, 220, 320, false, false);
            ImageView imageView = new ImageView(im);
            Hyperlink nameLink = new Hyperlink(filmInfo.body().title);

            nameLink.setWrapText(true);
            Response<Movie> finalFilmInfo = filmInfo;
            nameLink.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    showFilmPane.setVisible(true);
                    scroll4.setVisible(false);

                    try {
                        showInforLabelAboutFilmFromTMDB(finalFilmInfo.body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            VBox v = new VBox(imageView, nameLink);
            v.setPrefSize(220, 320);
            flowPaneForShowWatchedMovies.getChildren().add(v);
        }

    }
}