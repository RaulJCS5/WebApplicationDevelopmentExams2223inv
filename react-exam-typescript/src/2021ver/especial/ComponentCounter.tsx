import React, { useEffect, useState } from 'react'

export const ComponentCounter = () => {
    const [counter, setCounter] = useState(0)
    const [period, setPeriod] = useState(0)
    const [changePeriod, setChangePeriod] = useState(0)
    useEffect(()=>{
        const timeoutId = setTimeout(()=>{
            setCounter(counter+1)
        },period)
        return ()=>{
            clearTimeout(timeoutId)
        }
    },[counter,setCounter,period])
    return (
        <div>
            <p>{counter}</p>
            <input type={'number'} value={changePeriod} onChange={(event) => { setChangePeriod(Number(event.target.value)) }}></input>
            <button onClick={() => {
                setPeriod(changePeriod)
            }}>Press</button>
        </div>
    )
}
