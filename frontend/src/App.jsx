import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Header from './components/layout/Header/Header';
import Footer from './components/layout/Footer/Footer';

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <Header />

        <main className="container" style={{ padding: '40px 20px' }}>
          <Routes>
            <Route path="/" element={<h1>Платформа для онлайн обучения все в одном</h1>} />
          </Routes>
        </main>
        <Footer />
      </div>
    </BrowserRouter>
  );
}

export default App;