package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerSpringMVCTest {

    @InjectMocks
    private VetController underTest;

    @Mock
    private ClinicService service;

    @Mock
    private Map<String, Object> model;

    MockMvc mockMvc;

    List<Vet> vets = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        this.vets.add(new Vet());

        given(this.service.findVets()).willReturn(this.vets);

        this.mockMvc = MockMvcBuilders.standaloneSetup(this.underTest).build();
    }

    @Test
    void testControllerShowList() throws Exception {
        this.mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @Test
    void showVetList() {
        String actualView = this.underTest.showVetList(this.model);

        then(this.service).should().findVets();
        then(this.model).should().put(anyString(), any());
        assertThat(actualView).isEqualTo("vets/vetList");
    }

    @Test
    void showResourcesVetList() {
        Vets actualVets = this.underTest.showResourcesVetList();

        then(this.service).should().findVets();
        assertThat(actualVets.getVetList()).hasSize(1);
    }
}