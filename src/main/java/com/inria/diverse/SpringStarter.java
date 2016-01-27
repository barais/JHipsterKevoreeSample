package com.inria.diverse;



import java.net.InetAddress;

import org.kevoree.annotation.*;
import org.kevoree.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;


@ComponentType
public class SpringStarter {

    @Param(defaultValue = "Default Content")
    String message;

    @KevoreeInject
    org.kevoree.api.Context context;

    @Output
    org.kevoree.api.Port out;

    @Input
    public void in(Object i) {
        String msg = message+" from "+context.getInstanceName()+"@"+context.getNodeName();
        System.out.println(msg);
        out.send(msg,new Callback() {

			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub				
			}

			public void onSuccess(CallbackResult arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }

    @Start
    public void start() {
    	String[] args=new String[2];
    	args[0] = "java";
    	args[1] = "Application";
    	
        SpringApplication app = new SpringApplication(Application.class);
        
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        Application.addDefaultProfile(app, source);
        Environment env = app.run(args).getEnvironment();
        System.out.println("Access URLs:\n----------------------------------------------------------\n\t" +
                "Local: \t\thttp://127.0.0.1:{}\n\t" +
                "External: \thttp://{}:{}\n----------------------------------------------------------");

    	
    }

    @Stop
    public void stop() {}

    @Update
    public void update() {System.out.println("Param updated!");}

}

