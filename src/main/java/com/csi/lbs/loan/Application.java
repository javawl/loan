package com.csi.lbs.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.csi.lbs.loan.Application;

//import com.csi.sbs.common.business.log.InitLog;

@SpringBootApplication
@EnableEurekaClient
public class Application {
	
	@Bean
    @LoadBalanced
    public RestTemplate rest() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext context = SpringApplication.run(Application.class, args);
		
		//初始化日志相关配置
		//InitLog.loadLogConfig(context,"loan");
	}

}
