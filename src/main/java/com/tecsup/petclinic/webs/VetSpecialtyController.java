package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.services.VetSpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for VetSpecialty operations
 */
@RestController
@Slf4j
public class VetSpecialtyController {

    private final VetSpecialtyService vetSpecialtyService;

    public VetSpecialtyController(VetSpecialtyService vetSpecialtyService) {
        this.vetSpecialtyService = vetSpecialtyService;
    }

    /**
     * Create a new vet-specialty relationship
     *
     * @param vetSpecialtyDTO
     * @return
     */
    @PostMapping(value = "/vet-specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VetSpecialtyDTO> create(@RequestBody VetSpecialtyDTO vetSpecialtyDTO) {
        VetSpecialtyDTO newVetSpecialtyDTO = vetSpecialtyService.create(vetSpecialtyDTO);
        log.info("Created vet-specialty: vetId={}, specialtyId={}", 
                newVetSpecialtyDTO.getVetId(), newVetSpecialtyDTO.getSpecialtyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(newVetSpecialtyDTO);
    }

    /**
     * Find a vet-specialty relationship by vetId and specialtyId
     *
     * @param vetId
     * @param specialtyId
     * @return
     */
    @GetMapping(value = "/vet-specialties/{vetId}/{specialtyId}")
    public ResponseEntity<VetSpecialtyDTO> findById(
            @PathVariable Integer vetId,
            @PathVariable Integer specialtyId) {
        try {
            VetSpecialtyDTO vetSpecialtyDTO = vetSpecialtyService.findById(vetId, specialtyId);
            return ResponseEntity.ok(vetSpecialtyDTO);
        } catch (VetSpecialtyNotFoundException e) {
            log.warn("VetSpecialty not found: vetId={}, specialtyId={}", vetId, specialtyId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update a vet-specialty relationship
     *
     * @param vetSpecialtyDTO
     * @return
     */
    @PutMapping(value = "/vet-specialties")
    public ResponseEntity<VetSpecialtyDTO> update(@RequestBody VetSpecialtyDTO vetSpecialtyDTO) {
        try {
            VetSpecialtyDTO updatedVetSpecialtyDTO = vetSpecialtyService.update(vetSpecialtyDTO);
            return ResponseEntity.ok(updatedVetSpecialtyDTO);
        } catch (Exception e) {
            log.error("Error updating vet-specialty", e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a vet-specialty relationship
     *
     * @param vetId
     * @param specialtyId
     * @return
     */
    @DeleteMapping(value = "/vet-specialties/{vetId}/{specialtyId}")
    public ResponseEntity<String> delete(
            @PathVariable Integer vetId,
            @PathVariable Integer specialtyId) {
        try {
            vetSpecialtyService.delete(vetId, specialtyId);
            log.info("Deleted vet-specialty: vetId={}, specialtyId={}", vetId, specialtyId);
            return ResponseEntity.ok("Deleted vet-specialty: vetId=" + vetId + ", specialtyId=" + specialtyId);
        } catch (VetSpecialtyNotFoundException e) {
            log.warn("VetSpecialty not found for deletion: vetId={}, specialtyId={}", vetId, specialtyId);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find all specialties for a vet
     *
     * @param vetId
     * @return
     */
    @GetMapping(value = "/vet-specialties/vet/{vetId}")
    public ResponseEntity<java.util.List<VetSpecialtyDTO>> findByVetId(@PathVariable Integer vetId) {
        java.util.List<VetSpecialtyDTO> vetSpecialties = vetSpecialtyService.findByVetId(vetId);
        return ResponseEntity.ok(vetSpecialties);
    }

    /**
     * Find all vets with a specific specialty
     *
     * @param specialtyId
     * @return
     */
    @GetMapping(value = "/vet-specialties/specialty/{specialtyId}")
    public ResponseEntity<java.util.List<VetSpecialtyDTO>> findBySpecialtyId(@PathVariable Integer specialtyId) {
        java.util.List<VetSpecialtyDTO> vetSpecialties = vetSpecialtyService.findBySpecialtyId(specialtyId);
        return ResponseEntity.ok(vetSpecialties);
    }
}