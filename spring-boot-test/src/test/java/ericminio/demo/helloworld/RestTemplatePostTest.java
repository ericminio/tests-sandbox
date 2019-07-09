package ericminio.demo.helloworld;

import ericminio.demo.helloworld.domain.BuildGreeting;
import ericminio.demo.helloworld.domain.Greeting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static ericminio.support.PostRequest.post;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= RANDOM_PORT)
public class RestTemplatePostTest {

    @LocalServerPort
    int port;

    private String greetings;

    @Before
    public void buildEndpoint() {
        greetings = "http://localhost:"+ port +"/greetings";
    }

    @Test
    public void works() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Greeting> request = new HttpEntity<>(new BuildGreeting().from("Hal").please());
        ResponseEntity<String> response = restTemplate.exchange(greetings, POST, request, String.class);

        assertThat( response.getBody(), equalTo( "My name is not Hal" ) );
    }
}
