package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @InjectMocks
    private ClinicServiceImpl underTest;

    @Mock
    private PetRepository petRepository;

    @Mock
    private VetRepository vetRepository;

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private VisitRepository visitRepository;

    @Test
    void findPetTypes() {
        List<PetType> petTypes = new ArrayList<>();

        given(this.petRepository.findPetTypes()).willReturn(petTypes);

        Collection<PetType> actualPetTypes = this.underTest.findPetTypes();

        then(this.petRepository).should().findPetTypes();
        assertThat(actualPetTypes).isNotNull();
    }
}