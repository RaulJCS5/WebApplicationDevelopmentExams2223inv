import React, { useEffect, useState } from 'react'

export const ComponentURL = ({ urlComponent }) => {
    const [urlSave, setUrlSave] = useState(undefined)

    useEffect(() => {
        async function doFetch() {
            fetch(urlComponent)
                .then(async resp => {
                    const content = await resp.json()
                    if (!resp.ok) {
                        return Promise.reject(
                            {
                                url: urlComponent,
                                content: undefined,
                                status: resp.status,
                                error: content
                            }
                        )
                    }
                    setUrlSave({
                        url: urlComponent,
                        content: content,
                        status: resp.status,
                        error: undefined
                    })
                })
                .catch(errorContent => {
                    setUrlSave(errorContent)
                })
        }
        doFetch()
    }, [])

    return (
        <div>
            {urlSave !== undefined ? (
                <div>
                    <p>URL:{urlSave.url}</p>
                    <p>content:{JSON.stringify(urlSave.content)}</p>
                    <p>status:{urlSave.status}</p>
                    <p>error:{JSON.stringify(urlSave.error)}</p>
                </div>
            )
                : (
                    <div>
                        <p>URL:{urlComponent}</p>
                        <p>content: ... </p>
                        <p>status: ... </p>
                        <p>error: ... </p>
                    </div>
                )}
        </div>
    )
}
