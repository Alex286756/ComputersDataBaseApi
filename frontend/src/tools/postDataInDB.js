export async function postDataInDb(dataName, jwt, value) {
    const response = await fetch(dataName, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + jwt,
        },
        body: JSON.stringify({
            "name": `${value}`
        }),
    });
    const data = await response.json();
    return data;
}