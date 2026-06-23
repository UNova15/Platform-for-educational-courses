import { getTeacherCourses } from './api.js';
import { requireTeacher } from './guards.js';
import { COURSE_TAGS } from './constants.js';

function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str ?? '';
    return div.innerHTML;
}

function createCourseCard(course) {
    const card = document.createElement('div');
    card.classList.add('teacher-course-card');
    card.dataset.courseId = course.id;

    const tagLabel = COURSE_TAGS[course.tag] || '';
    const editUrl = `/teacher_course_edit.html?id=${course.id}`;
    const manageUrl = `/teacher_course.html?id=${course.id}`;

    card.innerHTML = `
        ${tagLabel ? `<span class="teacher-course-card__tag">${tagLabel}</span>` : ''}
        <h3 class="teacher-course-card__title">${escapeHtml(course.title)}</h3>
        <a href="${editUrl}" class="teacher-course-card__edit-btn" data-action="edit">Редактировать</a>
    `;

    card.addEventListener('click', (e) => {
        if (e.target.closest('[data-action="edit"]')) return;
        window.location.href = manageUrl;
    });

    return card;
}

function getEls() {
    return {
        skeleton: document.getElementById('teacherCoursesSkeleton'),
        grid:     document.getElementById('teacherCoursesGrid'),
        empty:    document.getElementById('teacherEmpty'),
    };
}

function removeSkeleton() {
    const skeleton = document.getElementById('teacherCoursesSkeleton');
    if (skeleton) skeleton.remove();
}

function showSkeleton() {
    const { skeleton, grid, empty } = getEls();
    if (skeleton) { skeleton.hidden = false; skeleton.style.display = ''; }
    if (grid)     { grid.hidden = true;  grid.style.display = 'none'; }
    if (empty)    { empty.hidden = true; empty.style.display = 'none'; }
}

function showCourses(courses) {
    removeSkeleton();
    const grid = document.getElementById('teacherCoursesGrid');
    const empty = document.getElementById('teacherEmpty');

    if (empty) { empty.hidden = true; empty.style.display = 'none'; }
    if (!grid) return;

    grid.innerHTML = '';
    courses.forEach(c => grid.appendChild(createCourseCard(c)));
    grid.hidden = false;
    grid.style.display = '';
}

function showEmpty(message) {
    removeSkeleton();
    const grid = document.getElementById('teacherCoursesGrid');
    const empty = document.getElementById('teacherEmpty');

    if (grid) {
        grid.innerHTML = '';
        grid.hidden = true;
        grid.style.display = 'none';
    }
    if (empty) {
        empty.hidden = false;
        empty.style.display = '';
        if (message) {
            const textEl = empty.querySelector('.teacher-empty__text');
            if (textEl) textEl.textContent = message;
        }
    }
}

async function loadCourses() {
    showSkeleton();
    try {
        const courses = await getTeacherCourses();
        if (!courses || courses.length === 0) {
            showEmpty('У вас пока нет курсов');
        } else {
            showCourses(courses);
        }
    } catch (error) {
        console.error('Ошибка загрузки курсов:', error);
        showEmpty('Не удалось загрузить курсы. Попробуйте перезайти в аккаунт.');
    }
}

export function initTeacherPage() {
    if (!document.querySelector('.teacher-page')) return;
    if (!requireTeacher()) return;
    loadCourses();
}