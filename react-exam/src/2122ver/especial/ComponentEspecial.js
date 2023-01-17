import React from 'react'
import { ComponentURL } from './ComponentURL'

export const ComponentEspecial = () => {
  const urls = [
    'https://api.chucknorris.io/jokes/random',
    'https://api.chucknorris.io/jokes/categories'
  ]
  return (
    <div>
      {
        urls.map((d, i) => {
          return <ComponentURL key={i} urlComponent={d}></ComponentURL>
        })
      }
    </div>
  )
}
