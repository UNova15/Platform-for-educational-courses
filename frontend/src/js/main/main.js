import './popup.js';

import { initAuthHandlers } from './handler.js';
import { initTeacherPage } from './teacher.js';
import { initCourseCreatePage } from './course_create.js';
import { initProfilePage, initTeacherProfilePage } from './profile.js';
import { initCatalog } from './courses.js';
import { initCourseViewPage } from './course_view.js';
import { initLessonViewPage } from './lesson_view.js';
import { initTestViewPage } from './test_view.js';
import { redirectTeacherFromHome } from './guards.js';

function getPageName() {
    const path = window.location.pathname;
    if (path === '/' || path === '') return 'index.html';
    return path.split('/').pop() || 'index.html';
}

document.addEventListener('DOMContentLoaded', () => {
    initAuthHandlers();
    const page = getPageName();

    switch (page) {
        case 'index.html':
            if (redirectTeacherFromHome()) return;
            initCatalog();
            break;
        case 'teacher_index.html':
            initTeacherPage();
            break;
        case 'teacher_course_create.html':
            initCourseCreatePage();
            break;
        case 'profile_student.html':
            initProfilePage();
            break;
        case 'profile_teacher.html':
            initTeacherProfilePage();
            break;
        case 'course_view.html':
            initCourseViewPage();
            break;
        case 'lesson_view.html':
            initLessonViewPage();
            break;
        case 'test_view.html':
            initTestViewPage();
            break;
        default:
            break;
    }
});