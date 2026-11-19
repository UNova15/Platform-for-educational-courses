import React, { useState } from 'react';
import '../styles/LessonPlayer.scss';

const LessonPlayer = () => {
  const [testPassed, setTestPassed] = useState(false);

  return (
    <div className="lesson-layout">
      {/* Сайдбар */}
      <aside className="lesson-sidebar">
        <div className="sidebar-header">
          <h3>Курс: Название курса</h3>
          <p>Ваш прогресс: 25%</p>
          <div className="progress-bar-bg">
            <div className="progress-bar-fill" style={{ width: '25%' }}></div>
          </div>
        </div>

        <div className="sidebar-module">
          <p className="module-title">Модуль 1. Введение</p>
          <div className="sidebar-lesson active">
            <p>Урок 1. Теоретические основы</p>
            <div className="sidebar-sub-items">
              <span className="item completed">Проверочный тест ✓</span>
              <span className="item">Пройден</span>
            </div>
          </div>
          <div className="sidebar-lesson next">
            <p>Следующий урок &gt;</p>
          </div>
        </div>
      </aside>

      {/* Основной контент */}
      <main className="lesson-main">
        <div className="lesson-top">
          <span className="breadcrumb">Модуль 1. Введение</span>
          <button className="btn-back-small">Назад</button>
        </div>
        
        <h1 className="lesson-title">Урок 1. Теоретические основы</h1>

        {!testPassed ? (
          <>
            <div className="theory-block">
              <h4>1.1 Заголовок раздела</h4>
              <p>Здесь какой то текст Здесь какой то текст Здесь какой то текст Здесь какой то текст Здесь какой то текст Здесь какой то текст Здесь какой то текст...</p>
              <h4>1.2 Заголовок раздела</h4>
              <p>Здесь какой то текст Здесь какой то текст Здесь какой то текст Здесь какой то текст...</p>
            </div>

            <div className="lesson-nav">
              <button className="btn-outline">Предыдущий урок</button>
              <button className="btn-primary" onClick={() => setTestPassed(true)}>Следующий урок</button>
            </div>

            <div className="test-block">
              <h3>Проверочный тест</h3>
              <p className="test-sub">Здесь какой то заголовок урока</p>
              
              <p className="question-text">Какой из пунктов ключевой?</p>
              <div className="options">
                <label><input type="radio" name="q1" /> пупу</label>
                <label><input type="radio" name="q1" /> пупу</label>
                <label><input type="radio" name="q1" /> пупу</label>
              </div>
              <div className="test-footer">
                <button className="btn-outline">Ответить</button>
                <span className="attempts">Предыдущая попытка</span>
              </div>
            </div>
          </>
        ) : (
          <div className="success-screen">
            <div className="success-icon">✓</div>
            <h2>Тест успешно пройден!</h2>
            <p>Вы закрепили материал урока «Теоретические основы». Продолжайте обучение — переходите к следующему уроку.</p>
            <div className="success-buttons">
              <button className="btn-outline" onClick={() => setTestPassed(false)}>Вернуться к уроку</button>
              <button className="btn-primary">Следующий урок &rarr;</button>
            </div>
          </div>
        )}

        <div className="next-step-banner">
          <div>
            <h4>Ещё один шаг в обучении</h4>
            <p>К материалам этого урока можно вернуться в любой момент.</p>
          </div>
          <div className="banner-progress">
            <span>Прогресс курса</span>
            <span>25%</span>
            <div className="progress-bar-bg">
              <div className="progress-bar-fill" style={{ width: '25%' }}></div>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default LessonPlayer;