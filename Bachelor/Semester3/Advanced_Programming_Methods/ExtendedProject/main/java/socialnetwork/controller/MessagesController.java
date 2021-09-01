package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import socialnetwork.controller.pages.PageActions;
import socialnetwork.domain.Entity;
import socialnetwork.domain.Message;
import socialnetwork.domain.User;
import socialnetwork.utils.Constants;
import socialnetwork.utils.events.message.MessageEvent;
import socialnetwork.utils.observer.Observer;

import java.util.List;
import java.util.stream.Collectors;

public class MessagesController extends AbstractController implements Observer<MessageEvent> {

    private final ObservableList<User> model = FXCollections.observableArrayList();

    @FXML
    TableView<User> tableViewUsers;
    @FXML
    TableColumn<User,String> tableColumnFirstName;
    @FXML
    TableColumn<User,String> tableColumnLastName;
    @FXML
    TextArea textAreaMessage;
    @FXML
    TextField textFieldSearchUser;

    @Override
    public void closeWindow() {
        Stage stage = (Stage)tableViewUsers.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(PageActions pageActions) {
        super.initialize(pageActions);
        pageActions.getService().addMessageObserver(this);
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableViewUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // it allows for selecting multiple rows at once with CTRL+Click
        setTableViewData();
        tableViewUsers.setFixedCellSize(Constants.SMALL_TABLE_VIEW_CELL_SIZE);
        tableViewUsers.setRowFactory(tableView -> new TableRow<>(){
            private final Tooltip tooltip = new Tooltip();

            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if(item==null)
                    setTooltip(null);
                else {
                    tooltip.setText("Double click to chat with "+item.getFirstName()+" "+item.getLastName());
                    setTooltip(tooltip);
                }
            }
        });
    }

    private void setTableViewData(){
        model.setAll(this.getAllUsers(this.pageActions.getService().getAllUsers()));
        tableViewUsers.setItems(model);
    }

    public void handleLabelHome(MouseEvent mouseEvent) {
        openWindow("home");
    }

    public void handleLabelSearch(MouseEvent mouseEvent) {
        openWindow("search");
    }

    public void handleLabelFriends(MouseEvent mouseEvent) {
        openWindow("friends");
    }

    public void handleLabelFriendRequests(MouseEvent mouseEvent) {
        openWindow("friendRequests");
    }

    public void handleTextField(KeyEvent keyEvent) {
        if(textFieldSearchUser.getText().equals(""))
            setTableViewData();
        else {
            model.setAll(this.getAllUsers(this.pageActions.getService().filterUsers(textFieldSearchUser.getText())));
        }
    }

    @Override
    public void update(MessageEvent event) {
        setTableViewData();
    }

    private List<User> getAllUsers(List<User> listOfUsers){
        return listOfUsers
                .stream()
                .filter(user -> (!user.equals(pageActions.getLoggedUser())))
                .collect(Collectors.toList());
    }

    private List<Long> getSelectedUsersIds(){
        List<User> all= tableViewUsers.getSelectionModel().getSelectedItems();
        if(all==null || all.isEmpty()) {
            MyAllert.showMessage(null, Alert.AlertType.WARNING, "Warning", "You did not select any user");
            return null;
        }
        return all.stream()
                .map(Entity::getId)
                .collect(Collectors.toList());
    }

    private List<User> getSelectedUsers(){
        return tableViewUsers.getSelectionModel().getSelectedItems();
    }


    public void handleButtonSendMessage(ActionEvent actionEvent) {
        if(textAreaMessage.getText().equals("")){
            MyAllert.showMessage(null, Alert.AlertType.WARNING,"Warning","Your message can't be empty");
            return;
        }
        List<Long> selected = getSelectedUsersIds();
        if(selected==null || selected.isEmpty())
            return;
        Message message = new Message(pageActions.getLoggedUser().getId(),selected,textAreaMessage.getText());
        pageActions.sendMessage(message);
        textAreaMessage.setText("");
    }

    public void handleTableViewClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount()==2){
            List<User> selected = getSelectedUsers();
            if(selected!=null && !selected.isEmpty()){
                openConversationWindow(selected.get(0));
            }
        }
    }

    private void openConversationWindow(User userSelected){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/conversation.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Your conversation with "+userSelected.getFirstName()+" "+userSelected.getLastName());

            ConversationController controller = loader.getController();
            controller.initialize(pageActions,userSelected);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleReportMessages(ActionEvent actionEvent) {
        //open the Report Messages Window
        try{
            if(this.getSelectedUsers().isEmpty()){
                MyAllert.showMessage(null, Alert.AlertType.WARNING,"Warning","You did not select a user");
                return;
            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/reportMessages.fxml"));
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Conversation Report");

            stage.show();
            ReportMessagesController controller = loader.getController();
            controller.initialize(pageActions,getSelectedUsers().get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleLabelEvents(MouseEvent mouseEvent) {
        openWindow("events");
    }
}
