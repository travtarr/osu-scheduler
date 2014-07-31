package edu.oregonState.scheduler;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.health.TemplateHealthCheck;
import edu.oregonState.scheduler.resources.GoogleAuthResource;
import edu.oregonState.scheduler.resources.HelloWorldResource;
import edu.oregonState.scheduler.resources.ScheduleResource;
import edu.oregonState.scheduler.user.User;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
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
    	bootstrap.addBundle(hibernate);
    }
    
    private final HibernateBundle<SchedulerConfiguration> hibernate = new HibernateBundle<SchedulerConfiguration>(User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(SchedulerConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

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
        
        //db
        final UserDAO dao = new UserDAO(hibernate.getSessionFactory());
        
        //health
        environment.healthChecks().register("template", healthCheck);
        //resources
        environment.jersey().register(helloResource);
        environment.jersey().register(scheduleResource);
        environment.jersey().register(googleAuthResource);
        
    }

}