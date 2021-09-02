package observer;

import io.grpc.stub.StreamObserver;
import proto.Empty;
import proto.ObserverGrpc;
import proto.Trip;
import services.Observer;
import utils.ProtobufUtil;

public class ObserverService extends ObserverGrpc.ObserverImplBase {

    private final Observer observer;

    public ObserverService(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void tripChanged(Trip request, StreamObserver<Empty> responseObserver) {
        domain.Trip trip = ProtobufUtil.getTrip(request);
        try {
            observer.tripChanged(trip);
        }catch (Exception e){
            e.printStackTrace();
        }
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
