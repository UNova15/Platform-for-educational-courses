import { startTestAttempt, submitTest } from './api.js';
import { requireStudent } from './guards.js';

function getParams() {
    const p = new URLSearchParams(window.location.search);
    return {
        courseId: p.get('courseId'),
        moduleId: p.get('moduleId'),
        testId: p.get('testId'),
    };
}

function escapeHtml(str) {
    const div = document.createElement('div');
    div.textContent = str ?? '';
    return div.innerHTML;
}

let questions = [];

function renderQuestions(qs) {
    const container = document.getElementById('testQuestions');
    container.innerHTML = qs.map((q, qi) => `
        <div class="test-question" data-question-id="${q.questionId || q.id}">
            <p class="test-question__text">${qi + 1}. ${escapeHtml(q.question)}</p>
            ${(q.options || []).map(o => `
                <label class="test-option">
                    <input type="radio"
                           name="q-${q.questionId || q.id}"
                           value="${o.optionId || o.id}">
                    <span>${escapeHtml(o.option)}</span>
                </label>
            `).join('')}
        </div>
    `).join('');
}

function collectAnswers() {
    return questions.map(q => {
        const qid = q.questionId || q.id;
        const selected = document.querySelector(`input[name="q-${qid}"]:checked`);
        return {
            questionId: qid,
            optionId: selected ? selected.value : null,
        };
    });
}

export async function initTestViewPage() {
    if (!document.querySelector('.test-view-page')) return;
    if (!requireStudent()) return;

    const { courseId, moduleId, testId } = getParams();
    if (!courseId || !moduleId || !testId) {
        document.getElementById('testLoading').hidden = true;
        document.getElementById('testError').hidden = false;
        return;
    }

    try {
        const attempt = await startTestAttempt(courseId, moduleId, testId);
        questions = attempt.questions || [];
        document.getElementById('testTitle').textContent = attempt.description || 'Тест';
        renderQuestions(questions);
        document.getElementById('testLoading').hidden = true;
        document.getElementById('submitTestBtn').hidden = false;

        document.getElementById('submitTestBtn').addEventListener('click', async () => {
            const answers = collectAnswers();
            if (answers.some(a => !a.optionId)) {
                if (!confirm('Не все вопросы отвечены. Отправить как есть?')) return;
            }
            try {
                const result = await submitTest(courseId, moduleId, testId, answers);
                const resEl = document.getElementById('testResult');
                resEl.hidden = false;
                resEl.textContent = `Результат: ${result.score ?? '—'} из ${result.total ?? questions.length}`;
                document.getElementById('submitTestBtn').hidden = true;
            } catch (e) {
                alert('Ошибка отправки: ' + (e.message || ''));
            }
        });
    } catch (e) {
        console.error(e);
        document.getElementById('testLoading').hidden = true;
        document.getElementById('testError').hidden = false;
    }
}