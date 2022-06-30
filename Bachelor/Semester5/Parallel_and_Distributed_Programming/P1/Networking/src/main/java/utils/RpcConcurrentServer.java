package utils;

import rpcprotocol.ClientRpcWorker;
import services.Service;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RpcConcurrentServer extends AbstractConcurrentServer {
    private final Service service;
    private final ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;
    private static final int NR_SECONDS_TO_CHECK = 5;

    public RpcConcurrentServer(int port, Service service, int nrThreads) {
        super(port);
        this.service = service;
        this.executorService = Executors.newFixedThreadPool(nrThreads);
    }

    @Override
    protected void createWorker(Socket client) {
        executorService.execute(new ClientRpcWorker(service, client));
    }

    @Override
    protected void scheduleChecks() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(service::checkShows, 0, NR_SECONDS_TO_CHECK, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        super.stop();
        service.notifyObserversServerCloses();
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }
}