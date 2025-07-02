import {BrowserRouter, Routes, Route} from 'react-router-dom';


//CSS import
import './App.css';
import './styles/login.css';
import './styles/regist.css';

//Page imports
import MapPage from './pages/MapPage.js';
import LoginPage from './pages/login/LogInPage.js';
import RegistUserPage from './pages/login/RegistUserPage.js';


function App() {
  return (

      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MapPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/regist" element={<RegistUserPage />} />
        </Routes>
      </BrowserRouter>

  );
}

export default App;

