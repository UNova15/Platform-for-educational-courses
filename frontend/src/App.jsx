import { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/layout/Header/Header';
import Footer from './components/layout/Footer/Footer';
import Profile from './pages/Profile';
import CourseStatistics from './pages/CourseStatistics';
import CourseProgram from './pages/CourseProgram';
import LessonPlayer from './pages/LessonPlayer';
import HomePage from './pages/HomePage/HomePage';
import LoginModal from './components/modals/LoginModal/LoginModal';
import RegistrationModal from './components/modals/RegistrationModal/RegistrationModal';
import PasswordResetModal from './components/modals/PasswordResetModal/PasswordResetModal';
import Container from './components/layout/Container/Container';

function App() {
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  const [isRegistrationModalOpen, setIsRegistrationModalOpen] = useState(false);
  const [isPasswordResetModalOpen, setIsPasswordResetModalOpen] = useState(false);

  function handleOpenLoginModal() {
    setIsLoginModalOpen(true);
  }

  function handleCloseLoginModal() {
    setIsLoginModalOpen(false);
  }

  function handleOpenRegistrationModal() {
    setIsLoginModalOpen(false);
    setIsRegistrationModalOpen(true);
  }

  function handleCloseRegistrationModal() {
    setIsRegistrationModalOpen(false);
  }

  function handleOpenPasswordResetModal() {
    setIsLoginModalOpen(false);
    setIsPasswordResetModalOpen(true);
  }

  function handleClosePasswordResetModal() {
    setIsPasswordResetModalOpen(false);
  }

  function handleSwitchToLogin() {
    setIsRegistrationModalOpen(false);
    setIsPasswordResetModalOpen(false);
    setIsLoginModalOpen(true);
  }

  return (
    <BrowserRouter>
      <div className="app" style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <Header onLoginClick={handleOpenLoginModal} />

        <main style={{ flex: 1 }}>
          <Container>
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/profile" element={<Profile />} />
              <Route path="/stats" element={<CourseStatistics />} />
              <Route path="/program" element={<CourseProgram />} />
              <Route path="/lesson" element={<LessonPlayer />} />
            </Routes>
          </Container>
        </main>

        <Footer />

        <LoginModal
          isOpen={isLoginModalOpen}
          onClose={handleCloseLoginModal}
          onSwitchToRegistration={handleOpenRegistrationModal}
          onSwitchToPasswordReset={handleOpenPasswordResetModal}
        />

        <RegistrationModal
          isOpen={isRegistrationModalOpen}
          onClose={handleCloseRegistrationModal}
          onSwitchToLogin={handleSwitchToLogin}
        />

        <PasswordResetModal
          isOpen={isPasswordResetModalOpen}
          onClose={handleClosePasswordResetModal}
          onSwitchToLogin={handleSwitchToLogin}
        />
      </div>
    </BrowserRouter>
  );
}

export default App;
