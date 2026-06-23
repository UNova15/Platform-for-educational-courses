function redirectByRole(role) {
    if (role === 'TEACHER') {
        window.location.href = '/teacher_index.html';
    } else if (role === 'STUDENT') {
        window.location.href = '/profile_student.html';
    } else {
        window.location.href = '/index.html';
    }
}