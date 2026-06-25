import { login, register } from "./api.js";
import { saveSession, redirectToHome } from "./auth.js";
import { showError, hideError } from "./errors.js";

async function handleLogin(event) {
  event.preventDefault();
  const form = event.target;
  hideError(form);

  const loginValue = form.querySelector('[name="login"]').value.trim();
  const password = form.querySelector('[name="password"]').value;

  if (!loginValue || !password) {
    showError(form, "Заполните все поля");
    return;
  }

  if (loginValue.length < 4 || loginValue.length > 30) {
    showError(form, "Логин должен быть от 4 до 30 символов");
    return;
  }

  if (password.length < 8) {
    showError(form, "Пароль должен быть не менее 8 символов");
    return;
  }

  const submitBtn = form.querySelector(".popup-submit");
  if (submitBtn) {
    submitBtn.disabled = true;
    submitBtn.textContent = "Вход...";
  }

  try {
    const authResponse = await login({ login: loginValue, password });
    saveSession(authResponse);
    redirectToHome();
  } catch (err) {
    console.error("Ошибка логина:", err);
    showError(form, err.message || "Неверный логин или пароль");
    if (submitBtn) {
      submitBtn.disabled = false;
      submitBtn.textContent = "Войти";
    }
  }
}

async function handleRegister(event) {
  event.preventDefault();
  const form = event.target;
  hideError(form);

  const role = form.querySelector('[name="role"]').value;
  const loginValue = form.querySelector('[name="login"]').value.trim();
  const password = form.querySelector('[name="password"]').value;
  const confirmPassword = form.querySelector('[name="confirmPassword"]').value;

  if (!loginValue || !password || !confirmPassword || !role) {
    showError(form, "Заполните все поля");
    return;
  }

  if (loginValue.length < 4 || loginValue.length > 30) {
    showError(form, "Логин должен быть от 4 до 30 символов");
    return;
  }

  if (password.length < 8) {
    showError(form, "Пароль должен быть не менее 8 символов");
    return;
  }

  if (password !== confirmPassword) {
    showError(form, "Пароли не совпадают");
    return;
  }

  if (role !== "STUDENT" && role !== "TEACHER") {
    showError(form, "Выберите роль");
    return;
  }

  const submitBtn = form.querySelector(".popup-submit");
  if (submitBtn) {
    submitBtn.disabled = true;
    submitBtn.textContent = "Регистрация...";
  }

  try {
    const authResponse = await register({
      login: loginValue,
      password,
      role,
    });
    saveSession(authResponse);
    redirectToHome();
  } catch (err) {
    console.error("Ошибка регистрации:", err);
    showError(form, err.message || "Не удалось зарегистрироваться");
    if (submitBtn) {
      submitBtn.disabled = false;
      submitBtn.textContent = "Зарегистрироваться";
    }
  }
}

async function handleResetPassword(event) {
  event.preventDefault();
  const form = event.target;
  hideError(form);

  const email = form.querySelector('[name="email"]').value.trim();

  if (!email) {
    showError(form, "Введите email или логин");
    return;
  }

  const submitBtn = form.querySelector(".popup-submit");
  if (submitBtn) {
    submitBtn.disabled = true;
    submitBtn.textContent = "Отправка...";
  }

  try {
    alert("Инструкции отправлены на ваш email");
    window.closePopup("resetPopup");
    form.reset();
  } catch (err) {
    console.error("Ошибка сброса:", err);
    showError(form, err.message || "Не удалось отправить инструкции");
  } finally {
    if (submitBtn) {
      submitBtn.disabled = false;
      submitBtn.textContent = "Отправить";
    }
  }
}

async function handleCourseApplication(event) {
  event.preventDefault();
  const form = event.target;
  hideError(form);

  const name = form.querySelector('[name="name"]').value.trim();
  const email = form.querySelector('[name="email"]').value.trim();
  const message = form.querySelector('[name="message"]')?.value.trim() || "";

  if (!name || !email) {
    showError(form, "Заполните обязательные поля");
    return;
  }

  const submitBtn = form.querySelector(".popup-submit");
  if (submitBtn) {
    submitBtn.disabled = true;
    submitBtn.textContent = "Отправка...";
  }

  try {
    alert("Заявка успешно отправлена");
    window.closePopup("applicationOnCourses");
    form.reset();
  } catch (err) {
    console.error("Ошибка заявки:", err);
    showError(form, err.message || "Не удалось отправить заявку");
  } finally {
    if (submitBtn) {
      submitBtn.disabled = false;
      submitBtn.textContent = "Подать заявку";
    }
  }
}

async function handleBecomeTeacher(event) {
  event.preventDefault();
  const form = event.target;
  hideError(form);

  const education = form.querySelector('[name="education"]').value.trim();
  const achievements = form.querySelector('[name="achievements"]').value.trim();
  const projects = form.querySelector('[name="projects"]').value.trim();

  if (!education && !achievements && !projects) {
    showError(form, "Заполните хотя бы одно поле");
    return;
  }

  const submitBtn = form.querySelector(".popup-submit");
  if (submitBtn) {
    submitBtn.disabled = true;
    submitBtn.textContent = "Отправка...";
  }

  try {
    alert("Заявка отправлена администратору");
    window.closePopup("changeRolePopup");
    form.reset();
  } catch (err) {
    console.error("Ошибка смены роли:", err);
    showError(form, err.message || "Не удалось отправить заявку");
  } finally {
    if (submitBtn) {
      submitBtn.disabled = false;
      submitBtn.textContent = "Отправить заявку";
    }
  }
}

export function initAuthHandlers() {
  window.handleLogin = handleLogin;
  window.handleRegister = handleRegister;
  window.handleResetPassword = handleResetPassword;
  window.handleCourseApplication = handleCourseApplication;
  window.handleBecomeTeacher = handleBecomeTeacher;

  initProfileButton();
}

function initProfileButton() {
  const btn = document.getElementById("profileBtn");
  if (!btn) return;

  btn.addEventListener("click", (e) => {
    e.preventDefault();

    const token = localStorage.getItem("authToken");
    const userStr = localStorage.getItem("user");

    if (!token || !userStr) {
      window.openPopup("loginPopup");
      return;
    }

    try {
      const user = JSON.parse(userStr);
      if (user.role === "TEACHER") {
        window.location.href = "/profile_teacher.html";
      } else {
        window.location.href = "/profile_student.html";
      }
    } catch {
      window.openPopup("loginPopup");
    }
  });
}
