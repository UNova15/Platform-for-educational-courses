import { isAuthenticated, isTeacher, isStudent } from "./auth.js";

export function requireAuth() {
  if (!isAuthenticated()) {
    window.location.replace("/index.html");
    return false;
  }
  return true;
}

export function requireTeacher() {
  if (!requireAuth()) return false;
  if (!isTeacher()) {
    window.location.replace("/index.html");
    return false;
  }
  return true;
}

export function requireStudent() {
  if (!requireAuth()) return false;
  if (!isStudent()) {
    window.location.replace("/teacher_index.html");
    return false;
  }
  return true;
}

export function redirectTeacherFromHome() {
  if (isAuthenticated() && isTeacher()) {
    window.location.replace("/teacher_index.html");
    return true;
  }
  return false;
}

export function redirectIfAuthenticated() {
  if (!isAuthenticated()) return false;
  if (isTeacher()) {
    window.location.replace("/teacher_index.html");
  } else {
    window.location.replace("/index.html");
  }
  return true;
}
