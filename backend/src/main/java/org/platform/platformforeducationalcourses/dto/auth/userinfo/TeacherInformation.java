package org.platform.platformforeducationalcourses.dto.auth.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeacherInformation extends UserInformation {
    private String educationInformation;
    private String scientificAchievements;
    private String additionalInformation;

}
