import React, { useState } from 'react'
import { ComponentMethodHttpWithAbort } from './ComponentMethodHttpWithAbort'
import { ComponentMethodHttpWithIsCancelled } from './ComponentMethodHttpWithIsCancelled'

//https://api.chucknorris.io/jokes/random
//https://httpbin.org/delay/5

export const ComponentTextArea = () => {
  const [uri, setUri] = useState('')
  return (
    <div>
      <input type="text" value={uri} onChange={(event) => { setUri(event.target.value) }} />
      <ComponentMethodHttpWithIsCancelled uri={uri}></ComponentMethodHttpWithIsCancelled>
      <ComponentMethodHttpWithAbort uri={uri}></ComponentMethodHttpWithAbort>
    </div>
  )
}
