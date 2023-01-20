import React from 'react';
import logo from './logo.svg';
import './App.css';
import { ComponentTextArea } from './2021ver/normal/ComponentTextArea';
import { ComponentCronometro } from './2021ver/recurso/ComponentCronometro';
import { ComponentCronometroRecurso } from './2021ver/recurso/ComponentCronometroRecurso';
import { ComponentCounter } from './2021ver/especial/ComponentCounter';
import { ComponentWithDateNow } from './2021ver/recurso/ComponentWithDateNow';


function App() {
  return (
    <ComponentWithDateNow></ComponentWithDateNow>
  );
}

export default App;
