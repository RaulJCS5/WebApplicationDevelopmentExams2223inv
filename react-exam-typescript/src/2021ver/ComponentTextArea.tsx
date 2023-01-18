import React, { useState } from 'react'
import { ComponentMethodHttp } from './ComponentMethodHttp'

export const ComponentTextArea = () => {
    const [uri,setUri] = useState('Okay')
  return (
    <div>
        <ComponentMethodHttp uri={uri}></ComponentMethodHttp>
    </div>
  )
}
