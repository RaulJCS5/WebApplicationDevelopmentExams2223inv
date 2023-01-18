import React, { useEffect, useState } from 'react'

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
