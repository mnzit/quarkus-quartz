package com.mnzit.manager;

import com.mnzit.annotation.ScheduledEvent;
import com.mnzit.annotation.ScheduledEventController;
import io.quarkus.arc.Arc;
import io.quarkus.arc.InstanceHandle;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.quartz.JobDataMap;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Manjit Shakya
 */
@ApplicationScoped
public class ScheduledEventManager {

    @Inject
    Logger log;

    private static Map<String, BeanMethod> scheduledMethods = new HashMap<>();

    public void init(List<Class<?>> classes) {
        for (Class<?> classz : classes) {
            ScheduledEventController scheduledEventController = classz.getAnnotation(ScheduledEventController.class);
            InstanceHandle<?> instance = Arc.container().instance(classz);
            log.info(classes + " bean status: " + instance.isAvailable());
            Object bean = instance.get();
            if (!classz.isInterface() && !classz.isEnum() && scheduledEventController != null) {
                Method[] methods = classz.getDeclaredMethods();

                for (Method method : methods) {
                    System.out.println("Requesting from method: " + method);
                    ScheduledEvent scheduledEvent = method.getAnnotation(ScheduledEvent.class);

                    if (scheduledEvent != null) {
                        Class<?>[] params = method.getParameterTypes();

                        if (params.length > 1) {
                            throw new RuntimeException("Only one parameter can exist");
                        }

                        if (!params[0].equals(JobDataMap.class)) {
                            throw new RuntimeException("Parameter type not supported. Use JobDataMap type");
                        }

                        scheduledMethods.put(scheduledEvent.value(), new BeanMethod(bean, method));
                    }
                }
            }
        }
        scheduledMethods = Collections.unmodifiableMap(scheduledMethods);
    }

    public void invoke(JobDataMap jobDataMap) throws Exception {
        String name = (String) jobDataMap.get("name");
        BeanMethod beanMethod = scheduledMethods.get(name);
        beanMethod.method().invoke(beanMethod.instance(), jobDataMap);
    }

    record BeanMethod(Object instance, Method method) {
    }
}
