package org.platform.platformforeducationalcourses.mapper;

import org.mapstruct.Mapper;
import org.platform.platformforeducationalcourses.dto.auth.login.LoginDto;
import org.platform.platformforeducationalcourses.dto.auth.login.LoginRequest;
import org.platform.platformforeducationalcourses.dto.auth.registration.RegistrationDto;
import org.platform.platformforeducationalcourses.dto.auth.registration.RegistrationRequest;

@Mapper
public interface AuthMapper {
    RegistrationDto toRegistrationDto(RegistrationRequest request);

    LoginDto toLoginDto(LoginRequest request);
}
