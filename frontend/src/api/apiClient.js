const API_BASE_URL = '/api';

export async function request(path, options = {}) {
  const token = localStorage.getItem('accessToken');

  const headers = {
    'Content-Type': 'application/json',
    ...options.headers,
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    let backendMessage = '';
    try {
      const errorData = await response.json();
      backendMessage = errorData?.message || errorData?.error || '';
    } catch {
     //
    }

    const error = new Error(backendMessage || `Статус ${response.status}`);
    error.status = response.status;
    error.backendMessage = backendMessage;
    throw error;
  }

  if (response.status === 204) {
    return null;
  }

  return response.json();
}