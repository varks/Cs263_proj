package test1.test1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/jerseyws")
public class TestJersey {
	@GET
    @Path("/test")
    public String testMethod() {
        return "this is a test of times --";
    }
}
