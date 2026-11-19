import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/layout/Header/Header';
import Footer from './components/layout/Footer/Footer';
import Profile from './pages/Profile';
import CourseStatistics from './pages/CourseStatistics';
import CourseProgram from './pages/CourseProgram';
import LessonPlayer from './pages/LessonPlayer';

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Header />

        <main className="container" style={{ padding: '40px 20px' }}>
          <Routes>
            <Route path="/" element={<h1>Платформа для онлайн обучения все в одном</h1>} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/stats" element={<CourseStatistics />} />
            <Route path="/program" element={<CourseProgram />} />
            <Route path="/lesson" element={<LessonPlayer />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;