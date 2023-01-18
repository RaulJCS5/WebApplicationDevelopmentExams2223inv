import React, { useEffect, useState } from 'react'
//https://dev.to/pallymore/clean-up-async-requests-in-useeffect-hooks-90h
//https://stackoverflow.com/questions/31061838/how-do-i-cancel-an-http-fetch-request/47250621#47250621

export const ComponentMethodHttpWithAbort = ({ uri }: { uri: string }) => {
  const [data, setData] = useState(undefined)
  const [loading, setLoading] = useState(false)
  const [fetchContent, setFetchContent] = useState('')
  useEffect(() => {
    const abortController = new AbortController();
    async function doFetch() {
      fetch(fetchContent, { signal: abortController.signal })
        .then(async (resp) => {
          const content = await resp.json()
          if (!resp.ok) {
            return Promise.reject(content)
          }
          setData(content)
          setLoading(false)
        })
        .catch((error) => {
          setData(error)
          setLoading(false)
        })
    }
    if (fetchContent !== '') {
      setLoading(true)
      doFetch()
    }
    return () => {
      abortController.abort();
    }
  }, [fetchContent])

  return (
    <div>
      <textarea rows={10} cols={50} value={JSON.stringify(data)} readOnly />
      <button onClick={() => {
        setFetchContent(uri)
      }} disabled={loading}>Fetch</button>
    </div>
  )
}
