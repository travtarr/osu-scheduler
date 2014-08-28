package edu.oregonState.scheduler;

import org.skife.jdbi.v2.DBI;

import edu.oregonState.scheduler.config.ConfigException;
import edu.oregonState.scheduler.data.UserDAO;
import edu.oregonState.scheduler.data.UserJDBIDAO;
import edu.oregonState.scheduler.health.TemplateHealthCheck;
import edu.oregonState.scheduler.resources.GoogleAuthResource;
import edu.oregonState.scheduler.resources.HelloWorldResource;
import edu.oregonState.scheduler.resources.ScheduleResource;
import edu.oregonState.scheduler.resources.UserResource;
import edu.oregonState.scheduler.user.User;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class SchedulerApplication extends Application<SchedulerConfiguration> {
    public static void main(String[] args) throws Exception {
        new SchedulerApplication().run(args);
    }

    @Override
    public String getName() {
        return "osu-scheduler";
    }

    @Override
    public void initialize(Bootstrap<SchedulerConfiguration> bootstrap) {
    	bootstrap.addBundle(hibernate);
    }
    

    @Override
    public void run(SchedulerConfiguration configuration,
                    Environment environment) throws ConfigException, ClassNotFoundException {
        final HelloWorldResource helloResource = new HelloWorldResource(
            configuration.getTemplate(),
            configuration.getDefaultName()
        );
        
        //jbdi
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        final UserJDBIDAO userJDBIdao = jdbi.onDemand(UserJDBIDAO.class);
        
        
        final UserResource userResource = new UserResource(new UserDAO(hibernate.getSessionFactory()),userJDBIdao,MainFactory.getGoogleTokenProvider());
        final ScheduleResource scheduleResource = new ScheduleResource(userResource);        
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        final GoogleAuthResource googleAuthResource = new GoogleAuthResource(MainFactory.getGoogleCalendarAuthURLProvider());        
        
        //health
        environment.healthChecks().register("template", healthCheck);
        //resources
        environment.jersey().register(helloResource);
        environment.jersey().register(scheduleResource);
        environment.jersey().register(googleAuthResource);
        environment.jersey().register(userResource);                
    }
    
    private HibernateBundle<SchedulerConfiguration> hibernate = new HibernateBundle<SchedulerConfiguration>(User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(SchedulerConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}