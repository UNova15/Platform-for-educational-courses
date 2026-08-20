package refactor.course.application.service.query;

import lombok.RequiredArgsConstructor;
import refactor.course.application.port.in.module.query.ModuleQueryResult;
import org.springframework.stereotype.Service;
import refactor.course.application.port.in.module.query.ModuleQueryUseCase;
import refactor.course.application.port.out.persistance.module.ModuleQueryPort;

@Service
@RequiredArgsConstructor
public class ModuleQueryService implements ModuleQueryUseCase {
    private final ModuleQueryPort queryPort;

    @Override
    public ModuleQueryResult findModule(long moduleId) {
        return queryPort.findModuleById(moduleId);
    }
}
