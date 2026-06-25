const TOKEN_KEY = "authToken";
const USER_KEY = "user";

export function saveSession(authResponse) {
  if (authResponse.jwtToken) {
    localStorage.setItem(TOKEN_KEY, authResponse.jwtToken);
  }

  const user = {
    id: authResponse.id,
    login: authResponse.login,
    role: authResponse.role,
  };

  localStorage.setItem(USER_KEY, JSON.stringify(user));
}

export function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

export function getUser() {
  const userStr = localStorage.getItem(USER_KEY);
  if (!userStr) return null;
  try {
    return JSON.parse(userStr);
  } catch {
    return null;
  }
}

export function isAuthenticated() {
  return !!getToken();
}

export function clearSession() {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(USER_KEY);
}

export function isTeacher() {
  const user = getUser();
  return user?.role === "TEACHER";
}

export function isStudent() {
  const user = getUser();
  return user?.role === "STUDENT";
}

export function redirectToHome() {
  if (!isAuthenticated()) {
    window.location.href = "/index.html";
    return;
  }
  if (isTeacher()) {
    window.location.href = "/teacher_index.html";
  } else {
    window.location.href = "/index.html";
  }
}

export function logout() {
  clearSession();
  window.location.href = "/index.html";
}
