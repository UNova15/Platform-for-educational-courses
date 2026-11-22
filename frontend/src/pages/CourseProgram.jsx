import React, { useState } from 'react';
import '../styles/CourseProgram.scss';

const CourseProgram = () => {
  const [openModule, setOpenModule] = useState(1);

  const modules = [
    {
      id: 1,
      title: 'Модуль 1. Введение',
      info: '',
      lessons: [
        { id: 1, type: 'Урок', title: 'Урок 1. Что такое...' },
        { id: 2, type: 'Урок', title: 'Урок 1. Что такое...' },
        { id: 3, type: 'Тест', title: 'Тест 1. Название теста' },
      ],
    },
    { id: 2, title: 'Модуль 2. Название', info: '6 уроков • 1 тест', lessons: [] },
    { id: 3, title: 'Модуль 3. Название', info: '5 уроков • 1 тест', lessons: [] },
  ];

  return (
    <div className="program-page">
      <div className="program-header">
        <h1>Курс: Название курса</h1>
        <button className="btn-back">Назад</button>
      </div>

      <div className="tabs">
        <button className="tab-btn">Курс</button>
        <button className="tab-btn active">Чат курса</button>
      </div>

      <h2 className="section-title">Программа курса</h2>

      <div className="modules-list">
        {modules.map((module) => (
          <div key={module.id} className="module-card">
            <div
              className="module-header"
              onClick={() => setOpenModule(openModule === module.id ? null : module.id)}
            >
              <div>
                <h3>{module.title}</h3>
                {module.info && <p className="module-info">{module.info}</p>}
              </div>
              <span className="arrow">{openModule === module.id ? '⌃' : '⌄'}</span>
            </div>

            {openModule === module.id && module.lessons.length > 0 && (
              <div className="lessons-list">
                {module.lessons.map((lesson) => (
                  <div key={lesson.id} className="lesson-row">
                    <div className="lesson-left">
                      <div
                        className={`dot ${lesson.type === 'Тест' ? 'dot-test' : 'dot-lesson'}`}
                      ></div>
                      <span>{lesson.title}</span>
                    </div>
                    <button className="btn-watch">Смотреть</button>
                  </div>
                ))}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseProgram;
