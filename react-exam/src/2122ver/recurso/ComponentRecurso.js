import React from 'react'
import { ComponentHTTP } from './ComponentHTTP'
import { useState } from 'react'
//https://api.chucknorris.io/jokes/random
export const ComponentRecurso = () => {
  const [uri, setUri] = useState('');
  const [value, setValue] = useState('');
  return (
    <div>
    <form onSubmit={(event) => {
      event.preventDefault();
      setUri(value)
      setValue('')
    }}>
      <input type="text" value={value} onChange={(event) => { setValue(event.target.value); }} />
    </form>
    <ComponentHTTP uri={uri} period={120}></ComponentHTTP>
    </div>
  );
}
