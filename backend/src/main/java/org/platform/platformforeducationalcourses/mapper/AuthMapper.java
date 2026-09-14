package org.platform.platformforeducationalcourses.mapper;

import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.dto.auth.login.LoginDto;
import org.platform.platformforeducationalcourses.dto.auth.registration.RegistrationDto;
import refactor.auth.implemetnation.adapter.in.web.LoginCommand;
import refactor.auth.implemetnation.adapter.in.web.RegistrationCommand;

@Mapper
public interface AuthMapper {
    RegistrationDto toRegistrationDto(RegistrationCommand request);

    LoginDto toLoginDto(LoginCommand request);
}
