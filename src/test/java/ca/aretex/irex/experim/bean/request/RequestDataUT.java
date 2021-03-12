package ca.aretex.irex.experim.bean.request;

import ca.aretex.irex.experim.bean.request.data.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class RequestDataUT {
    ObjectMapper objectMapper = new ObjectMapper();

    Contact contact = Contact.builder().email("test@irex.aretex.ca").phone("0101010101").build();
    Client client = Client.builder().name("test").contact(contact).build();
    Candidate candidate = Candidate.builder().client(client).build();
    Mentor mentor = Mentor.builder().client(client).disponibilite("now").build();
    Partner partner = Partner.builder().employeur(Employeur.builder().client(client).nomEntreprise("irex").build()).build();

    @Test
    public void candidateTest() throws JsonProcessingException {
        log.info("candidate={}", candidate);
        String candidateJson = objectMapper.writeValueAsString(candidate);
        log.info("candidateJson={}", candidateJson);

        assertThat(candidateJson).isEqualToIgnoringCase("{\"phone\":\"0101010101\",\"email_id\":\"test@irex.aretex.ca\",\"lead_name\":\"test\"}");
        assertThat(objectMapper.readValue(candidateJson, Candidate.class)).isEqualTo(candidate);

    }
    @Test
    public void mentorTest() throws JsonProcessingException {
        log.info("mentor={}", mentor);
        String mentorJson = objectMapper.writeValueAsString(mentor);
        log.info("mentorJson={}", mentorJson);

        assertThat(objectMapper.readValue(mentorJson, Mentor.class)).isEqualTo(mentor);
    }
    @Test
    public void partnerTest() throws JsonProcessingException {
        log.info("partner={}", partner);
        String partnerJson = objectMapper.writeValueAsString(partner);
        log.info("partnerJson={}", partnerJson);

        assertThat(objectMapper.readValue(partnerJson, Partner.class)).isEqualTo(partner);
    }
}
