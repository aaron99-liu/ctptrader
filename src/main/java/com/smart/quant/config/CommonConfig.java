package com.smart.quant.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.ErrorHandler;

@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class CommonConfig implements SchedulingConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(CommonConfig.class);

	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		return new TaskSchedulerBuilder().poolSize(20).customizers(new TaskSchedulerCustomizer() {

			@Override
			public void customize(ThreadPoolTaskScheduler taskScheduler) {
				taskScheduler.setErrorHandler(new ErrorHandler() {
					
					@Override
					public void handleError(Throwable t) {
						logger.error("ctp trade service task error!", t);
					}
				});
				taskScheduler.initialize();
			}
			
		}).build();
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(threadPoolTaskScheduler());
	}
}
