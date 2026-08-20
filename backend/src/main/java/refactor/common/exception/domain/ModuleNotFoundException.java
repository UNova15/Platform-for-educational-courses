package refactor.common.exception.domain;

public class ModuleNotFoundException extends NotFoundException {
    private final long moduleId;
    private final long requesterId;

    public ModuleNotFoundException(long moduleId, long requesterId) {
        super("Module with ID: %d for user with ID %d not found".formatted(moduleId, requesterId));
        this.moduleId = moduleId;
        this.requesterId = requesterId;
    }
}
