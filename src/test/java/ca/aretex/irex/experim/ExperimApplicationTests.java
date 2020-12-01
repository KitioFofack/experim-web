package ca.aretex.irex.experim;

import ca.aretex.irex.experim.bean.request.Prospectable;
import ca.aretex.irex.experim.bean.request.data.Partner;
import ca.aretex.irex.experim.controler.SubmitController;
import ca.aretex.irex.experim.service.ERPNextRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
//@SpringBootTest
@WebMvcTest(ERPNextRepository.class)
class ExperimApplicationTests {

	@Value("${erpnextServerURL}")
	private String erpnextServerURL;

	@Value("${erpNextAccount}")
	private String erpNextAccount;

	@Value("${erpNextAccountPassword}")
	private String erpNextAccountPassword;

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	ERPNextRepository backendService;
	@MockBean
	SubmitController controller;
	@MockBean
	Partner partner;
	@MockBean
	Prospectable prospectable;

	@Test
	void contextLoads() {
	}

	@Test
	public void submitPartnerTest() throws Exception {
		when(controller.submitPartner(partner)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
		assert(backendService.save(partner).is2xxSuccessful());
	}
	@Test
	public void backendServiceTest() throws Exception {
		when(backendService.save(prospectable)).thenReturn(HttpStatus.CREATED);
		this.mockMvc.perform(post(erpnextServerURL+"/api/resource/Lead").accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON)
		.content(asJsonString(partner))
		).andDo(print()).andExpect(status().is2xxSuccessful());
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			log.info("content as json : {}", jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}




}
