package stub;

import domain.Reservation;
import domain.Trip;
import domain.User;
import domain.dtos.TripDTO;
import observer.ObserverServer;
import observer.ObserverService;
import proto.*;
import services.Observer;
import services.Service;
import services.TourismAgencyException;
import utils.ProtobufUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProtobufStub implements Service {

    private final ServiceGrpc.ServiceBlockingStub stub;

    public ProtobufStub(ServiceGrpc.ServiceBlockingStub stub) {
        this.stub = stub;
    }

    @Override
    public User findUser(String username, String password, Observer client) {
        ObserverServer server = new ObserverServer(0, new ObserverService(client));
        try{ // eager start observer server
            server.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        FindUserRequest request = FindUserRequest.newBuilder()
                .setPassword(password)
                .setUsername(username)
                .setObserverPort(server.getPort())
                .build();
        proto.FindUserResponse response = stub.findUser(request);
        if (response.hasUser()){ // successfully logged in
            return ProtobufUtil.getUser(response.getUser());
        }
        server.stop();
        return null;
    }

    @Override
    public List<Trip> getAllTrips() {
        Empty request = Empty.newBuilder().build();
        Iterator<proto.Trip> trips = stub.getAllTrips(request);
        List<Trip> result = new ArrayList<>();
        while (trips.hasNext()){
            proto.Trip trip = trips.next();
            result.add(ProtobufUtil.getTrip(trip));
        }
        return result;
    }

    @Override
    public List<TripDTO> findTrips(String destination, LocalDateTime from, LocalDateTime to) {
        TripDataDTO tripDataDTO = TripDataDTO.newBuilder()
                .setDestination(destination)
                .setFrom(ProtobufUtil.getTimestamp(from))
                .setTo(ProtobufUtil.getTimestamp(to))
                .build();
        Iterator<proto.TripDTO> trips = stub.findTrips(tripDataDTO);
        List<TripDTO> result = new ArrayList<>();
        while (trips.hasNext()){
            proto.TripDTO trip = trips.next();
            result.add(ProtobufUtil.getTripDTO(trip));
        }
        return result;
    }

    @Override
    public Reservation bookTrip(String client, String phoneNumber, int tickets, Trip trip, User user) throws TourismAgencyException {
        BookTripRequest request = BookTripRequest.newBuilder()
                .setClient(client)
                .setPhoneNumber(phoneNumber)
                .setTickets(tickets)
                .setTrip(ProtobufUtil.getTrip(trip))
                .setUser(ProtobufUtil.getUser(user))
                .build();
        BookTripResponse result = stub.bookTrip(request);
        if (!result.hasReservation()){ // there was some error
            throw new TourismAgencyException(result.getErrorMessage());
        }
        return null; // successfully saved
    }

    @Override
    public void logOut(User user, Observer observer) {
        proto.User request = ProtobufUtil.getUser(user);
        stub.logout(request);
    }
}
