const BASE_URL = '/api';

import { getToken, clearSession } from './auth.js';

async function refreshToken() {
    try {
        const response = await fetch(`${BASE_URL}/auth/refresh`, {
            method: 'POST',
            credentials: 'include',
        });

        if (!response.ok) throw new Error('Не удалось обновить токен');

        const data = await response.json();
        if (data.jwtToken) {
            localStorage.setItem('authToken', data.jwtToken);
        }
        return data.jwtToken;
    } catch (error) {
        clearSession();
        throw error;
    }
}

async function apiFetch(endpoint, options = {}) {
    const { body, _retry, ...customOptions } = options;

    const headers = {
        'Content-Type': 'application/json',
        ...options.headers,
    };

    const token = getToken();
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        ...customOptions,
        headers,
        credentials: 'include',
    };

    if (body !== undefined) {
        config.body = JSON.stringify(body);
    }

    let response = await fetch(`${BASE_URL}${endpoint}`, config);

    if (response.status === 401 && !_retry) {
        try {
            await refreshToken();
            return apiFetch(endpoint, { ...options, _retry: true });
        } catch {
            clearSession();
            window.location.replace('/index.html');
            throw new Error('Сессия истекла');
        }
    }

    if (response.status === 403) {
        throw new Error('Доступ запрещён (403). Возможно, у вашей учётной записи нет нужных прав.');
    }

    if (!response.ok) {
        let errorMessage = `Ошибка ${response.status}`;
        try {
            const errorData = await response.json();
            errorMessage = errorData.message || errorData.error || errorMessage;
        } catch {
        }
        throw new Error(errorMessage);
    }

    if (response.status === 204) return { success: true };

    const text = await response.text();
    return text ? JSON.parse(text) : null;
}


export function register(userData) {
    return apiFetch('/auth/registration', { method: 'POST', body: userData });
}

export function login(credentials) {
    return apiFetch('/auth/login', { method: 'POST', body: credentials });
}

export function getCatalogCourses(page = 0, size = 9, tag = '') {
    let url = `/courses?page=${page}&size=${size}`;
    if (tag) url += `&tag=${encodeURIComponent(tag)}`;
    return apiFetch(url, { method: 'GET' });
}

export function getCatalogCourseDetails(courseId) {
    return apiFetch(`/courses/${courseId}`, { method: 'GET' });
}

export function getStudentCourses() {
    return apiFetch('/student/courses', { method: 'GET' });
}

export function getStudentCourse(courseId) {
    return apiFetch(`/student/courses/${courseId}`, { method: 'GET' });
}

export function enrollInCourse(courseId) {
    return apiFetch(`/student/enrollments/${courseId}`, { method: 'POST' });
}

export function getStudentLesson(courseId, moduleId, lessonId) {
    return apiFetch(
        `/student/courses/${courseId}/modules/${moduleId}/lessons/${lessonId}`,
        { method: 'GET' }
    );
}

export function startTestAttempt(courseId, moduleId, testId) {
    return apiFetch(
        `/student/courses/${courseId}/modules/${moduleId}/tests/${testId}/attempts`,
        { method: 'POST' }
    );
}

export function submitTest(courseId, moduleId, testId, answers) {
    return apiFetch(
        `/student/courses/${courseId}/modules/${moduleId}/tests/${testId}/submit`,
        { method: 'POST', body: { answers } }
    );
}

export function getTestReview(courseId, moduleId, testId) {
    return apiFetch(
        `/student/courses/${courseId}/modules/${moduleId}/tests/${testId}/review`,
        { method: 'GET' }
    );
}

export function getTeacherCourses() {
    return apiFetch('/teacher/courses', { method: 'GET' });
}

export function getTeacherCourse(courseId) {
    return apiFetch(`/teacher/courses/${courseId}`, { method: 'GET' });
}

export function createCourse(courseData) {
    return apiFetch('/teacher/courses', { method: 'POST', body: courseData });
}

export function updateCourse(courseId, courseData) {
    return apiFetch(`/teacher/courses/${courseId}`, { method: 'PUT', body: courseData });
}

export function deleteCourse(courseId) {
    return apiFetch(`/teacher/courses/${courseId}`, { method: 'DELETE' });
}

export function createModule(courseId, moduleData) {
    return apiFetch(`/teacher/courses/${courseId}/modules`, {
        method: 'POST',
        body: moduleData
    });
}

export function getModule(courseId, moduleId) {
    return apiFetch(`/teacher/courses/${courseId}/modules/${moduleId}`, {
        method: 'GET'
    });
}

export function updateModule(courseId, moduleId, moduleData) {
    return apiFetch(`/teacher/courses/${courseId}/modules/${moduleId}`, {
        method: 'PUT',
        body: moduleData
    });
}

export function deleteModule(courseId, moduleId) {
    return apiFetch(`/teacher/courses/${courseId}/modules/${moduleId}`, {
        method: 'DELETE'
    });
}

export function createLesson(courseId, moduleId, lessonData) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/lessons`,
        { method: 'POST', body: lessonData }
    );
}

export function getLesson(courseId, moduleId, lessonId) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/lessons/${lessonId}`,
        { method: 'GET' }
    );
}

export function updateLesson(courseId, moduleId, lessonId, lessonData) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/lessons/${lessonId}`,
        { method: 'PUT', body: lessonData }
    );
}

export function deleteLesson(courseId, moduleId, lessonId) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/lessons/${lessonId}`,
        { method: 'DELETE' }
    );
}

export function createTest(courseId, moduleId, testData) {
    return apiFetch(`/teacher/courses/${courseId}/modules/${moduleId}`, {
        method: 'POST',
        body: testData
    });
}

export function getTest(courseId, moduleId, testId) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/${testId}`,
        { method: 'GET' }
    );
}

export function updateTest(courseId, moduleId, testId, testData) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/${testId}`,
        { method: 'POST', body: testData }
    );
}

export function deleteTest(courseId, moduleId, testId) {
    return apiFetch(
        `/teacher/courses/${courseId}/modules/${moduleId}/${testId}`,
        { method: 'DELETE' }
    );
}