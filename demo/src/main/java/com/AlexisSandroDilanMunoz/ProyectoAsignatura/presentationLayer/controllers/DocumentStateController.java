package com.AlexisSandroDilanMunoz.ProyectoAsignatura.presentationLayer.controllers;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.DocumentStateDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.service.DocumentStateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de estados de documentos.
 *
 * CARACTERÍSTICAS:
 * - Creación, actualización y eliminación de estados
 * - Consulta de estados por organización
 * - Ordenamiento y reorganización de estados
 */
@RestController
@RequestMapping("/api/document-states")
public class DocumentStateController {

    private final DocumentStateService documentStateService;

    // Inyección del servicio mediante constructor
    public DocumentStateController(DocumentStateService documentStateService) {
        this.documentStateService = documentStateService;
    }

    /**
     * Crear un nuevo estado de documento
     */
    @PostMapping
    public DocumentStateDto createState(
            @RequestBody DocumentStateDto dto,
            @RequestParam Long organizationId) {

        return documentStateService.createState(dto, organizationId);
    }

    /**
     * Actualizar un estado existente
     */
    @PutMapping("/{stateId}")
    public DocumentStateDto updateState(
            @PathVariable Long stateId,
            @RequestBody DocumentStateDto dto) {

        return documentStateService.updateState(stateId, dto);
    }

    /**
     * Eliminar un estado por su id
     */
    @DeleteMapping("/{stateId}")
    public void deleteState(@PathVariable Long stateId) {
        documentStateService.deleteState(stateId);
    }

    /**
     * Obtener todos los estados de una organización
     */
    @GetMapping("/organization/{organizationId}")
    public List<DocumentStateDto> getStatesByOrganization(
            @PathVariable Long organizationId) {

        return documentStateService.getStatesByOrganization(organizationId);
    }

    /**
     * Obtener los estados ordenados de una organización
     */
    @GetMapping("/organization/{organizationId}/ordered")
    public List<DocumentStateDto> getStatesOrdered(
            @PathVariable Long organizationId) {

        return documentStateService.getStatesOrdered(organizationId);
    }

    /**
     * Reordenar los estados de una organización
     */
    @PutMapping("/organization/{organizationId}/reorder")
    public void reorderStates(
            @PathVariable Long organizationId,
            @RequestBody List<Long> stateIdsInOrder) {

        documentStateService.reorderStates(organizationId, stateIdsInOrder);
    }
}