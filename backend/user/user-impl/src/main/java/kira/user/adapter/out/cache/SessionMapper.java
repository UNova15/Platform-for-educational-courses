package kira.user.adapter.out.cache;

import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Username;

public class SessionMapper {
    public static ConfirmationSession toDomain(SessionEntity entity) {
        return ConfirmationSession.restore(
                Username.of(entity.username()),
                Email.of(entity.email()),
                Code.of(entity.code()),
                HashedPassword.of(entity.hashedPassword()),
                entity.numberOfInputAttempts(),
                entity.step(),
                entity.lastLetterSent(),
                entity.expireAt());
    }

    public static SessionEntity toEntity(ConfirmationSession session) {
        return new SessionEntity(
                session.username().value(),
                session.email().value(),
                session.code().value(),
                session.hashedPassword().value(),
                session.numberOfInputAttempts(),
                session.step(),
                session.lastUpdateTime(),
                session.expireAt());
    }
}
