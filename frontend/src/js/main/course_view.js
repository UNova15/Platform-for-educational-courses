import { getCatalogCourseDetails, enrollInCourse, getStudentCourse } from './api.js';
import { isAuthenticated, isStudent } from './auth.js';
import { COURSE_TAGS } from './constants.js';

function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str ?? '';
    return div.innerHTML;
}

function getCourseIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get('id');
}

function renderHeader(course) {
    document.getElementById('courseTitle').textContent = course.title;
    document.getElementById('breadcrumbsCourseTitle').textContent = course.title;
    document.getElementById('courseDescription').textContent = course.description || '';

    const tagEl = document.getElementById('courseTag');
    if (course.tag && COURSE_TAGS[course.tag]) {
        tagEl.textContent = COURSE_TAGS[course.tag];
        tagEl.hidden = false;
    } else {
        tagEl.hidden = true;
    }
}

function renderModules(course, enrolled) {
    const container = document.getElementById('modulesList');
    container.innerHTML = '';

    const modules = course.modules || [];
    if (modules.length === 0) {
        container.innerHTML = '<p>В этом курсе пока нет модулей</p>';
        return;
    }

    modules.forEach(module => {
        const block = document.createElement('div');
        block.classList.add('module-item');

        const lessons = (module.lessons || []).map(l => `
            <div class="lesson-row ${enrolled ? '' : 'lesson-row--locked'}"
                 data-type="lesson"
                 data-lesson-id="${l.lessonId || l.id}"
                 data-module-id="${module.moduleId || module.id}">
                <span class="lesson-row__name">${escapeHtml(l.title)}</span>
                <span class="lesson-row__type">Урок</span>
            </div>
        `).join('');

        const tests = (module.tests || []).map(t => `
            <div class="lesson-row ${enrolled ? '' : 'lesson-row--locked'}"
                 data-type="test"
                 data-test-id="${t.testId || t.id}"
                 data-module-id="${module.moduleId || module.id}">
                <span class="lesson-row__name">${escapeHtml(t.description || 'Тест')}</span>
                <span class="lesson-row__type">Тест</span>
            </div>
        `).join('');

        block.innerHTML = `
            <h3 class="module-item__title">${escapeHtml(module.title)}</h3>
            <p class="module-item__description">${escapeHtml(module.description || '')}</p>
            <div class="module-item__lessons">
                ${lessons}
                ${tests}
            </div>
        `;

        container.appendChild(block);
    });

    if (enrolled) {
        const courseId = getCourseIdFromUrl();
        container.querySelectorAll('.lesson-row').forEach(row => {
            row.addEventListener('click', () => {
                const type = row.dataset.type;
                const moduleId = row.dataset.moduleId;
                if (type === 'lesson') {
                    const lessonId = row.dataset.lessonId;
                    window.location.href = `/lesson_view.html?courseId=${courseId}&moduleId=${moduleId}&lessonId=${lessonId}`;
                } else {
                    const testId = row.dataset.testId;
                    window.location.href = `/test_view.html?courseId=${courseId}&moduleId=${moduleId}&testId=${testId}`;
                }
            });
        });
    }
}

async function checkEnrollment(courseId) {
    if (!isAuthenticated() || !isStudent()) return null;
    try {
        return await getStudentCourse(courseId);
    } catch {
        return null;
    }
}

async function handleEnroll(courseId) {
    if (!isAuthenticated()) {
        window.openPopup('loginPopup');
        return;
    }
    if (!isStudent()) {
        alert('Записываться на курсы могут только студенты');
        return;
    }

    const btn = document.getElementById('enrollBtn');
    btn.disabled = true;
    btn.textContent = 'Записываем...';

    try {
        await enrollInCourse(courseId);
        // перезагрузим, чтобы подтянуть содержимое для записанного студента
        window.location.reload();
    } catch (e) {
        alert('Не удалось записаться: ' + (e.message || ''));
        btn.disabled = false;
        btn.textContent = 'Записаться на курс';
    }
}

export async function initCourseViewPage() {
    if (!document.querySelector('.course-view-page')) return;

    const courseId = getCourseIdFromUrl();
    if (!courseId) {
        document.getElementById('courseLoading').hidden = true;
        document.getElementById('courseError').hidden = false;
        return;
    }

    try {
        // загрузим публичную информацию о курсе
        const publicCourse = await getCatalogCourseDetails(courseId);
        renderHeader(publicCourse);

        // проверим, записан ли студент
        const studentCourse = await checkEnrollment(courseId);
        const enrolled = !!studentCourse;

        // если записан — используем «полную» версию с прогрессом
        renderModules(studentCourse || publicCourse, enrolled);

        document.getElementById('courseLoading').hidden = true;
        document.getElementById('courseContent').hidden = false;

        const enrollBtn = document.getElementById('enrollBtn');
        const enrolledBadge = document.getElementById('enrolledBadge');

        if (enrolled) {
            enrollBtn.hidden = true;
            enrolledBadge.hidden = false;
        } else {
            enrollBtn.hidden = false;
            enrolledBadge.hidden = true;
            enrollBtn.addEventListener('click', () => handleEnroll(courseId));
        }
    } catch (error) {
        console.error('Ошибка загрузки курса:', error);
        document.getElementById('courseLoading').hidden = true;
        document.getElementById('courseError').hidden = false;
    }
}