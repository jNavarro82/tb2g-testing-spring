package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * MockBean configuration
 */
@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired
    private OwnerController ownerController;

    @Autowired
    private ClinicService clinicService;

    @Mock
    private Map<String, Object> model;

    @Captor
    private ArgumentCaptor<String> captor;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.ownerController).build();
    }

    @AfterEach
    void tearDown() {
        reset(this.clinicService);
    }

    @Test
    void testEditOwnerPostValid() throws Exception {
        this.mockMvc.perform(post("/owners/{ownerId}/edit", "1")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("address", "123 Duval St")
                        .param("city", "Madrid")
                        .param("telephone", "789456321"))
                .andExpect(view().name("redirect:/owners/{ownerId}"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testEditOwnerPostInValid() throws Exception {
        this.mockMvc.perform(post("/owners/{ownerId}/edit", "1")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("city", "Madrid"))
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(status().isOk());
    }

    @Test
    void testNewOwnerPostValid() throws Exception {
        this.mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("address", "123 Duval St")
                        .param("city", "Madrid")
                        .param("telephone", "789456321"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testNewOwnerPostInValid() throws Exception {
        this.mockMvc.perform(post("/owners/new")
                        .param("firstName", "Jimmy")
                        .param("lastName", "Buffet")
                        .param("city", "Madrid"))
                .andExpect(model().attributeHasErrors("owner"))
                .andExpect(model().attributeHasFieldErrors("owner", "address"))
                .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }

    @Test
    void testFindByNameOneOwner() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);

        given(this.clinicService.findOwnerByLastName(anyString())).willReturn(owners);

        this.mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));

        then(this.clinicService).should().findOwnerByLastName(anyString());
    }

    @Test
    void testFindByNameOwners() throws Exception {
        Owner owner = new Owner();
        owner.setId(1);
        Owner anotherOwner = new Owner();
        anotherOwner.setId(2);
        List<Owner> owners = new ArrayList<>();
        owners.add(owner);
        owners.add(anotherOwner);

        given(this.clinicService.findOwnerByLastName(anyString())).willReturn(owners);

        this.mockMvc.perform(get("/owners"))
                .andExpect(model().attributeExists("selections"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"));

        then(this.clinicService).should().findOwnerByLastName(this.captor.capture());
        assertThat(this.captor.getValue()).isEqualTo("");
    }

    @Test
    void testFindByNameNotFound() throws Exception {
        this.mockMvc.perform(get("/owners")
                        .param("lastName", "Dont find ME!"))
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void initCreationFormTest() throws Exception {
        this.mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owners/createOrUpdateOwnerForm"));
    }
}