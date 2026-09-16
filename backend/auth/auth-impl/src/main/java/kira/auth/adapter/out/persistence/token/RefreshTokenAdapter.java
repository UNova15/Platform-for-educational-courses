package kira.auth.adapter.out.persistence.token;

import java.util.Optional;

import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.auth.application.ports.out.persistance.RefreshTokenRepositoryPort;

@Repository
@RequiredArgsConstructor
class RefreshTokenAdapter implements RefreshTokenRepositoryPort {
    private final RefreshTokenRepository repository;
    private final RefreshTokenMapper mapper;

    @Override
    public RefreshToken save(RefreshToken token) {
        RefreshTokenEntity entity = mapper.toEntity(token);
        RefreshTokenEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void remove(RefreshToken token) {
        repository.deleteById(token.id().value());
    }

    @Override
    public Optional<RefreshToken> load(HashedRefreshToken token) {
        return repository.findByToken(token.value()).map(mapper::toDomain);
    }
}
