package com.mmajdis.ufoo.endpoint;

import com.mmajdis.ufoo.UFooProcessor;
import com.mmajdis.ufoo.analyzer.FootprintSimilarityService;
import com.mmajdis.ufoo.analyzer.Serializer;
import com.mmajdis.ufoo.endpoint.collector.http.RequestHandler;
import com.mmajdis.ufoo.endpoint.collector.http.geoip.LocationLookupService;
import com.mmajdis.ufoo.endpoint.collector.tcp.PacketStream;
import com.mmajdis.ufoo.stock.UFooStock;
import com.mmajdis.ufoo.stock.UFooStockImpl;
import com.mmajdis.ufoo.util.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

/**
 * @author Matej Majdis
 */
@Aspect
@Named
public class Injector {

    private RequestHandler requestHandler;
    private PacketStream packetStream;

    ExecutorService executor;

    public Injector() {

        startNetworkAnalysis();

        Serializer serializer = new Serializer();
        UFooStock uFooStock = new UFooStockImpl();
        FootprintSimilarityService footprintSimilarityService = new FootprintSimilarityService(uFooStock);
        UFooProcessor uFooProcessorInstance = new UFooProcessor(packetStream, serializer, footprintSimilarityService, uFooStock);
        LocationLookupService locationLookupServiceInstance = new LocationLookupService();

        this.requestHandler = new RequestHandler(uFooProcessorInstance, locationLookupServiceInstance);
        executor = Executors.newFixedThreadPool(Constants.MAX_THREADS);
    }

    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *) && " +
                    "execution(* *(..))"
    )
    public void controller() {
    }

    @Before("controller()")
    public void advice(JoinPoint thisJoinPoint) {

        HttpServletRequest request = getRequestObject(thisJoinPoint);
        if (request != null) {
            Callable<Boolean> task = () -> {
                return requestHandler.handle(request);
            };
            executor.submit(task);
        }
    }

    private Thread startNetworkAnalysis() {

        if (!Constants.TCP_SUPPORTED || packetStream != null) {
            return null;
        }

        packetStream = new PacketStream();
        Thread thread = new Thread(packetStream);
        thread.start();
        return thread;
    }

    private HttpServletRequest getRequestObject(final JoinPoint joinPoint) {
        for (final Object paramValue : joinPoint.getArgs()) {
            if (paramValue instanceof HttpServletRequest) {
                return (HttpServletRequest) paramValue;
            }
        }
        return null;
    }
}
