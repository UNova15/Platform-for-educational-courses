import { request } from './apiClient';
export function loginUser({ email, password }) {
  return request('/auth/login', {
    method: 'POST',
    body: JSON.stringify({ email, password }),
  });
}
export function registerUser({ login, email, password }) {
  return request('/auth/registration', {
    method: 'POST',
    body: JSON.stringify({ login, email, password }),
  });
}
export function confirmRegistrationCode({ email, code }) {
  return request('/auth/registration/confirm', {
    method: 'POST',
    body: JSON.stringify({ email, code }),
  });
}
export function resendRegistrationCode({ email }) {
  return request('/auth/registration/resend', {
    method: 'POST',
    body: JSON.stringify({ email }),
  });
}
export function sendPasswordResetCode({ email }) {
  return request('/auth/password/reset', {
    method: 'POST',
    body: JSON.stringify({ email }),
  });
}
export function verifyPasswordResetCode({ email, code }) {
  return request('/auth/password/reset/verify', {
    method: 'POST',
    body: JSON.stringify({ email, code }),
  });
}
export function setNewPassword({ email, code, password }) {
  return request('/auth/password/reset/confirm', {
    method: 'POST',
    body: JSON.stringify({ email, code, password }),
  });
}