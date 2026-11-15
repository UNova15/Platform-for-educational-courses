import React from 'react';
import '../styles/Profile.scss';

const Profile = () => {
  // Все данные пока лежат прямо здесь для теста
  const user = {
    firstName: "Кирилл",
    lastName: "Кирилл Кирилл Сергеевич",
    email: "kirill@example.com",
    phone: "+7 (999) 123-45-67",
    registrationDate: "12.05.2023",
    avatarInitials: "К"
  };

  const stats = {
    newCourses: 3,
    completedCourses: 2,
    certificates: 1,
    achievements: 450
  };

  const courses = [
    { id: 1, title: 'Аналитика в Data Science', progress: 20 },
    { id: 2, title: 'Тестировщик', progress: 40 },
    { id: 3, title: 'Маркетолог', progress: 60 },
  ];

  const schedule = [
    { id: 1, day: 'Пн', time: '10:00', title: 'Аналитика в Data Science', type: 'Практическое занятие' },
    { id: 2, day: 'Ср', time: '12:30', title: 'Аналитика в Data Science', type: 'Практическое занятие' },
    { id: 3, day: 'Пт', time: '15:00', title: 'Аналитика в Data Science', type: 'Практическое занятие' },
  ];

  const myCourses = [
    { id: 1, title: 'Аналитика в Data Science', tags: ['Преподаватель', 'Наставник'] },
    { id: 2, title: 'Тестировщик', tags: ['Преподаватель', 'Наставник'] },
    { id: 3, title: 'Маркетолог', tags: ['Преподаватель', 'Наставник'] },
  ];

  return (
    <div className="profile-page">
      
      <div className="profile-greeting">
        <h1>Привет, {user.firstName}!</h1>
        <p>Продолжай обучение и достигай новых высот.</p>
      </div>

      <div className="profile-top-section">
        <div className="stats-grid">
          <div className="stat-card">
            <span className="stat-value">{stats.newCourses}</span>
            <span className="stat-label">Новых курса</span>
          </div>
          <div className="stat-card">
            <span className="stat-value">{stats.completedCourses}</span>
            <span className="stat-label">Пройдено курсов</span>
          </div>
          <div className="stat-card">
            <span className="stat-value">{stats.certificates}</span>
            <span className="stat-label">Сертификат</span>
          </div>
          <div className="stat-card">
            <span className="stat-value">{stats.achievements}</span>
            <span className="stat-label">Достижения</span>
          </div>
        </div>

        <div className="user-card">
           <div className="profile-avatar-large">{user.avatarInitials}</div>
           <div className="profile-contacts">
              <div className="user-name">{user.lastName}</div>
              <span>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path><polyline points="22,6 12,13 2,6"></polyline></svg>
                {user.email}
              </span>
              <span>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"><path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path></svg>
                {user.phone}
              </span>
              <span>
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
                {user.registrationDate}
              </span>
           </div>
           <button className="btn-profile-action">Изменить профиль</button>
        </div>
      </div>

      <h2 className="section-title">Продолжить обучение</h2>
      <div className="courses-grid">
        {courses.map((course) => (
          <div key={course.id} className="course-card">
            <div className="course-image-placeholder"></div>
            <div className="course-info">
              <div className="course-title">{course.title}</div>
              <div className="progress-container">
                <div className="progress-header">
                  <span>Прогресс</span>
                  <span>{course.progress}%</span>
                </div>
                <div className="progress-bar-bg">
                  <div className="progress-bar-fill" style={{ width: `${course.progress}%` }}></div>
                </div>
                <button className="btn-continue-small">Продолжить</button>
              </div>
            </div>
          </div>
        ))}
      </div>

      <h2 className="section-title" style={{ marginTop: '50px' }}>Ближайшие занятия</h2>
      <div className="lessons-list">
        {schedule.map((lesson) => (
          <div key={lesson.id} className="lesson-item">
            <div className="lesson-date">
              <span className="date-day">{lesson.day}</span>
              <span className="date-time">{lesson.time}</span>
            </div>
            <div className="lesson-info">
              <span className="lesson-title">{lesson.title}</span>
              <span className="lesson-sub">{lesson.type}</span>
            </div>
            <button className="btn-outline-small">Подключиться</button>
          </div>
        ))}
      </div>

      <h2 className="section-title">Мои курсы</h2>
      <div className="my-courses-grid">
        {myCourses.map((course) => (
          <div key={course.id} className="my-course-card">
            <div className="card-image"></div>
            <div className="card-content">
              <div className="card-title">{course.title}</div>
              <div className="tags-row">
                {course.tags.map((tag, idx) => (
                  <span key={idx} className="tag">{tag}</span>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>

      <button className="btn-main-action">Записаться на курс</button>

      <h2 className="section-title">Неопубликованные курсы</h2>
      <div className="unpublished-card">
        <div className="card-image"></div>
        <div className="card-content">
          <div className="card-title">Название курса</div>
          <div className="tags-row">
            <span className="tag">Преподаватель</span>
            <span className="tag">Специалист</span>
          </div>
        </div>
      </div>

    </div>
  );
};

export default Profile;