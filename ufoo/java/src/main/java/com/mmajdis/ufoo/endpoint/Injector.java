package com.mmajdis.ufoo.endpoint;

import com.mmajdis.ufoo.UFooProcessor;
import com.mmajdis.ufoo.analyzer.FootprintMatcher;
import com.mmajdis.ufoo.analyzer.Serializer;
import com.mmajdis.ufoo.endpoint.collector.http.RequestHandler;
import com.mmajdis.ufoo.endpoint.collector.http.geoip.LocationLookupService;
import com.mmajdis.ufoo.endpoint.collector.tcp.PacketStream;
import com.mmajdis.ufoo.endpoint.collector.tcp.TCPFootprint;
import com.mmajdis.ufoo.stock.MarkerStockManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author Matej Majdis
 */
@Aspect
@Named
public class Injector {

    private static final boolean tcpSupported = true;

    private RequestHandler requestHandler;
    private PacketStream packetStream;

    public Injector() {

        startNetworkAnalysis();

        Serializer serializer = new Serializer();
        MarkerStockManager markerStockManager = new MarkerStockManager();
        FootprintMatcher footprintMatcher = new FootprintMatcher(markerStockManager);
        UFooProcessor uFooProcessorInstance = new UFooProcessor(packetStream,  serializer, footprintMatcher, markerStockManager);
        LocationLookupService locationLookupServiceInstance = new LocationLookupService();

        this.requestHandler = new RequestHandler(uFooProcessorInstance, locationLookupServiceInstance);
    }

    @Pointcut(
            "within(@org.springframework.web.bind.annotation.RestController *) && " +
                    "execution(* *(..))"
    )
    public void controller() {
    }

    @Before("controller()")
    public void advice(JoinPoint thisJoinPoint) {


        System.out.println("--------" + thisJoinPoint);

        startNetworkAnalysis();

        //TODO - do the magic
        HttpServletRequest request = getRequestObject(thisJoinPoint);
        if (request != null) {
            requestHandler.handle(request);
        }

        Map<String, Set<TCPFootprint>> map = packetStream.getActualTcpStream();
    }

    private Thread startNetworkAnalysis() {

        if (!tcpSupported || packetStream != null) {
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
