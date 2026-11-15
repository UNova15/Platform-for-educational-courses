import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Profile from './pages/Profile';

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        {
          
        }
        
        <main className="container">
          <Routes>
            <Route path="/" element={<h1>Главная страница</h1>} />
            <Route path="/profile" element={<Profile />} />
            {

            }
          </Routes>
        </main>

        {

        }
      </div>
    </BrowserRouter>
  );
}

export default App;