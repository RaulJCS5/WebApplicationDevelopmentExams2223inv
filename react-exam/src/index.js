import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { ComponentNormal } from './2122ver/normal/ComponentNormal';
import { ComponentRecurso } from './2122ver/recurso/ComponentRecurso';
import { ComponentEspecial } from './2122ver/especial/ComponentEspecial';
import { Cronometro } from './help/Cronometro';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <ComponentNormal />
  </React.StrictMode>
);