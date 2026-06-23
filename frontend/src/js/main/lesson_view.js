import { getStudentLesson } from './api.js';
import { requireStudent } from './guards.js';

function getParams() {
    const p = new URLSearchParams(window.location.search);
    return {
        courseId: p.get('courseId'),
        moduleId: p.get('moduleId'),
        lessonId: p.get('lessonId'),
    };
}

export async function initLessonViewPage() {
    if (!document.querySelector('.lesson-view-page')) return;
    if (!requireStudent()) return;

    const { courseId, moduleId, lessonId } = getParams();
    if (!courseId || !moduleId || !lessonId) {
        document.getElementById('lessonLoading').hidden = true;
        document.getElementById('lessonError').hidden = false;
        return;
    }

    try {
        const lesson = await getStudentLesson(courseId, moduleId, lessonId);
        document.getElementById('lessonTitle').textContent = lesson.title || 'Урок';
        document.getElementById('lessonContent').textContent = lesson.content || '';
        document.getElementById('lessonLoading').hidden = true;
    } catch (e) {
        console.error(e);
        document.getElementById('lessonLoading').hidden = true;
        document.getElementById('lessonError').hidden = false;
    }
}