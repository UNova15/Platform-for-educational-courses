import { createCourse } from "./api.js";
import { requireTeacher } from "./guards.js";

let moduleCounter = 0;
let lessonCounter = 0;

function createLessonBlock() {
  lessonCounter++;
  const block = document.createElement("div");
  block.classList.add("lesson-block");
  block.dataset.lessonId = `lesson-${lessonCounter}`;

  block.innerHTML = `
        <button type="button" class="lesson-block__delete" title="Удалить урок">✕</button>
        <input type="text" class="lesson-block__title-input"
               placeholder="Название урока" maxlength="100" required>
        <textarea class="lesson-block__desc-input"
                  placeholder="Описание урока" maxlength="500" rows="2"></textarea>
        <textarea class="lesson-block__content-input"
                  placeholder="Контент" maxlength="1000" required></textarea>
        <label class="lesson-block__mandatory-label">
            <input type="checkbox" class="lesson-block__mandatory-checkbox" checked>
            Обязательный урок
        </label>
    `;

  block.querySelector(".lesson-block__delete").addEventListener("click", () => {
    block.remove();
  });

  return block;
}

function createTestBlock() {
  const block = document.createElement("div");
  block.classList.add("test-block");

  block.innerHTML = `
        <div class="test-block__header">
            <span class="test-block__label">Тест</span>
            <button type="button" class="test-block__delete" title="Удалить тест">✕</button>
        </div>
        <textarea class="test-block__desc-input"
                  placeholder="Описание теста" rows="2"></textarea>
        <div class="test-block__questions" data-questions></div>
        <button type="button" class="btn-add btn-add--small" data-action="add-question">
            <span class="btn-add__icon">+</span> Добавить вопрос
        </button>
    `;

  const questionsContainer = block.querySelector("[data-questions]");

  block
    .querySelector('[data-action="add-question"]')
    .addEventListener("click", () => {
      questionsContainer.appendChild(createQuestionBlock());
    });

  block.querySelector(".test-block__delete").addEventListener("click", () => {
    block.remove();
  });

  questionsContainer.appendChild(createQuestionBlock());

  return block;
}

function createQuestionBlock() {
  const block = document.createElement("div");
  block.classList.add("question-block");

  block.innerHTML = `
        <div class="question-block__header">
            <input type="text" class="question-block__input"
                   placeholder="Текст вопроса" maxlength="100" required>
            <button type="button" class="question-block__delete" title="Удалить вопрос">✕</button>
        </div>
        <div class="question-block__options" data-options></div>
        <button type="button" class="btn-add btn-add--tiny" data-action="add-option">
            <span class="btn-add__icon">+</span> Добавить вариант
        </button>
    `;

  const optionsContainer = block.querySelector("[data-options]");

  block
    .querySelector('[data-action="add-option"]')
    .addEventListener("click", () => {
      optionsContainer.appendChild(createOptionBlock());
    });

  block
    .querySelector(".question-block__delete")
    .addEventListener("click", () => {
      block.remove();
    });

  optionsContainer.appendChild(createOptionBlock(true));
  optionsContainer.appendChild(createOptionBlock(false));

  return block;
}

function createOptionBlock(isCorrect = false) {
  const block = document.createElement("div");
  block.classList.add("option-block");

  block.innerHTML = `
        <label class="option-block__correct-label">
            <input type="checkbox" class="option-block__correct" ${isCorrect ? "checked" : ""}>
        </label>
        <input type="text" class="option-block__text"
               placeholder="Вариант ответа" maxlength="100" required>
        <button type="button" class="option-block__delete" title="Удалить">✕</button>
    `;

  block.querySelector(".option-block__delete").addEventListener("click", () => {
    block.remove();
  });

  return block;
}

function createModuleBlock() {
  moduleCounter++;
  const block = document.createElement("div");
  block.classList.add("module-block");
  block.dataset.moduleId = `module-${moduleCounter}`;

  block.innerHTML = `
        <button type="button" class="module-block__delete" title="Удалить модуль">✕</button>
        <div class="module-block__header">
            <input type="text" class="module-block__title-input"
                   placeholder="Название модуля" maxlength="100" required>
        </div>
        <div class="module-block__desc">
            <textarea class="module-block__desc-input"
                      placeholder="Описание модуля" maxlength="500" rows="2"></textarea>
        </div>
        <div class="module-block__lessons" data-lessons></div>
        <div class="module-block__tests" data-tests></div>
        <div class="module-block__actions">
            <button type="button" class="btn-add" data-action="add-lesson">
                <span class="btn-add__icon">+</span> Добавить урок
            </button>
            <button type="button" class="btn-add" data-action="add-test">
                <span class="btn-add__icon">+</span> Добавить тест
            </button>
        </div>
    `;

  const lessonsContainer = block.querySelector("[data-lessons]");
  const testsContainer = block.querySelector("[data-tests]");

  block
    .querySelector('[data-action="add-lesson"]')
    .addEventListener("click", () => {
      lessonsContainer.appendChild(createLessonBlock());
    });

  block
    .querySelector('[data-action="add-test"]')
    .addEventListener("click", () => {
      testsContainer.appendChild(createTestBlock());
    });

  block.querySelector(".module-block__delete").addEventListener("click", () => {
    if (confirm("Удалить модуль со всеми уроками и тестами?")) {
      block.remove();
    }
  });
  lessonsContainer.appendChild(createLessonBlock());

  return block;
}

function collectFormData() {
  const title = document.getElementById("courseTitle").value.trim();
  const description = document.getElementById("courseDescription").value.trim();
  const tag = document.getElementById("courseTag").value;

  if (!title) {
    alert("Введите название курса");
    return null;
  }

  const moduleBlocks = document.querySelectorAll(".module-block");
  const modules = [];

  let moduleIndex = 0;
  for (const moduleBlock of moduleBlocks) {
    const moduleTitle = moduleBlock
      .querySelector(".module-block__title-input")
      .value.trim();
    const moduleDesc =
      moduleBlock.querySelector(".module-block__desc-input")?.value.trim() ||
      "";

    if (!moduleTitle) {
      alert("Заполните название модуля");
      return null;
    }

    const lessonBlocks = moduleBlock.querySelectorAll(
      ":scope > [data-lessons] > .lesson-block",
    );
    const lessons = [];
    let lessonIndex = 0;

    for (const lb of lessonBlocks) {
      const lTitle = lb
        .querySelector(".lesson-block__title-input")
        .value.trim();
      const lContent = lb
        .querySelector(".lesson-block__content-input")
        .value.trim();
      const lType = "TEXT";
      const lMandatory =
        lb.querySelector(".lesson-block__mandatory-checkbox")?.checked ?? true;

      if (!lTitle || !lContent) {
        alert("Заполните название и контент каждого урока");
        return null;
      }

      lessons.push({
        title: lTitle,
        type: lType,
        content: lContent,
        orderIndex: lessonIndex,
        mandatory: lMandatory,
      });
      lessonIndex++;
    }

    const testBlocks = moduleBlock.querySelectorAll(
      ":scope > [data-tests] > .test-block",
    );
    const tests = [];
    let testIndex = 0;

    for (const tb of testBlocks) {
      const tDesc =
        tb.querySelector(".test-block__desc-input")?.value.trim() || "";
      const questionBlocks = tb.querySelectorAll(".question-block");
      const questions = [];

      for (const qb of questionBlocks) {
        const qText = qb.querySelector(".question-block__input").value.trim();
        if (!qText) {
          alert("Заполните текст вопроса");
          return null;
        }

        const optionBlocks = qb.querySelectorAll(".option-block");
        const options = [];

        for (const ob of optionBlocks) {
          const oText = ob.querySelector(".option-block__text").value.trim();
          const oCorrect = ob.querySelector(".option-block__correct").checked;

          if (!oText) {
            alert("Заполните текст варианта ответа");
            return null;
          }

          options.push({ option: oText, isCorrect: oCorrect });
        }

        if (options.length < 2) {
          alert("Добавьте минимум 2 варианта ответа");
          return null;
        }

        if (!options.some((o) => o.isCorrect)) {
          alert("Отметьте хотя бы один правильный ответ");
          return null;
        }

        questions.push({ question: qText, options });
      }

      if (questions.length === 0) {
        alert("Добавьте хотя бы один вопрос в тест");
        return null;
      }

      tests.push({
        description: tDesc,
        orderIndex: testIndex,
        questions,
      });
      testIndex++;
    }

    modules.push({
      title: moduleTitle,
      description: moduleDesc,
      orderIndex: moduleIndex,
      lessons,
      tests,
    });
    moduleIndex++;
  }

  return {
    title,
    description: description || null,
    tag: tag || null,
    modules,
  };
}

async function handleSubmit(e) {
  e.preventDefault();
  const submitBtn = document.querySelector(".btn-submit");
  const data = collectFormData();
  if (!data) return;

  submitBtn.disabled = true;
  submitBtn.textContent = "Создание...";

  try {
    await createCourse(data);
    alert("Курс успешно создан!");
    window.location.href = "/teacher_index.html";
  } catch (error) {
    console.error("Ошибка создания курса:", error);
    alert(
      "Не удалось создать курс: " + (error.message || "Неизвестная ошибка"),
    );
    submitBtn.disabled = false;
    submitBtn.textContent = "Добавить курс";
  }
}

export function initCourseCreatePage() {
  if (!document.querySelector(".course-create-page")) return;
  if (!requireTeacher()) return;

  const modulesContainer = document.getElementById("modulesContainer");
  const addModuleBtn = document.getElementById("addModuleBtn");
  const form = document.getElementById("courseForm");

  if (!modulesContainer || !addModuleBtn || !form) {
    console.error("Элементы формы не найдены");
    return;
  }

  modulesContainer.appendChild(createModuleBlock());

  addModuleBtn.addEventListener("click", () => {
    modulesContainer.appendChild(createModuleBlock());
  });

  form.addEventListener("submit", handleSubmit);
}
