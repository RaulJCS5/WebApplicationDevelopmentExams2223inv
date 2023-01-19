import React from 'react'
import { ComponentMessages } from './ComponentMessages'


export const Component = ({text}) => {
  return (
    <div>{text}</div>
  )
}

export const ComponentNormal = () => {
    const messages = ['olá', 'tudo','bem','chamo-me','raul']
    const period = 1000
  return (
    <div>
        <ComponentMessages messages={messages} period={period} Component={Component}></ComponentMessages>
    </div>
  )
}
