import React, { useState,useEffect } from 'react'

export const ComponentMessages = ({ messages,period,Component}) => {
    const [time,setTime] = useState(0)
    useEffect(() => {
        setTimeout(() => {
            setTime(time + 1)
        }, period)
        if(time===messages.length){
            setTime(0)
        }
    }, [time,setTime,period])
    return (
        <div>
            <Component text={messages[time]}></Component>
        </div>
    )
}
