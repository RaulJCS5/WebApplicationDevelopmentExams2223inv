import React, { useEffect, useState } from 'react'

export const ComponentWithDateNow = () => {
    const [time,setTime] = useState(Date.now())
    const [lapButton, setLapButton] = useState(false)
    const [lap, setLap] = useState<Number[]>([])
    return (
        <div>
            <div>time: {time}</div>
            <div>lap: {lap.map((l, i) => { return <p key={i}>{`${l}`}</p> })}</div>
            <button onClick={() => {
                setTime(Date.now())
                setLapButton(true)
                setLap([])
                if(lapButton){
                    setLapButton(false)
                }
            }}>Start</button>
            <button onClick={() => {
                setLap([...lap, (Date.now()-time)/1000])
            }} disabled={!lapButton}>Lap</button>
        </div>
    )
}
