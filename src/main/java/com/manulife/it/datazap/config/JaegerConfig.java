package  com.manulife.it.datazap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

/**
 * Jaeger configuration.
 */
//@Configuration
@Profile("!test")
public class JaegerConfig {
	

  /**
   * Creates Spring Bean for Jaeger Tracer.
   *
   * @return bean of type {@link Tracer}
   */
   //@Bean
  @Profile("!test")
  public Tracer jaegerTracer() {
	  Tracer tracer = io.jaegertracing.Configuration.fromEnv()
        .getTracer();

    GlobalTracer.registerIfAbsent(tracer);
    return tracer;
  }
  

}
