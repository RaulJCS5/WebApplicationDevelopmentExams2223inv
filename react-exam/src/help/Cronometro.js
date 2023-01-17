import React, { useEffect, useState } from 'react'

export const Cronometro = () => {
    const [time,setTime] = useState(0)
    const [timerRunning,setTimerRunning] = useState(false)
    const [turn,setTurn] = useState([])
    useEffect(()=>{
        var intervalId
        if(timerRunning){
            intervalId = setTimeout(()=>{
                setTime(time+1)
            },1000)
        }
        return ()=>{
            clearInterval(intervalId)
        }
    },[timerRunning,setTime,time])
    function handleStart(){
        setTimerRunning(true)
    }
    function handleStop(){
        setTimerRunning(false)
    }
    function handleReset(){
        setTime(0)
        setTimerRunning(false)
        setTurn([])
    }
    function handleVolta(){
        setTurn([...turn,time])
    }
    return (
        <div>
            <h2>{time}</h2>
            <button onClick={handleStart}>Start</button>
            <button onClick={handleStop}>Stop</button>
            <button onClick={handleReset}>Reset</button>
            <button onClick={handleVolta}>Volta</button>
            {
                turn.map((val,i)=>{
                    return <p key={i}>Turn number {i}: {val}</p>
                })
            }
        </div>
    )
}
