package stories.US001;

import static constants.Endpoints.RESTRICOES;
import static helper.ServiceHelper.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;

import datafactory.DynamicFactory;
import helper.BaseTest;
import helper.VariaveisUteis;
import io.restassured.response.Response;

public class GETRestricoesTests extends BaseTest {
	
	@Test
	public void deveDarGetTest() {
		Response response = rest.get(RESTRICOES,"777");
		assertThat(response.statusCode(), is(204));
		assertThat(response,is(not(nullValue())));
		assertThat(response.body(),is(not(nullValue())));
	}
	

}
