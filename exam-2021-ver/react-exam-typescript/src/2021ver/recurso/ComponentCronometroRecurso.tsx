import React, { useEffect, useState } from 'react'

/*
Inicialmente o botao Start esta ativo e o botao Lap esta inativo. Um clique no botao Start reinicia o
cronometro, o que resulta na ativacao do botao Lap e na remocao de todos os numeros da lista. Neste
estado, um clique no botao Lap acrescenta a lista o valor em segundos entre o ultimo clique em Lap e o
ultimo clique em Start.
*/

export const ComponentCronometroRecurso = () => {
    const [time, setTime] = useState(0)
    const [start, setStart] = useState(true)
    const [lapButton, setLapButton] = useState(false)
    const [lap, setLap] = useState<Number[]>([])
    useEffect(() => {
        const timeoutId = setTimeout(() => {
            setTime(time + 1)
        }, 1000)
        return () => {
            clearTimeout(timeoutId)
        }
    }, [time, setTime, start, setStart])
    return (
        <div>
            <div>time: {time}</div>
            <div>lap: {lap.map((l, i) => { return <p key={i}>{`${l}`}</p> })}</div>
            <button onClick={() => {
                setTime(0)
                setLapButton(true)
                setLap([])
                if(lapButton){
                    setLapButton(false)
                }
            }}>Start</button>
            <button onClick={() => {
                setLap([...lap, time])
            }} disabled={!lapButton}>Lap</button>
        </div>
    )
}
