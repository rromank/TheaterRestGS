package com.epam.theater;

import com.epam.theater.service.EventService;
import com.epam.theater.service.MovieService;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import org.openspaces.remoting.EventDrivenRemotingProxyConfigurer;
import org.openspaces.remoting.ExecutorRemotingProxyConfigurer;
import org.openspaces.remoting.SpaceRemotingServiceExporter;

public class Main {

    public static void main(String[] args) {
        UrlSpaceConfigurer spaceConfigurer = new UrlSpaceConfigurer("jini://*/*/space?groups=gigaspaces-10.1.0-XAPPremium-m11");
        GigaSpace gigaSpace = new GigaSpaceConfigurer(spaceConfigurer).gigaSpace();
        int count = gigaSpace.count(new Object());
        System.out.println(count);
        System.out.println("==============================");

//        ExecutorRemotingProxyConfigurer<MovieService> proxyConfigurer = new ExecutorRemotingProxyConfigurer(gigaSpace, MovieService.class);
//        MovieService movieService = proxyConfigurer.proxy();
//        System.out.println(movieService.getAll());

        EventService eventService = new EventDrivenRemotingProxyConfigurer<EventService>(gigaSpace, EventService.class)
                .timeout(15000).proxy();

        eventService.logToConsole();

        MovieService movieService = new EventDrivenRemotingProxyConfigurer<>(gigaSpace, MovieService.class).timeout(15000).proxy();
        System.out.println(movieService.getAll());
    }

}