import React, { useEffect, useState } from 'react'

function useCounter(initialValue:number):[observed:number,inc:()=>void,dec:()=>void]{
    const [counter,setCounter] = useState(initialValue)
    function inc(){
        setCounter(counter+1)
    }
    function dec(){
        setCounter(counter-1)
    }
    return [counter,inc,dec]
}

export const ComponentPolling = ({ uri, period }: { uri: string, period: number }) => {
    const [time, setTime] = useState(0)
    const [data, setData] = useState(undefined)
    const [error, setError] = useState(undefined)
    const [observed,inc,dec] = useCounter(10)
    useEffect(() => {
        let isCancelled = false
        async function doFetch() {
            fetch(uri)
                .then(async resp => {
                    const content = await resp.json()
                    if (!isCancelled) {
                        if (!resp.ok) {
                            return Promise.reject(content)
                        }
                        setError(undefined)
                        setData(content)
                    }
                })
                .catch(error => {
                    setData(undefined)
                    setError(error)
                })
        }
        if (time > 0) {
            doFetch()
        }
        const timeId = setTimeout(() => {
            setTime(time + 1)
        }, period)
        return () => {
            isCancelled=true
            clearTimeout(timeId)
        }
    }, [uri, time, period])
    return (
        <div>
            <textarea readOnly rows={10} cols={40} value={error === undefined ? JSON.stringify(data) : JSON.stringify(error)}></textarea>
            <div>
                <p>{observed}</p>
                <button onClick={inc} >+</button>
                <button onClick={dec}>-</button>
            </div>
        </div>
    )
}
