import { getCatalogCourses } from './api.js';

let categoryCards;
let selectedCategoryTitle;
let coursesHint;
let coursesGrid;
let coursesLoading;

export function initCatalog() {
    categoryCards = document.querySelectorAll('.category-card');
    selectedCategoryTitle = document.getElementById('selectedCategoryTitle');
    coursesHint = document.getElementById('coursesHint');
    coursesGrid = document.getElementById('coursesGrid');
    coursesLoading = document.getElementById('coursesLoading');

    if (!categoryCards.length) return;

    initCategoryCards();
}

export function initCategoryCards() {
    categoryCards.forEach((card) => {
        card.addEventListener('click', async () => {
            const tag = card.dataset.tag;
            const title = card.dataset.title;

            categoryCards.forEach((item) => item.classList.remove('active'));
            card.classList.add('active');

            selectedCategoryTitle.textContent = title;
            coursesHint.style.display = 'none';
            coursesGrid.innerHTML = '';
            coursesLoading.style.display = 'block';

            try {
                const response = await getCatalogCourses(0, 9, tag);
                const courses = response.content || [];

                coursesLoading.style.display = 'none';
                renderCourses(courses, title);
            } catch (error) {
                coursesLoading.style.display = 'none';
                coursesHint.style.display = 'block';
                coursesHint.textContent = 'Не удалось загрузить курсы. Попробуйте позже.';
                console.error('Ошибка при загрузке курсов:', error);
            }
        });
    });
}

function renderCourses(courses, categoryTitle) {
    if (!courses || !courses.length) {
        coursesHint.style.display = 'block';
        coursesHint.textContent = `В категории "${categoryTitle}" пока нет курсов`;
        coursesGrid.innerHTML = '';
        return;
    }

    coursesHint.style.display = 'none';
    coursesGrid.innerHTML = courses.map((course) => `
        <article class="course-card" data-id="${course.id}">
            <div class="course-card__top">
                <span class="course-card__tag">${formatTag(course.tag)}</span>
            </div>
            <h4 class="course-card__title">${course.title}</h4>
            <p class="course-card__description">
                ${course.description || 'Описание отсутствует'}
            </p>
            <p class="course-card__date">${formatDate(course.createdAt)}</p>
        </article>
    `).join('');

    // ↓ ДОБАВЛЯЕМ ОБРАБОТЧИК КЛИКА
    coursesGrid.querySelectorAll('.course-card').forEach(card => {
        card.addEventListener('click', () => {
            const id = card.dataset.id;
            window.location.href = `/course_view.html?id=${id}`;
        });
    });
}

function formatTag(tag) {
    const tagNames = {
        PROGRAMMING_AND_IT: 'Программирование',
        MATHEMATICS_AND_EXACT_SCIENCES: 'Математика',
        BUSINESS_AND_CAREER: 'Бизнес',
        DESIGN_AND_CREATIVITY: 'Дизайн',
        LANGUAGES_AND_HUMANITIES: 'Языки',
        PERSONAL_GROWTH_AND_HOBBIES: 'Саморазвитие'
    };
    return tagNames[tag] || tag;
}

function formatDate(dateString) {
    if (!dateString) return '';
    return new Date(dateString).toLocaleDateString('ru-RU', {
        day: 'numeric',
        month: 'long',
        year: 'numeric'
    });
}