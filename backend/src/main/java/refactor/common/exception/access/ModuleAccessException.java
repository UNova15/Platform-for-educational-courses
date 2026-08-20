package refactor.common.exception.access;

public class ModuleAccessException extends AccessException {

    public ModuleAccessException(long moduleId, long requesterId) {
        super(moduleId, requesterId);
    }
}
