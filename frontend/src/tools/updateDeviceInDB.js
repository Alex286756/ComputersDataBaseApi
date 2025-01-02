export async function updateDeviceInDb(deviceId, jwt, idValue) {
    await fetch(deviceId, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + jwt,
        },
        body: JSON.stringify({
            "complectId": `${idValue}`
        }),
    });
}