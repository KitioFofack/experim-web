package ca.aretex.irex.experim.bean.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CandidateUT {
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void testToString() throws JsonProcessingException {
        Candidate candidate = new Candidate();
        candidate.setEmail("test@irex.aretex.ca");
        candidate.setName("test");
        candidate.setPhone("0101010101");

        log.info("candidate={}", candidate);
        String candidateJson = objectMapper.writeValueAsString(candidate);
        log.info("candidateJson={}", candidateJson);

        assertThat(candidateJson).isEqualToIgnoringCase("{\"phone\":\"0101010101\",\"lead_name\":\"test\",\"email_id\":\"test@irex.aretex.ca\"}");
    }
}
