package org.platform.platformforeducationalcourses.dto.auth.userinfo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "role")
@JsonSubTypes({
    @JsonSubTypes.Type(value = StudentInformation.class, name = "STUDENT"),
    @JsonSubTypes.Type(value = TeacherInformation.class, name = "TEACHER")
})
public abstract class UserInformation {}
