package edu.oregonState.scheduler;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.health.TemplateHealthCheck;
import edu.oregonState.scheduler.resources.GoogleAuthResource;
import edu.oregonState.scheduler.resources.HelloWorldResource;
import edu.oregonState.scheduler.resources.ScheduleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SchedulerApplication extends Application<SchedulerConfiguration> {
    public static void main(String[] args) throws Exception {
        new SchedulerApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<SchedulerConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(SchedulerConfiguration configuration,
                    Environment environment) throws ConfigException {
        final HelloWorldResource helloResource = new HelloWorldResource(
            configuration.getTemplate(),
            configuration.getDefaultName()
        );
        final ScheduleResource scheduleResource = new ScheduleResource();        
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        final GoogleAuthResource googleAuthResource = new GoogleAuthResource(MainFactory.getGoogleCalendarAuthURLProvider());
        environment.healthChecks().register("template", healthCheck);        
        environment.jersey().register(helloResource);
        environment.jersey().register(scheduleResource);
        environment.jersey().register(googleAuthResource);
    }

}