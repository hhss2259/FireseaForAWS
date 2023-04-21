package firesea.testserver.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Slf4j
@Configuration
//@ConfigurationProperties(prefix = "yaml")
@PropertySource(value = "classpath:email.yml", factory = YamlPropertySourceFactory.class)
//@PropertySource(value = "classpath:email.yml", factory = YamlPropertySourceFactory.class)
public class EmailConfig {

    @Value("${spring.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${spring.mail.debug}")
    private boolean debug;

    @Value("${spring.mail.host")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password")
    private String password;

    @Value("${spring.mail.default.encoding}")
    private String encoding;
}
