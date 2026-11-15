export const fetchProfileData = async () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        user: {
          firstName: "Кирилл",
          lastName: "Кирилл Кирилл",
          email: "kirill@example.com",
          phone: "+7 (999) 123-45-67",
          registrationDate: "12.05.2023",
          avatarInitials: "К"
        },
        stats: {
          newCourses: 3,
          completedCourses: 2,
          certificates: 1,
          achievements: 450
        },
        
        schedule: [
          { id: 1, day: 'Пн', time: '10:00', title: 'Аналитика в Data Science', type: 'Практическое занятие' },
          { id: 2, day: 'Ср', time: '12:30', title: 'Аналитика в Data Science', type: 'Практическое занятие' },
        ],
        myCourses: [
          { id: 1, title: 'Аналитика в Data Science', tags: ['Преподаватель', 'Наставник'] },
          { id: 2, title: 'Тестировщик', tags: ['Преподаватель', 'Наставник'] },
        ]
      });
    }, 500);
  });
};