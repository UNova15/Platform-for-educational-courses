package common.domain;

import java.util.Optional;
import java.util.function.Function;

public sealed interface Result<S, F> permits Result.Success, Result.Failure {

    static <S, F> Result<S, F> success(S success) {
        return new Success<>(success);
    }

    static <S, F> Result<S, F> failure(F error) {
        return new Failure<>(error);
    }

    static <S, F> Result<S, F> fromOptional(Optional<S> optional, F errorIfEmpty) {
        return optional.map(Result::<S, F>success).orElseGet(() -> failure(errorIfEmpty));
    }

    <T> Result<T, F> map(Function<S, T> mapper);

    <T> Result<T, F> flatMap(Function<S, Result<T, F>> mapper);

    <R> R fold(Function<S, R> onSuccess, Function<F, R> onFailure);

    boolean isSuccess();

    S getSuccess();

    F getError();

    record Success<S, F>(S value) implements Result<S, F> {

        @Override
        public <T> Result<T, F> map(Function<S, T> mapper) {
            return success(mapper.apply(value));
        }

        @Override
        public <T> Result<T, F> flatMap(Function<S, Result<T, F>> mapper) {
            return mapper.apply(value);
        }

        @Override
        public <R> R fold(Function<S, R> onSuccess, Function<F, R> onFailure) {
            return onSuccess.apply(value);
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public S getSuccess() {
            return value;
        }

        @Override
        public F getError() {
            throw new IllegalStateException("The exception is not contained in the successful branch");
        }
    }

    record Failure<S, F>(F error) implements Result<S, F> {
        @Override
        public <T> Result<T, F> map(Function<S, T> mapper) {
            return failure(error);
        }

        @Override
        public <T> Result<T, F> flatMap(Function<S, Result<T, F>> mapper) {
            return failure(error);
        }

        @Override
        public <R> R fold(Function<S, R> onSuccess, Function<F, R> onFailure) {
            return onFailure.apply(error);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public S getSuccess() {
            throw new IllegalStateException("The success value is not contained in the failure branch");
        }

        @Override
        public F getError() {
            return error;
        }
    }
}
