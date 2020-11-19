package ca.aretex.irex.experim;

import ca.aretex.irex.experim.bean.request.Prospectable;
import ca.aretex.irex.experim.service.ERPNextRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@SpringBootTest
@WebMvcTest(ERPNextRepository.class)
class ExperimApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	ERPNextRepository backendService;
	@MockBean
	Prospectable prospectable;

	@Test
	void contextLoads() {
	}

	@Test
	public void backendServiceTest() throws Exception {
		when(backendService.save(prospectable)).thenReturn(HttpStatus.CREATED);
		//this.mockMvc.perform(get());
	}





}
