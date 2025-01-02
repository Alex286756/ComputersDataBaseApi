export async function loadDataFromDB(dispatch, dataName, jwt, sliceName) {
    const response = await fetch(dataName, {
        headers: {
            "Authorization": "Bearer " + jwt,
        },
    })
    const data = await response.json()
    
    dispatch(sliceName(data))
}