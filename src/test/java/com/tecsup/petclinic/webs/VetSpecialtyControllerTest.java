package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for VetSpecialtyController
 */
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetSpecialtyControllerTest {

    private static final ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindVetSpecialtyOK() throws Exception {
        mockMvc.perform(get("/vet-specialties/2/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vetId", is(2)))
                .andExpect(jsonPath("$.specialtyId", is(1)))
                .andExpect(jsonPath("$.yearsExperience", is(8)))
                .andExpect(jsonPath("$.isPrimary", is(true)));
    }

    @Test
    public void testCreateVetSpecialtyOK() throws Exception {
        VetSpecialtyDTO newVetSpecialty = VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(2)
                .certificationDate(LocalDate.of(2023, 5, 15))
                .yearsExperience(3)
                .isPrimary(false)
                .notes("New certification for vet 1")
                .build();

        String jsonRequest = om.writeValueAsString(newVetSpecialty);

        mockMvc.perform(post("/vet-specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vetId", is(1)))
                .andExpect(jsonPath("$.specialtyId", is(2)))
                .andExpect(jsonPath("$.yearsExperience", is(3)))
                .andExpect(jsonPath("$.isPrimary", is(false)))
                .andExpect(jsonPath("$.notes", is("New certification for vet 1")));
    }
}
