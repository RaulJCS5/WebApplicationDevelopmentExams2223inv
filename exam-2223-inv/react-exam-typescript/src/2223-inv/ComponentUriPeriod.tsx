import React, { useState } from 'react'
import { ComponentPolling } from './ComponentPolling'

export const ComponentUriPeriod = () => {
    const [period, setPeriod] = useState(0)
    const [uri, setUri] = useState('')
    const [periodP, setPeriodP] = useState(0)
    const [uriP, setUriP] = useState('')
    return (
        <div>
            <input type={'text'} value={uri} onChange={(event)=>{setUri(event.target.value)}}></input>
            <input type={'number'} value={period} onChange={(event)=>{setPeriod(Number(event.target.value))}}></input>
            <button onClick={()=>{
                setPeriodP(period)
                setUriP(uri)
            }}>Submit</button>
            <ComponentPolling uri={uriP} period={periodP}></ComponentPolling>
        </div>
    )
}
