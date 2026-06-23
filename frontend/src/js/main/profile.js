import { getUser, isTeacher } from './auth.js';
import { requireStudent, requireAuth } from './guards.js';
import { getStudentCourses, getStudentCourse, getTeacherCourses } from './api.js';

const AVATAR_KEY = 'profileAvatar';
const DEFAULT_AVATAR = '/assets/avatars/avatar-default.svg';

function escapeHtml(str) {
    return String(str ?? '')
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;')
        .replaceAll('"', '&quot;')
        .replaceAll("'", '&#039;');
}

function getCourseProgress(courseDetails) {
    const modules = courseDetails?.modules || [];
    let total = 0;
    let completed = 0;

    modules.forEach(module => {
        const lessons = module.lessons || [];
        const tests = module.tests || [];

        total += lessons.length + tests.length;
        completed += lessons.filter(l => l.completedAt).length;
        completed += tests.filter(t => t.completedAt).length;
    });

    return {
        percent: total > 0 ? Math.round((completed / total) * 100) : 0,
        isCompleted: total > 0 && completed === total
    };
}

function drawDonutChart(completed, total) {
    const fill = document.getElementById('donutFill');
    if (!fill) return;

    const radius = 50;
    const circumference = 2 * Math.PI * radius;
    const percent = total > 0 ? completed / total : 0;
    const offset = circumference * (1 - percent);

    fill.style.strokeDasharray = `${circumference}`;
    setTimeout(() => {
        fill.style.strokeDashoffset = offset;
    }, 100);
}

function renderCoursesGrid(containerId, courses) {
    const grid = document.getElementById(containerId);
    if (!grid) return;

    if (!courses || courses.length === 0) {
        grid.innerHTML = '<p class="profile-empty-text">Курсов пока нет</p>';
        return;
    }

    grid.innerHTML = courses.map(course => `
        <div class="profile-course-card" data-id="${course.id}"
             style="${course.cover
        ? `background-image: url('${course.cover}'); background-size: cover;`
        : ''}">
            <h4 class="profile-course-card__title">${escapeHtml(course.title)}</h4>
        </div>
    `).join('');

    grid.querySelectorAll('.profile-course-card').forEach(card => {
        card.addEventListener('click', () => {
            window.location.href = `/course-view.html?id=${card.dataset.id}`;
        });
    });
}

function renderStudentProfile(data) {
    const completedEl = document.getElementById('completedCount');
    const activeEl = document.getElementById('activeCount');

    if (completedEl) completedEl.textContent = ` ${data.completedCount}`;
    if (activeEl) activeEl.textContent = ` ${data.activeCount}`;

    drawDonutChart(data.completedCount, data.activeCount + data.completedCount);
    renderCoursesGrid('activeCoursesGrid', data.activeCourses);
    renderCoursesGrid('completedCoursesGrid', data.completedCourses);
}

async function loadStudentProfileData() {
    try {
        const courses = await getStudentCourses();

        const detailed = await Promise.all(
            (courses || []).map(async (course) => {
                try {
                    const details = await getStudentCourse(course.id);
                    return { ...course, details };
                } catch {
                    return { ...course, details: null };
                }
            })
        );

        const activeCourses = [];
        const completedCourses = [];

        detailed.forEach(course => {
            const progress = getCourseProgress(course.details);
            const item = {
                id: course.id,
                title: course.title,
                cover: '',
                progress: progress.percent
            };

            if (progress.isCompleted) {
                completedCourses.push(item);
            } else {
                activeCourses.push(item);
            }
        });

        renderStudentProfile({
            activeCount: activeCourses.length,
            completedCount: completedCourses.length,
            activeCourses,
            completedCourses
        });
    } catch (error) {
        console.error('Ошибка загрузки профиля студента:', error);
        renderStudentProfile({
            activeCount: 0,
            completedCount: 0,
            activeCourses: [],
            completedCourses: []
        });
    }
}

function initLogout() {
    const btn = document.getElementById('logoutBtn');
    if (!btn) return;

    btn.addEventListener('click', () => {
        if (!confirm('Вы уверены, что хотите выйти?')) return;

        localStorage.removeItem('authToken');
        localStorage.removeItem('user');

        window.location.href = '/index.html';
    });
}

function initAvatar() {
    const avatarImg = document.getElementById('profileAvatarImg');
    const avatarBtn = document.getElementById('profileAvatar');
    const popup = document.getElementById('avatarPopup');
    if (!avatarImg || !avatarBtn || !popup) return;

    const saved = localStorage.getItem(AVATAR_KEY) || DEFAULT_AVATAR;
    avatarImg.src = saved;

    avatarBtn.addEventListener('click', () => {
        const current = localStorage.getItem(AVATAR_KEY) || DEFAULT_AVATAR;
        popup.querySelectorAll('.avatar-grid__item').forEach((item) => {
            item.classList.toggle('is-active', item.dataset.avatar === current);
        });
        window.openPopup('avatarPopup');
    });

    popup.querySelectorAll('.avatar-grid__item').forEach((item) => {
        item.addEventListener('click', () => {
            const newSrc = item.dataset.avatar;
            avatarImg.src = newSrc;
            localStorage.setItem(AVATAR_KEY, newSrc);
            window.closePopup('avatarPopup');
        });
    });
}

export function initProfilePage() {
    const container = document.querySelector('.profile-page');
    if (!container) return;
    if (!requireStudent()) return;

    const user = getUser();

    const nameEl = document.getElementById('profileName');
    if (nameEl && user) {
        nameEl.textContent = user.login || 'Ученик';
    }

    const roleEl = document.getElementById('profileRole');
    if (roleEl && user) {
        const roleMap = {
            STUDENT: 'Студент',
            TEACHER: 'Преподаватель',
            ADMIN: 'Администратор'
        };
        roleEl.textContent = roleMap[user.role] || 'Пользователь';
    }

    initAvatar();
    initLogout();
    loadStudentProfileData();
}

export function initTeacherProfilePage() {
    const container = document.querySelector('.profile-page');
    if (!container) return;
    if (!requireAuth()) return;
    if (!isTeacher()) {
        window.location.href = '/profile_student.html';
        return;
    }

    const user = getUser();

    const nameEl = document.getElementById('profileName');
    if (nameEl && user) nameEl.textContent = user.login || 'Преподаватель';

    const roleEl = document.getElementById('profileRole');
    if (roleEl) roleEl.textContent = 'Преподаватель';

    initAvatar();
    initLogout();
    loadTeacherStats();
}

async function loadTeacherStats() {
    const countEl = document.getElementById('teacherCoursesCount');
    if (!countEl) return;

    try {
        const courses = await getTeacherCourses();
        countEl.textContent = ` ${Array.isArray(courses) ? courses.length : 0}`;
    } catch (e) {
        console.error('Ошибка загрузки курсов учителя:', e);
        countEl.textContent = ' 0';
    }
}