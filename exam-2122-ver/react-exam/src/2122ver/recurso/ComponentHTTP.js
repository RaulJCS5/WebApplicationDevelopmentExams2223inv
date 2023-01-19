import React, { useState, useEffect } from 'react'

export const ComponentHTTP = ({ uri, period }) => {
    const [time, setTime] = useState(0)
    const [timeLong, setTimeLong] = useState(0)
    const [saveData, setSaveData] = useState(undefined)
    const [error, setError] = useState(undefined)
    const [statusCode, setStatusCode] = useState(undefined)
    useEffect(() => {
        const intervalId = setTimeout(() => {
            setTime(time + 1)
        }, period)
        return () => {
            clearInterval(intervalId)
        }
    }, [time, setTime, period])
    useEffect(() => {
        async function doFetch() {
            const startTime = Date.now()
            let endTime;
            fetch(uri)
                .then(async resp => {
                    setStatusCode(resp.status)
                    const data = await resp.json()
                    if (!resp.ok) {
                        return Promise.reject(data)
                    }
                    setSaveData(data)
                    setError(undefined)
                    endTime = Date.now() - startTime
                    setTimeLong(endTime)
                    if (endTime >= period) {
                        setSaveData(undefined)
                        setError({error:'timeout'})
                    }
                })
                .catch(error => {
                    setError(error)
                    setSaveData(undefined)
                    endTime = Date.now() - startTime
                    setTimeLong(endTime)
                    if (endTime >= period) {
                        setSaveData(undefined)
                        setError({error:'timeout'})
                    }
                })
        }
        doFetch()
    }, [uri])
    return (
        <div>
            <p>Time: {time}</p>
            <p>Data: {JSON.stringify(saveData)}</p>
            <p>Error: {JSON.stringify(error)}</p>
            <p>Status code: {JSON.stringify(statusCode)}</p>
            <p>How long it took? {timeLong}</p>
        </div>
    )
}
