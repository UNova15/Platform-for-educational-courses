package org.platform.platformforeducationalcourses.mapper;

import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.dto.auth.login.LoginDto;
import org.platform.platformforeducationalcourses.dto.auth.registration.RegistrationDto;
import refactor.user.application.ports.in.command.LoginCommand;
import refactor.user.application.ports.in.command.RegistrationCommand;

@Mapper
public interface AuthMapper {
    RegistrationDto toRegistrationDto(RegistrationCommand request);

    LoginDto toLoginDto(LoginCommand request);
}
