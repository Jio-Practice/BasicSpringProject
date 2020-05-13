package Config;

import com.CustomerInfo.Customer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.basicCrudOps")
public class MainConfig {

    @Bean
    public Customer customer(){ return new Customer(); }
    //TODo; Add final configs all here
}
