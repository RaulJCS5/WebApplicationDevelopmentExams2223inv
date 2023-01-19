import React, { useEffect, useState } from 'react'
/*
This approach is different from what is the question
This cronometer laps the time between the current time and the previous lap
*/

export const ComponentCronometro = () => {
    const [time, setTime] = useState(0)
    const [start, setStart] = useState(false)
    const [lap, setLap] = useState<Number[]>([])
    const [lapBetween, setLapBetween] = useState<Number[]>([])
    useEffect(() => {
        const timeoutId = setTimeout(() => {
            setTime(time + 1)
        }, 1000)
        if (start) {
            setTime(0)
            setLap([])
            setLapBetween([])
            clearTimeout(timeoutId)
            setStart(!start)
            return
        }
        return () => {
            clearTimeout(timeoutId)
        }
    }, [time, setTime, start, setStart])
    return (
        <div>
            <div>time: {time}</div>
            <div>lap: {lapBetween.map((l, i) => { return <p key={i}>{`${l}`}</p> })}</div>
            <button onClick={() => { setStart(!start) }}>Start</button>
            <button onClick={() => {
                if (lap.length < 1) {
                    setLap([...lap, time])
                    setLapBetween([...lapBetween, time])
                }else{
                    const lastLap = Number(lap[lap.length-1])
                    setLap([...lap, time])
                    setLapBetween([...lapBetween,time-lastLap])
                }
            }}>Lap</button>
        </div>
    )
}
