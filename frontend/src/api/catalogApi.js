import { request } from './apiClient';
export function getCatalogCourses() {
  return request('/catalog');
}
export function getCourseDetails(courseId) {
  return request(`/catalog/${courseId}`);
}