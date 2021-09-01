package socialnetwork.controller.pages;

import socialnetwork.domain.*;
import socialnetwork.domain.dtos.FriendRequestDTO;
import socialnetwork.domain.dtos.FriendshipDTO;
import socialnetwork.domain.dtos.MessageDTO;
import socialnetwork.service.MasterService;
import socialnetwork.utils.runners.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PageActions {

    private final PageObject pageObject;

    public PageActions(PageObject pageObject) {
        this.pageObject=pageObject;
    }

    public MasterService getService() {
        return pageObject.getService();
    }

    public User getLoggedUser() {
        return pageObject.getLoggedUser();
    }

    public List<FriendshipDTO> getFriendships(int pageNumber){
        return getService().getFriendshipsPage(pageNumber,getLoggedUser());
    }

    public List<FriendshipDTO> getFriendships(LocalDate dateFrom,LocalDate dateTo){
        return getService().filterFriendshipsIDDate(getLoggedUser().getId(),dateFrom,dateTo);
    }

    public void removeFriendships(FriendshipDTO friendship){
        Tuple<Long,Long> ids = friendship.getIds();
        RemoveFriendRunner runner = new RemoveFriendRunner(ids,getService());
        runner.execute();
    }

    public List<FriendRequestDTO> getSentFriendRequests(int pageNumber){
        return getService().getSentFriendRequestsPage(pageNumber,getLoggedUser());
    }

    public List<FriendRequestDTO> getSentFriendRequests(){
        return getService().getFriendRequestsDTO(getService().getAllFriendRequests())
                .stream()
                .filter(friendRequestDTO -> friendRequestDTO.getUserFromId().equals(getLoggedUser().getId()))
                .collect(Collectors.toList());
    }

    public List<FriendRequestDTO> getReceivedFriendRequests(int pageNumber){
        return getService().getReceivedFriendRequestsPage(pageNumber,getLoggedUser());
    }

    public List<FriendRequestDTO> getReceivedFriendRequests(){
        return this.getService().getFriendRequestsDTO(this.getService().getAllFriendRequests()).stream()
                .filter(friendRequestDTO -> friendRequestDTO.getUserToId().equals(getLoggedUser().getId()))
                .collect(Collectors.toList());
    }

    public void acceptFriendRequest(FriendRequestDTO request){
        AcceptFriendRequestRunner runner = new AcceptFriendRequestRunner(request.getId(),this.getService());
        runner.execute();
    }

    public void rejectFriendRequest(FriendRequestDTO request){
        RejectFriendRequestRunner runner = new RejectFriendRequestRunner(request.getId(),this.getService());
        runner.execute();
    }

    public void removeFriendRequest(FriendRequestDTO request){
        RemovePendingFriendRequestRunner runner = new RemovePendingFriendRequestRunner(this.getService(),request.getId());
        runner.execute();
    }

    public void sendMessage(Message message){
        var runner = new SendMessageRunner(message,this.getService());
        runner.execute();
    }

    public void replyMessage(MessageDTO messageToReply, String text){
        var runner = new ReplyMessageRunner(messageToReply.getMessageId(),getLoggedUser().getId(),text,getService());
        runner.execute();
    }

    public List<MessageDTO> getMessages(int leftLimit, int rightLimit, User userToMessage){
        return this.getService().getMessagesPage(leftLimit,rightLimit,getLoggedUser(),userToMessage);
    }

    public List<MessageDTO> getMessages(User userToMessage){
        return this.getService().getConversation(getLoggedUser().getId(),userToMessage.getId());
    }

    public List<MessageDTO> getMessages(User userToMessage, LocalDate dateFrom, LocalDate dateTo){
        return this.getService().getConversation(getLoggedUser(), userToMessage, dateFrom, dateTo);
    }

    public List<MessageDTO> getMessages(LocalDate dateFrom, LocalDate dateTo){
        return getService().getOnesMessages(getLoggedUser(),dateFrom,dateTo);
    }

    public List<Event> getEvents(){
        return getService().getAllEvents();
    }

    public List<Event> getEvents(int pageNumber){
        return getService().getEventsPage(pageNumber);
    }

    public boolean isParticipant(Long idEvent){
        return getService().isParticipant(idEvent,getLoggedUser().getId());
    }

    public boolean isSubscribedToNotification(Long idEvent){
        return getService().isSubscribedToNotification(idEvent,getLoggedUser().getId());
    }

    public Optional<Event> addEvent(Event event){
        return getService().addEvent(event);
    }

    public Optional<Event> addParticipant(Long idEvent){
        return getService().addParticipant(idEvent,getLoggedUser().getId());
    }

    public Optional<Event> removeParticipant(Long idEvent){
        return getService().removeParticipant(idEvent,getLoggedUser().getId());
    }

    public Optional<Event> addSubscriber(Event event){
        return getService().addSubscriber(event,getLoggedUser().getId());
    }

    public Optional<Event> removeSubscriber(Event event){
        return getService().removeSubscriber(event,getLoggedUser().getId());
    }

    public List<Notification> getNotifications(){
        return getService().getNotifications(getLoggedUser());
    }

    public List<Notification> getNotifications(int pageNumber){
        return getService().getNotificationsPage(pageNumber,getLoggedUser());
    }

}
