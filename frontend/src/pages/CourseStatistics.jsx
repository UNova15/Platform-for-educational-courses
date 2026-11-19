import React, { useState } from 'react';
import '../styles/CourseStatistics.scss';

const CourseStatistics = () => {
  const [activeTab, setActiveTab] = useState('lessons');
  const [isReportOpen, setIsReportOpen] = useState(false);

  const lessonStats = [
    { id: 1, name: 'Сурмалян Яна', status: 'Пройден', time: '47 часов', color: '#C4B5FD' },
    { id: 2, name: 'Сурмалян Яна', status: 'В процессе', time: '-', color: '#C4B5FD' },
    { id: 3, name: 'Сурмалян Яна', status: 'Не начат', time: '-', color: '#C4B5FD' },
  ];

  const testStats = [
    { id: 1, name: 'Сурмалян Яна', status: 'Пройден', result: '9/10', time: '14 мин', color: '#C4B5FD' },
    { id: 2, name: 'Сурмалян Яна', status: 'В процессе', result: '-', time: 'В процессе', color: '#C4B5FD' },
    { id: 3, name: 'Сурмалян Яна', status: 'Не начат', result: '-', time: '-', color: '#C4B5FD' },
  ];

  return (
    <div className="stats-page">
      
      <div className="stats-header">
        <h1>Статистика курса: название курса</h1>
        <button className="btn-back">Назад</button>
      </div>

      <div className="stats-tabs">
        <button 
          className={`tab-btn ${activeTab === 'lessons' ? 'active' : ''}`}
          onClick={() => { setActiveTab('lessons'); setIsReportOpen(false); }}
        >
          Уроки
        </button>
        <button 
          className={`tab-btn ${activeTab === 'tests' ? 'active' : ''}`}
          onClick={() => setActiveTab('tests')}
        >
          Тесты
        </button>
      </div>

      <div className="select-wrapper">
        <select className="lesson-select">
          {activeTab === 'lessons' ? (
            <option>Урок №1. Название урока</option>
          ) : (
            <option>Тест №1. Название теста</option>
          )}
        </select>
      </div>

      <div className="stats-content">
        <div className={`main-stats-area ${isReportOpen ? 'with-report' : ''}`}>
          
          <h2>Сводные данные</h2>
          <div className="summary-cards">
            {activeTab === 'tests' && (
              <div className="summary-card">
                <span className="card-label">Всего попыток</span>
                <span className="card-value">2</span>
              </div>
            )}
            <div className="summary-card">
              <span className="card-label">Успешность</span>
              <span className="card-value">75%</span>
            </div>
            <div className="summary-card">
              <span className="card-label">В процессе</span>
              <span className="card-value">5</span>
            </div>
            <div className="summary-card">
              <span className="card-label">Не приступали</span>
              <span className="card-value">7</span>
            </div>
          </div>

          <h2>Результаты студентов</h2>
          <table className="stats-table">
            <thead>
              <tr>
                <th>Студент</th>
                <th>Статус</th>
                {activeTab === 'tests' && <th>Результат</th>}
                <th>Время</th>
                {activeTab === 'tests' && <th>Действие</th>}
              </tr>
            </thead>
            <tbody>
              {(activeTab === 'lessons' ? lessonStats : testStats).map((student) => (
                <tr key={student.id}>
                  <td>
                    <div className="student-cell">
                      <div className="avatar-small" style={{ backgroundColor: student.color }}></div>
                      {student.name}
                    </div>
                  </td>
                  <td>{student.status}</td>
                  {activeTab === 'tests' && <td>{student.result}</td>}
                  <td>{student.time}</td>
                  {activeTab === 'tests' && (
                    <td>
                      <button 
                        className="btn-view-report" 
                        onClick={() => setIsReportOpen(true)}
                      >
                        Посмотреть отчеты
                      </button>
                    </td>
                  )}
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {isReportOpen && activeTab === 'tests' && (
          <div className="report-sidebar">
            <h3>Детальный отчет:<br/>Сурмалян Яна</h3>
            <p className="report-subtitle">Тест №1. Название теста</p>
            <p className="report-time">14 минут</p>

            <div className="report-question">
              <p className="q-title">Вопрос 1</p>
              <p className="q-text">Сам вопрос</p>
              <div className="q-answer correct">✓ Текст ответа</div>
            </div>

            <div className="report-question">
              <p className="q-title">Вопрос 2</p>
              <p className="q-text">Сам вопрос</p>
              <div className="q-answer correct">✓ Текст ответа</div>
            </div>

            <div className="report-question">
              <p className="q-title">Вопрос 3</p>
              <p className="q-text">Сам вопрос</p>
              <div className="q-answer incorrect">✕ Текст ответа</div>
              <p className="correct-answer-text">Правильный ответ: ответ</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CourseStatistics;