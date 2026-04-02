package com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.mapper;

import com.AlexisSandroDilanMunoz.ProyectoAsignatura.businessLayer.dtos.TaskDto;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Document;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.Task;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.UserAccount;
import com.AlexisSandroDilanMunoz.ProyectoAsignatura.persistenceLayer.entity.WorkflowStep;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface TaskMapper {

    @Mapping(target = "documentId",            source = "document.documentId")
    @Mapping(target = "documentTitle",         source = "document.title")
    @Mapping(target = "documentReferenceCode", source = "document.referenceCode")
    @Mapping(target = "stepId",                source = "step.stepId")
    @Mapping(target = "stepName",              source = "step.name")
    @Mapping(target = "stepOrder",             source = "step.stepOrder")
    @Mapping(target = "assignedToId",          source = "assignedTo.userId")
    @Mapping(target = "assignedToName",        source = "assignedTo.fullName")
    TaskDto toDTO(Task entity);

    @Mapping(target = "documentId",            source = "document.documentId")
    @Mapping(target = "documentTitle",         source = "document.title")
    @Mapping(target = "documentReferenceCode", source = "document.referenceCode")
    @Mapping(target = "stepId",                source = "step.stepId")
    @Mapping(target = "stepName",              source = "step.name")
    @Mapping(target = "stepOrder",             source = "step.stepOrder")
    @Mapping(target = "assignedToId",          source = "assignedTo.userId")
    @Mapping(target = "assignedToName",        source = "assignedTo.fullName")
    List<TaskDto> toDTOList(List<Task> entities);

    /**
     * DTO → Entidad (uso interno del Service al crear una tarea para el flujo — RF29).
     * taskId lo genera la BD.
     * document, step y assignedTo los asigna el Service.
     * status, createdAt, dueDate los inicializa el Service.
     */
    @Mapping(target = "taskId",      ignore = true)
    @Mapping(target = "document",    source = "documentId",   qualifiedByName = "documentFromId")
    @Mapping(target = "step",        source = "stepId",       qualifiedByName = "stepFromId")
    @Mapping(target = "assignedTo",  source = "assignedToId", qualifiedByName = "userFromId")
    @Mapping(target = "status",      ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    @Mapping(target = "dueDate",     ignore = true)
    @Mapping(target = "completedAt", ignore = true)
    Task toEntity(TaskDto dto);

    /** Referencia a Document usando solo el ID */
    @Named("documentFromId")
    default Document documentFromId(Long documentId) {
        if (documentId == null) return null;
        Document doc = new Document();
        doc.setDocumentId(documentId);
        return doc;
    }

    /** Referencia a WorkflowStep usando solo el ID */
    @Named("stepFromId")
    default WorkflowStep stepFromId(Long stepId) {
        if (stepId == null) return null;
        WorkflowStep step = new WorkflowStep();
        step.setStepId(stepId);
        return step;
    }

    /** Referencia a UserAccount usando solo el ID */
    @Named("userFromId")
    default UserAccount userFromId(Long userId) {
        if (userId == null) return null;
        UserAccount ua = new UserAccount();
        ua.setUserId(userId);
        return ua;
    }
}
